package io.paoloconte.htmx.dsl
enum class InputType(val value: String) {
    text("text"),
    email("email"),
    password("password"),
    number("number"),
    tel("tel"),
    url("url"),
    search("search"),
    date("date"),
    time("time"),
    hidden("hidden"),
    checkbox("checkbox"),
    radio("radio"),
    file("file"),
    color("color"),
    range("range"),
    submit("submit"),
    reset("reset"),
    button("button");
}

enum class ButtonType(val value: String) {
    button("button"),
    submit("submit"),
    reset("reset");
}

var Tag.id: String
    get() = attributes["id"] ?: ""
    set(value) { attributes["id"] = value }

var Tag.name: String
    get() = attributes["name"] ?: ""
    set(value) { attributes["name"] = value }

var Tag.value: String
    get() = attributes["value"] ?: ""
    set(value) { attributes["value"] = value }

var Tag.placeholder: String
    get() = attributes["placeholder"] ?: ""
    set(value) { attributes["placeholder"] = value }

var Tag.src: String
    get() = attributes["src"] ?: ""
    set(value) { attributes["src"] = value }

var Tag.href: String
    get() = attributes["href"] ?: ""
    set(value) { attributes["href"] = value }

var Tag.action: String
    get() = attributes["action"] ?: ""
    set(value) { attributes["action"] = value }

var Tag.method: String
    get() = attributes["method"] ?: ""
    set(value) { attributes["method"] = value }

var Tag.colSpan: String
    get() = attributes["colspan"] ?: ""
    set(value) { attributes["colspan"] = value }

var Tag.rowSpan: String
    get() = attributes["rowspan"] ?: ""
    set(value) { attributes["rowspan"] = value }

var Tag.role: String
    get() = attributes["role"] ?: ""
    set(value) { attributes["role"] = value }

var Tag.tabIndex: String
    get() = attributes["tabindex"] ?: ""
    set(value) { attributes["tabindex"] = value }

var Tag.alt: String
    get() = attributes["alt"] ?: ""
    set(value) { attributes["alt"] = value }

var Tag.target: String
    get() = attributes["target"] ?: ""
    set(value) { attributes["target"] = value }

var Tag.rel: String
    get() = attributes["rel"] ?: ""
    set(value) { attributes["rel"] = value }

var Tag.charset: String
    get() = attributes["charset"] ?: ""
    set(value) { attributes["charset"] = value }

var Tag.content: String
    get() = attributes["content"] ?: ""
    set(value) { attributes["content"] = value }

var Tag.type: String
    get() = attributes["type"] ?: ""
    set(value) { attributes["type"] = value }

var Tag.forAttr: String
    get() = attributes["for"] ?: ""
    set(value) { attributes["for"] = value }

var Tag.disabled: Boolean
    get() = "disabled" in attributes
    set(value) { if (value) attributes["disabled"] = "disabled" else attributes.remove("disabled") }

var Tag.checked: Boolean
    get() = "checked" in attributes
    set(value) { if (value) attributes["checked"] = "checked" else attributes.remove("checked") }

var Tag.readonly: Boolean
    get() = "readonly" in attributes
    set(value) { if (value) attributes["readonly"] = "readonly" else attributes.remove("readonly") }

var Tag.required: Boolean
    get() = "required" in attributes
    set(value) { if (value) attributes["required"] = "required" else attributes.remove("required") }

var Tag.classes: Set<String>
    get() = attributes["class"]?.split(" ")?.filter { it.isNotBlank() }?.toSet() ?: emptySet()
    set(value) {
        val joined = value.joinToString(" ")
        if (joined.isNotBlank()) attributes["class"] = joined else attributes.remove("class")
    }

fun Tag.classes(vararg names: String) {
    val existing = classes
    classes = existing + names.toSet()
}

fun Tag.data(key: String, value: String) {
    attributes["data-$key"] = value
}
