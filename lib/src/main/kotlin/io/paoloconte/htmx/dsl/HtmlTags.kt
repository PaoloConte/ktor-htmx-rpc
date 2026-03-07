package io.paoloconte.htmx.dsl

// Helper to create a tag with optional CSS classes
private fun Tag.htmlTag(name: String, cssClasses: Array<out String>, block: Tag.() -> Unit): Tag {
    val child = HtmlTag(name)
    if (cssClasses.isNotEmpty()) {
        child.attributes["class"] = cssClasses.joinToString(" ")
    }
    child.block()
    children.add(child)
    return child
}

fun Tag.html(vararg cssClasses: String, block: Tag.() -> Unit = {}) = htmlTag("html", cssClasses, block)
fun Tag.head(vararg cssClasses: String, block: Tag.() -> Unit = {}) = htmlTag("head", cssClasses, block)
fun Tag.body(vararg cssClasses: String, block: Tag.() -> Unit = {}) = htmlTag("body", cssClasses, block)
fun Tag.title(vararg cssClasses: String, block: Tag.() -> Unit = {}) = htmlTag("title", cssClasses, block)

fun Tag.meta(charset: String? = null, name: String? = null, content: String? = null, block: Tag.() -> Unit = {}): Tag {
    val child = HtmlTag("meta")
    charset?.let { child.attributes["charset"] = it }
    name?.let { child.attributes["name"] = it }
    content?.let { child.attributes["content"] = it }
    child.block()
    children.add(child)
    return child
}

fun Tag.link(rel: String? = null, href: String? = null, block: Tag.() -> Unit = {}): Tag {
    val child = HtmlTag("link")
    rel?.let { child.attributes["rel"] = it }
    href?.let { child.attributes["href"] = it }
    child.block()
    children.add(child)
    return child
}

fun Tag.style(vararg cssClasses: String, block: Tag.() -> Unit = {}) = htmlTag("style", cssClasses, block)

fun Tag.script(src: String? = null, vararg cssClasses: String, block: Tag.() -> Unit = {}): Tag {
    val child = HtmlTag("script")
    src?.let { child.attributes["src"] = it }
    if (cssClasses.isNotEmpty()) child.attributes["class"] = cssClasses.joinToString(" ")
    child.block()
    children.add(child)
    return child
}

fun Tag.base(vararg cssClasses: String, block: Tag.() -> Unit = {}) = htmlTag("base", cssClasses, block)
fun Tag.noscript(vararg cssClasses: String, block: Tag.() -> Unit = {}) = htmlTag("noscript", cssClasses, block)
fun Tag.header(vararg cssClasses: String, block: Tag.() -> Unit = {}) = htmlTag("header", cssClasses, block)
fun Tag.footer(vararg cssClasses: String, block: Tag.() -> Unit = {}) = htmlTag("footer", cssClasses, block)
fun Tag.main(vararg cssClasses: String, block: Tag.() -> Unit = {}) = htmlTag("main", cssClasses, block)
fun Tag.section(vararg cssClasses: String, block: Tag.() -> Unit = {}) = htmlTag("section", cssClasses, block)
fun Tag.article(vararg cssClasses: String, block: Tag.() -> Unit = {}) = htmlTag("article", cssClasses, block)
fun Tag.aside(vararg cssClasses: String, block: Tag.() -> Unit = {}) = htmlTag("aside", cssClasses, block)
fun Tag.nav(vararg cssClasses: String, block: Tag.() -> Unit = {}) = htmlTag("nav", cssClasses, block)
fun Tag.div(vararg cssClasses: String, block: Tag.() -> Unit = {}) = htmlTag("div", cssClasses, block)
fun Tag.h1(vararg cssClasses: String, block: Tag.() -> Unit = {}) = htmlTag("h1", cssClasses, block)
fun Tag.h2(vararg cssClasses: String, block: Tag.() -> Unit = {}) = htmlTag("h2", cssClasses, block)
fun Tag.h3(vararg cssClasses: String, block: Tag.() -> Unit = {}) = htmlTag("h3", cssClasses, block)
fun Tag.h4(vararg cssClasses: String, block: Tag.() -> Unit = {}) = htmlTag("h4", cssClasses, block)
fun Tag.h5(vararg cssClasses: String, block: Tag.() -> Unit = {}) = htmlTag("h5", cssClasses, block)
fun Tag.h6(vararg cssClasses: String, block: Tag.() -> Unit = {}) = htmlTag("h6", cssClasses, block)
fun Tag.p(vararg cssClasses: String, block: Tag.() -> Unit = {}) = htmlTag("p", cssClasses, block)
fun Tag.span(vararg cssClasses: String, block: Tag.() -> Unit = {}) = htmlTag("span", cssClasses, block)

fun Tag.a(href: String? = null, vararg cssClasses: String, block: Tag.() -> Unit = {}): Tag {
    val child = HtmlTag("a")
    href?.let { child.attributes["href"] = it }
    if (cssClasses.isNotEmpty()) child.attributes["class"] = cssClasses.joinToString(" ")
    child.block()
    children.add(child)
    return child
}

fun Tag.strong(vararg cssClasses: String, block: Tag.() -> Unit = {}) = htmlTag("strong", cssClasses, block)
fun Tag.em(vararg cssClasses: String, block: Tag.() -> Unit = {}) = htmlTag("em", cssClasses, block)
fun Tag.b(vararg cssClasses: String, block: Tag.() -> Unit = {}) = htmlTag("b", cssClasses, block)
fun Tag.i(vararg cssClasses: String, block: Tag.() -> Unit = {}) = htmlTag("i", cssClasses, block)
fun Tag.u(vararg cssClasses: String, block: Tag.() -> Unit = {}) = htmlTag("u", cssClasses, block)
fun Tag.small(vararg cssClasses: String, block: Tag.() -> Unit = {}) = htmlTag("small", cssClasses, block)
fun Tag.mark(vararg cssClasses: String, block: Tag.() -> Unit = {}) = htmlTag("mark", cssClasses, block)
fun Tag.code(vararg cssClasses: String, block: Tag.() -> Unit = {}) = htmlTag("code", cssClasses, block)
fun Tag.pre(vararg cssClasses: String, block: Tag.() -> Unit = {}) = htmlTag("pre", cssClasses, block)
fun Tag.blockquote(vararg cssClasses: String, block: Tag.() -> Unit = {}) = htmlTag("blockquote", cssClasses, block)
fun Tag.br(block: Tag.() -> Unit = {}) = htmlTag("br", emptyArray(), block)
fun Tag.hr(block: Tag.() -> Unit = {}) = htmlTag("hr", emptyArray(), block)
fun Tag.ul(vararg cssClasses: String, block: Tag.() -> Unit = {}) = htmlTag("ul", cssClasses, block)
fun Tag.ol(vararg cssClasses: String, block: Tag.() -> Unit = {}) = htmlTag("ol", cssClasses, block)
fun Tag.li(vararg cssClasses: String, block: Tag.() -> Unit = {}) = htmlTag("li", cssClasses, block)
fun Tag.dl(vararg cssClasses: String, block: Tag.() -> Unit = {}) = htmlTag("dl", cssClasses, block)
fun Tag.dt(vararg cssClasses: String, block: Tag.() -> Unit = {}) = htmlTag("dt", cssClasses, block)
fun Tag.dd(vararg cssClasses: String, block: Tag.() -> Unit = {}) = htmlTag("dd", cssClasses, block)
fun Tag.table(vararg cssClasses: String, block: Tag.() -> Unit = {}) = htmlTag("table", cssClasses, block)
fun Tag.thead(vararg cssClasses: String, block: Tag.() -> Unit = {}) = htmlTag("thead", cssClasses, block)
fun Tag.tbody(vararg cssClasses: String, block: Tag.() -> Unit = {}) = htmlTag("tbody", cssClasses, block)
fun Tag.tfoot(vararg cssClasses: String, block: Tag.() -> Unit = {}) = htmlTag("tfoot", cssClasses, block)
fun Tag.tr(vararg cssClasses: String, block: Tag.() -> Unit = {}) = htmlTag("tr", cssClasses, block)
fun Tag.th(vararg cssClasses: String, block: Tag.() -> Unit = {}) = htmlTag("th", cssClasses, block)
fun Tag.td(vararg cssClasses: String, block: Tag.() -> Unit = {}) = htmlTag("td", cssClasses, block)
fun Tag.caption(vararg cssClasses: String, block: Tag.() -> Unit = {}) = htmlTag("caption", cssClasses, block)
fun Tag.colgroup(vararg cssClasses: String, block: Tag.() -> Unit = {}) = htmlTag("colgroup", cssClasses, block)
fun Tag.col(block: Tag.() -> Unit = {}) = htmlTag("col", emptyArray(), block)
fun Tag.form(vararg cssClasses: String, block: Tag.() -> Unit = {}) = htmlTag("form", cssClasses, block)

fun Tag.input(type: InputType = InputType.text, name: String? = null, vararg cssClasses: String, block: Tag.() -> Unit = {}): Tag {
    val child = HtmlTag("input")
    child.attributes["type"] = type.value
    name?.let { child.attributes["name"] = it }
    if (cssClasses.isNotEmpty()) child.attributes["class"] = cssClasses.joinToString(" ")
    child.block()
    children.add(child)
    return child
}

fun Tag.button(vararg cssClasses: String, type: ButtonType = ButtonType.button, block: Tag.() -> Unit = {}): Tag {
    val child = HtmlTag("button")
    child.attributes["type"] = type.value
    if (cssClasses.isNotEmpty()) child.attributes["class"] = cssClasses.joinToString(" ")
    child.block()
    children.add(child)
    return child
}

fun Tag.select(vararg cssClasses: String, block: Tag.() -> Unit = {}) = htmlTag("select", cssClasses, block)
fun Tag.option(vararg cssClasses: String, block: Tag.() -> Unit = {}) = htmlTag("option", cssClasses, block)
fun Tag.optgroup(vararg cssClasses: String, block: Tag.() -> Unit = {}) = htmlTag("optgroup", cssClasses, block)
fun Tag.textarea(vararg cssClasses: String, block: Tag.() -> Unit = {}) = htmlTag("textarea", cssClasses, block)
fun Tag.label(vararg cssClasses: String, block: Tag.() -> Unit = {}) = htmlTag("label", cssClasses, block)
fun Tag.fieldset(vararg cssClasses: String, block: Tag.() -> Unit = {}) = htmlTag("fieldset", cssClasses, block)
fun Tag.legend(vararg cssClasses: String, block: Tag.() -> Unit = {}) = htmlTag("legend", cssClasses, block)
fun Tag.img(src: String? = null, alt: String? = null, vararg cssClasses: String, block: Tag.() -> Unit = {}): Tag {
    val child = HtmlTag("img")
    src?.let { child.attributes["src"] = it }
    alt?.let { child.attributes["alt"] = it }
    if (cssClasses.isNotEmpty()) child.attributes["class"] = cssClasses.joinToString(" ")
    child.block()
    children.add(child)
    return child
}

fun Tag.audio(vararg cssClasses: String, block: Tag.() -> Unit = {}) = htmlTag("audio", cssClasses, block)
fun Tag.video(vararg cssClasses: String, block: Tag.() -> Unit = {}) = htmlTag("video", cssClasses, block)
fun Tag.source(block: Tag.() -> Unit = {}) = htmlTag("source", emptyArray(), block)
fun Tag.canvas(vararg cssClasses: String, block: Tag.() -> Unit = {}) = htmlTag("canvas", cssClasses, block)
fun Tag.picture(vararg cssClasses: String, block: Tag.() -> Unit = {}) = htmlTag("picture", cssClasses, block)
fun Tag.figure(vararg cssClasses: String, block: Tag.() -> Unit = {}) = htmlTag("figure", cssClasses, block)
fun Tag.figcaption(vararg cssClasses: String, block: Tag.() -> Unit = {}) = htmlTag("figcaption", cssClasses, block)
fun Tag.iframe(vararg cssClasses: String, block: Tag.() -> Unit = {}) = htmlTag("iframe", cssClasses, block)
fun Tag.embed(block: Tag.() -> Unit = {}) = htmlTag("embed", emptyArray(), block)
fun Tag.objectTag(vararg cssClasses: String, block: Tag.() -> Unit = {}) = htmlTag("object", cssClasses, block)
fun Tag.details(vararg cssClasses: String, block: Tag.() -> Unit = {}) = htmlTag("details", cssClasses, block)
fun Tag.summary(vararg cssClasses: String, block: Tag.() -> Unit = {}) = htmlTag("summary", cssClasses, block)
fun Tag.dialog(vararg cssClasses: String, block: Tag.() -> Unit = {}) = htmlTag("dialog", cssClasses, block)
