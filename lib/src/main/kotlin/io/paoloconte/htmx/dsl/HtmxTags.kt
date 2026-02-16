package io.paoloconte.htmx.dsl

// Helper to create a tag with id and hx-swap-oob
private fun Tag.htmxTag(name: String, id: String, oobSwap: SwapStrategy, block: Tag.() -> Unit): Tag {
    val child = HtmlTag(name)
    child.attributes["id"] = id
    child.attributes["hx-swap-oob"] = oobSwap.value
    child.block()
    children.add(child)
    return child
}

// Document structure
fun Tag.html(id: String, oobSwap: SwapStrategy, block: Tag.() -> Unit = {}) = htmxTag("html", id, oobSwap, block)
fun Tag.head(id: String, oobSwap: SwapStrategy, block: Tag.() -> Unit = {}) = htmxTag("head", id, oobSwap, block)
fun Tag.body(id: String, oobSwap: SwapStrategy, block: Tag.() -> Unit = {}) = htmxTag("body", id, oobSwap, block)
fun Tag.title(id: String, oobSwap: SwapStrategy, block: Tag.() -> Unit = {}) = htmxTag("title", id, oobSwap, block)
fun Tag.style(id: String, oobSwap: SwapStrategy, block: Tag.() -> Unit = {}) = htmxTag("style", id, oobSwap, block)
fun Tag.noscript(id: String, oobSwap: SwapStrategy, block: Tag.() -> Unit = {}) = htmxTag("noscript", id, oobSwap, block)

// Sections
fun Tag.header(id: String, oobSwap: SwapStrategy, block: Tag.() -> Unit = {}) = htmxTag("header", id, oobSwap, block)
fun Tag.footer(id: String, oobSwap: SwapStrategy, block: Tag.() -> Unit = {}) = htmxTag("footer", id, oobSwap, block)
fun Tag.main(id: String, oobSwap: SwapStrategy, block: Tag.() -> Unit = {}) = htmxTag("main", id, oobSwap, block)
fun Tag.section(id: String, oobSwap: SwapStrategy, block: Tag.() -> Unit = {}) = htmxTag("section", id, oobSwap, block)
fun Tag.article(id: String, oobSwap: SwapStrategy, block: Tag.() -> Unit = {}) = htmxTag("article", id, oobSwap, block)
fun Tag.aside(id: String, oobSwap: SwapStrategy, block: Tag.() -> Unit = {}) = htmxTag("aside", id, oobSwap, block)
fun Tag.nav(id: String, oobSwap: SwapStrategy, block: Tag.() -> Unit = {}) = htmxTag("nav", id, oobSwap, block)

// Content grouping
fun Tag.div(id: String, oobSwap: SwapStrategy, block: Tag.() -> Unit = {}) = htmxTag("div", id, oobSwap, block)
fun Tag.h1(id: String, oobSwap: SwapStrategy, block: Tag.() -> Unit = {}) = htmxTag("h1", id, oobSwap, block)
fun Tag.h2(id: String, oobSwap: SwapStrategy, block: Tag.() -> Unit = {}) = htmxTag("h2", id, oobSwap, block)
fun Tag.h3(id: String, oobSwap: SwapStrategy, block: Tag.() -> Unit = {}) = htmxTag("h3", id, oobSwap, block)
fun Tag.h4(id: String, oobSwap: SwapStrategy, block: Tag.() -> Unit = {}) = htmxTag("h4", id, oobSwap, block)
fun Tag.h5(id: String, oobSwap: SwapStrategy, block: Tag.() -> Unit = {}) = htmxTag("h5", id, oobSwap, block)
fun Tag.h6(id: String, oobSwap: SwapStrategy, block: Tag.() -> Unit = {}) = htmxTag("h6", id, oobSwap, block)
fun Tag.p(id: String, oobSwap: SwapStrategy, block: Tag.() -> Unit = {}) = htmxTag("p", id, oobSwap, block)
fun Tag.span(id: String, oobSwap: SwapStrategy, block: Tag.() -> Unit = {}) = htmxTag("span", id, oobSwap, block)
fun Tag.strong(id: String, oobSwap: SwapStrategy, block: Tag.() -> Unit = {}) = htmxTag("strong", id, oobSwap, block)
fun Tag.em(id: String, oobSwap: SwapStrategy, block: Tag.() -> Unit = {}) = htmxTag("em", id, oobSwap, block)
fun Tag.b(id: String, oobSwap: SwapStrategy, block: Tag.() -> Unit = {}) = htmxTag("b", id, oobSwap, block)
fun Tag.i(id: String, oobSwap: SwapStrategy, block: Tag.() -> Unit = {}) = htmxTag("i", id, oobSwap, block)
fun Tag.u(id: String, oobSwap: SwapStrategy, block: Tag.() -> Unit = {}) = htmxTag("u", id, oobSwap, block)
fun Tag.small(id: String, oobSwap: SwapStrategy, block: Tag.() -> Unit = {}) = htmxTag("small", id, oobSwap, block)
fun Tag.mark(id: String, oobSwap: SwapStrategy, block: Tag.() -> Unit = {}) = htmxTag("mark", id, oobSwap, block)
fun Tag.code(id: String, oobSwap: SwapStrategy, block: Tag.() -> Unit = {}) = htmxTag("code", id, oobSwap, block)
fun Tag.pre(id: String, oobSwap: SwapStrategy, block: Tag.() -> Unit = {}) = htmxTag("pre", id, oobSwap, block)
fun Tag.blockquote(id: String, oobSwap: SwapStrategy, block: Tag.() -> Unit = {}) = htmxTag("blockquote", id, oobSwap, block)

// Lists
fun Tag.ul(id: String, oobSwap: SwapStrategy, block: Tag.() -> Unit = {}) = htmxTag("ul", id, oobSwap, block)
fun Tag.ol(id: String, oobSwap: SwapStrategy, block: Tag.() -> Unit = {}) = htmxTag("ol", id, oobSwap, block)
fun Tag.li(id: String, oobSwap: SwapStrategy, block: Tag.() -> Unit = {}) = htmxTag("li", id, oobSwap, block)
fun Tag.dl(id: String, oobSwap: SwapStrategy, block: Tag.() -> Unit = {}) = htmxTag("dl", id, oobSwap, block)
fun Tag.dt(id: String, oobSwap: SwapStrategy, block: Tag.() -> Unit = {}) = htmxTag("dt", id, oobSwap, block)
fun Tag.dd(id: String, oobSwap: SwapStrategy, block: Tag.() -> Unit = {}) = htmxTag("dd", id, oobSwap, block)

// Table
fun Tag.table(id: String, oobSwap: SwapStrategy, block: Tag.() -> Unit = {}) = htmxTag("table", id, oobSwap, block)
fun Tag.thead(id: String, oobSwap: SwapStrategy, block: Tag.() -> Unit = {}) = htmxTag("thead", id, oobSwap, block)
fun Tag.tbody(id: String, oobSwap: SwapStrategy, block: Tag.() -> Unit = {}) = htmxTag("tbody", id, oobSwap, block)
fun Tag.tfoot(id: String, oobSwap: SwapStrategy, block: Tag.() -> Unit = {}) = htmxTag("tfoot", id, oobSwap, block)
fun Tag.tr(id: String, oobSwap: SwapStrategy, block: Tag.() -> Unit = {}) = htmxTag("tr", id, oobSwap, block)
fun Tag.th(id: String, oobSwap: SwapStrategy, block: Tag.() -> Unit = {}) = htmxTag("th", id, oobSwap, block)
fun Tag.td(id: String, oobSwap: SwapStrategy, block: Tag.() -> Unit = {}) = htmxTag("td", id, oobSwap, block)
fun Tag.caption(id: String, oobSwap: SwapStrategy, block: Tag.() -> Unit = {}) = htmxTag("caption", id, oobSwap, block)
fun Tag.colgroup(id: String, oobSwap: SwapStrategy, block: Tag.() -> Unit = {}) = htmxTag("colgroup", id, oobSwap, block)

// Forms
fun Tag.form(id: String, oobSwap: SwapStrategy, block: Tag.() -> Unit = {}) = htmxTag("form", id, oobSwap, block)
fun Tag.select(id: String, oobSwap: SwapStrategy, block: Tag.() -> Unit = {}) = htmxTag("select", id, oobSwap, block)
fun Tag.option(id: String, oobSwap: SwapStrategy, block: Tag.() -> Unit = {}) = htmxTag("option", id, oobSwap, block)
fun Tag.optgroup(id: String, oobSwap: SwapStrategy, block: Tag.() -> Unit = {}) = htmxTag("optgroup", id, oobSwap, block)
fun Tag.textarea(id: String, oobSwap: SwapStrategy, block: Tag.() -> Unit = {}) = htmxTag("textarea", id, oobSwap, block)
fun Tag.label(id: String, oobSwap: SwapStrategy, block: Tag.() -> Unit = {}) = htmxTag("label", id, oobSwap, block)
fun Tag.fieldset(id: String, oobSwap: SwapStrategy, block: Tag.() -> Unit = {}) = htmxTag("fieldset", id, oobSwap, block)
fun Tag.legend(id: String, oobSwap: SwapStrategy, block: Tag.() -> Unit = {}) = htmxTag("legend", id, oobSwap, block)

// Media & interactive
fun Tag.audio(id: String, oobSwap: SwapStrategy, block: Tag.() -> Unit = {}) = htmxTag("audio", id, oobSwap, block)
fun Tag.video(id: String, oobSwap: SwapStrategy, block: Tag.() -> Unit = {}) = htmxTag("video", id, oobSwap, block)
fun Tag.canvas(id: String, oobSwap: SwapStrategy, block: Tag.() -> Unit = {}) = htmxTag("canvas", id, oobSwap, block)
fun Tag.picture(id: String, oobSwap: SwapStrategy, block: Tag.() -> Unit = {}) = htmxTag("picture", id, oobSwap, block)
fun Tag.figure(id: String, oobSwap: SwapStrategy, block: Tag.() -> Unit = {}) = htmxTag("figure", id, oobSwap, block)
fun Tag.figcaption(id: String, oobSwap: SwapStrategy, block: Tag.() -> Unit = {}) = htmxTag("figcaption", id, oobSwap, block)
fun Tag.iframe(id: String, oobSwap: SwapStrategy, block: Tag.() -> Unit = {}) = htmxTag("iframe", id, oobSwap, block)
fun Tag.objectTag(id: String, oobSwap: SwapStrategy, block: Tag.() -> Unit = {}) = htmxTag("object", id, oobSwap, block)
fun Tag.details(id: String, oobSwap: SwapStrategy, block: Tag.() -> Unit = {}) = htmxTag("details", id, oobSwap, block)
fun Tag.summary(id: String, oobSwap: SwapStrategy, block: Tag.() -> Unit = {}) = htmxTag("summary", id, oobSwap, block)
fun Tag.dialog(id: String, oobSwap: SwapStrategy, block: Tag.() -> Unit = {}) = htmxTag("dialog", id, oobSwap, block)
