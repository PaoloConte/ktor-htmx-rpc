package pages

import io.paoloconte.htmx.dsl.*
import io.paoloconte.htmx.rpc.RpcRouting.Companion.rpc
import model.Contact
import model.ContactForm

fun Tag.contactRow(contact: Contact): Tag {
    return tr("contact-row") {
        id = "contact-${contact.id}"

        td("px-4", "py-3") { +contact.name }
        td("px-4", "py-3") { +contact.email }
        td("px-4", "py-3") { +contact.phone }
        td("px-4", "py-3") {
            button("btn-icon") {
                rpc = ContactPage::toggleFavorite
                hxVals = """{"contactId": "${contact.id}"}"""
                //hxSwap = "outerHTML"
                //hxTarget = "#contact-${contact.id}"

                +(if (contact.favorite) "★" else "☆")
            }
        }
        td("px-4", "py-3", "text-right") {
            button("btn", "btn-sm", "btn-primary") {
                rpc = ContactPage::startEdit
                hxVals = """{"contactId": "${contact.id}"}"""

                +"Edit"
            }
            button("btn", "btn-sm", "btn-danger", "ml-2") {
                rpc = ContactPage::deleteContact
                hxVals = """{"contactId": "${contact.id}"}"""
                hxConfirm = "Delete ${contact.name}?"
                +"Delete"
            }
        }
    }
}

fun Tag.contactEditRow(contact: Contact, errors: Map<String, String> = emptyMap()): Tag {
    return tr("contact-row", "editing") {
        id = "contact-${contact.id}"

        td("px-4", "py-2") {
            input(InputType.text, "name", "input") {
                id = "edit-name-${contact.id}"
                value = contact.name
            }
            errors["name"]?.let { span("text-red-500", "text-sm") { +it } }
        }
        td("px-4", "py-2") {
            input(InputType.email, "email", "input") {
                id = "edit-email-${contact.id}"
                value = contact.email
            }
            errors["email"]?.let { span("text-red-500", "text-sm") { +it } }
        }
        td("px-4", "py-2") {
            input(InputType.tel, "phone", "input") {
                id = "edit-phone-${contact.id}"
                value = contact.phone
            }
        }
        td("px-4", "py-2") {} // Empty favorite column during edit
        td("px-4", "py-2", "text-right") {
            button("btn", "btn-sm", "btn-success") {
                rpc = ContactPage::saveEdit
                hxInclude = "[id^='edit-'][id$='-${contact.id}']"
                hxVals = """{"contactId": "${contact.id}"}"""

                +"Save"
            }
            button("btn", "btn-sm", "btn-secondary", "ml-2") {
                rpc = ContactPage::cancelEdit
                hxVals = """{"contactId": "${contact.id}"}"""

                +"Cancel"
            }
        }
    }
}

fun Tag.contactTable(contacts: List<Contact>) {
    table("table", "w-full") {
        id = "contacts-table"
        thead {
            tr {
                th("px-4", "py-3", "text-left") { +"Name" }
                th("px-4", "py-3", "text-left") { +"Email" }
                th("px-4", "py-3", "text-left") { +"Phone" }
                th("px-4", "py-3", "text-left") { +"Fav" }
                th("px-4", "py-3", "text-right") { +"Actions" }
            }
        }
        tbody {
            id = "contacts-body"
            contacts.forEach { contactRow(it) }
        }
    }
}

fun Tag.addContactForm(form: ContactForm? = null, errors: Map<String, String> = emptyMap()): Tag {
    return div("card", "mb-4", "p-4") {
        id = "add-contact-form"

        h3("text-lg", "font-bold", "mb-3") { +"New Contact" }

        div("grid", "grid-cols-3", "gap-4") {
            div {
                label("block", "text-sm", "font-medium") { +"Name" }
                input(InputType.text, "new-name", "input", "w-full") {
                    id = "new-contact-name"
                    value = form?.name ?: ""
                    placeholder = "Mario Rossi"
                }
                errors["name"]?.let { span("text-red-500", "text-sm") { +it } }
            }
            div {
                label("block", "text-sm", "font-medium") { +"Email" }
                input(InputType.email, "new-email", "input", "w-full") {
                    id = "new-contact-email"
                    value = form?.email ?: ""
                    placeholder = "mario@example.com"
                }
                errors["email"]?.let { span("text-red-500", "text-sm") { +it } }
            }
            div {
                label("block", "text-sm", "font-medium") { +"Phone" }
                input(InputType.tel, "new-phone", "input", "w-full") {
                    id = "new-contact-phone"
                    value = form?.phone ?: ""
                    placeholder = "+39 333 ..."
                }
            }
        }

        div("mt-3") {
            button("btn", "btn-primary") {
                rpc = ContactPage::addContact
                hxInclude = "[id^='new-contact-']"

                +"Add"
            }
        }
    }
}

fun Tag.statsBar() {
    div("flex", "gap-4", "mb-4", "text-sm", "text-gray-600") {
        id = "stats-bar"

        span {
            id = "contact-count"
            rpc = ContactPage::getContactsCount
            hxTrigger = "load, contacts-updated from:body"
        }
        span {
            id = "favorite-count"
            rpc = ContactPage::getFavouriteCount
            hxTrigger = "load, contacts-updated from:body"
        }
    }
}

fun Tag.searchBar() {
    div("mb-4", "relative") {
        id = "search-bar"
        input(InputType.search, "query", "input", "w-full") {
            id = "search-input"
            placeholder = "Search contacts..."
            rpc = ContactPage::search
            hxTrigger = "input changed delay:300ms, search"
            hxInclude = "#search-input"
            hxIndicator = "#search-spinner"
        }
        span("htmx-indicator", "search-spinner", "mr-4") {
            id = "search-spinner"
            +"Searching..."
        }
    }
}
