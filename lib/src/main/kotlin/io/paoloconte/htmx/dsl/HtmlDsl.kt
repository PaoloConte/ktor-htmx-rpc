package io.paoloconte.htmx.dsl

sealed interface Node

class TextNode(val text: String) : Node

class RawHtml(val html: String) : Node

interface Tag {
    val tagName: String
    val attributes: MutableMap<String, String>
    val children: MutableList<Node>

    fun raw(html: String) {
        children.add(RawHtml(html))
    }

    operator fun String.unaryPlus() {
        children.add(TextNode(this))
    }
}

class HtmlTag(override val tagName: String) : Tag, Node {
    override val attributes = mutableMapOf<String, String>()
    override val children = mutableListOf<Node>()

    fun tag(name: String, block: Tag.() -> Unit = {}): Tag {
        val child = HtmlTag(name)
        child.block()
        children.add(child)
        return child
    }

    fun render(): String = buildString { renderTo(this) }

    fun renderTo(sb: StringBuilder) {
        sb.append('<').append(tagName)
        for ((key, value) in attributes) {
            sb.append(' ').append(key).append("=\"").append(escapeAttr(value)).append('"')
        }
        if (tagName in VOID_ELEMENTS) {
            sb.append("/>")
            return
        }
        sb.append('>')
        for (child in children) {
            when (child) {
                is TextNode -> sb.append(escapeHtml(child.text))
                is RawHtml -> sb.append(child.html)
                is HtmlTag -> child.renderTo(sb)
            }
        }
        sb.append("</").append(tagName).append('>')
    }
}

/** Builds HTML from a virtual root — renders only the children (no wrapper tag). */
fun buildHtml(block: Tag.() -> Unit): String = buildString {
    val root = HtmlTag("__root__")
    root.block()
    for (child in root.children) {
        when (child) {
            is TextNode -> append(escapeHtml(child.text))
            is RawHtml -> append(child.html)
            is HtmlTag -> child.renderTo(this)
        }
    }
}

val VOID_ELEMENTS = setOf(
    "area", "base", "br", "col", "embed", "hr", "img", "input",
    "link", "meta", "param", "source", "track", "wbr"
)

fun escapeHtml(text: String): String = buildString(text.length) {
    for (c in text) {
        when (c) {
            '&' -> append("&amp;")
            '<' -> append("&lt;")
            '>' -> append("&gt;")
            else -> append(c)
        }
    }
}

fun escapeAttr(text: String): String = buildString(text.length) {
    for (c in text) {
        when (c) {
            '&' -> append("&amp;")
            '"' -> append("&quot;")
            '<' -> append("&lt;")
            '>' -> append("&gt;")
            else -> append(c)
        }
    }
}
