package pages

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.paoloconte.htmx.dsl.*
import io.paoloconte.htmx.dsl.SwapStrategy.*
import io.paoloconte.htmx.rpc.RpcResponse
import kotlinx.coroutines.delay
import model.ContactForm
import service.ContactRepository

object ContactPage {

    suspend fun startEdit(call: ApplicationCall): RpcResponse {
        val params = call.receiveParameters()
        val id = params["contactId"]?.toIntOrNull() ?: error("no contact id found")
        val contact = ContactRepository.get(id)
        return {
            replace { contactEditRow(contact) }
        }
    }

    suspend fun cancelEdit(call: ApplicationCall): RpcResponse {
        val params = call.receiveParameters()
        val id = params["contactId"]?.toIntOrNull() ?: error("no contact id found")
        val contact = ContactRepository.get(id)
        return {
            replace { contactRow(contact) }
        }
    }

    suspend fun saveEdit(call: ApplicationCall): RpcResponse {
        val params = call.receiveParameters()
        val id = params["contactId"]?.toIntOrNull() ?: error("no contact id found")
        val form = ContactForm(
            name = params["name"] ?: error("no name found"),
            email = params["email"] ?: error("no email found"),
            phone = params["phone"] ?: ""
        )

        val errors = form.validate()

        if (errors.isNotEmpty()) {
            val contact = ContactRepository.get(id)
            return {
                replace {
                    contactEditRow(contact.copy(
                        name = form.name,
                        email = form.email,
                        phone = form.phone
                    ), errors)
                }
                toast("Fix the errors", "error")
            }
        }

        val updated = ContactRepository.update(id, form)

        return {
            replace { contactRow(updated) }
            triggerEvent("contacts-updated")
            toast("${updated.name} updated!", "success")
        }
    }

    suspend fun addContact(call: ApplicationCall): RpcResponse {
        val params = call.receiveParameters()
        val form = ContactForm(
            name = params["new-name"] ?: "",
            email = params["new-email"] ?: "",
            phone = params["new-phone"] ?: ""
        )

        val errors = form.validate()

        if (errors.isNotEmpty()) {
            return {
                replace { addContactForm(form, errors) }
                toast("Fix the errors", "error")
            }
        }

        val contact = ContactRepository.create(form)

        return {
            tbody("contacts-body", BeforeEnd) { contactRow(contact) }
            replace { addContactForm() }
            triggerEvent("contacts-updated")
            toast("${contact.name} added!", "success")
        }
    }

    suspend fun deleteContact(call: ApplicationCall): RpcResponse {
        val params = call.receiveParameters()
        val id = params["contactId"]?.toIntOrNull() ?: error("no contact id found")
        val contact = ContactRepository.get(id)
        ContactRepository.delete(id)

        return {
            removeElement("#contact-$id")
            triggerEvent("contacts-updated")
            toast("${contact.name} deleted", "warning")
        }
    }

    suspend fun toggleFavorite(call: ApplicationCall): RpcResponse {
        val params = call.receiveParameters()
        val id = params["contactId"]?.toIntOrNull() ?: error("no contact id found")
        val contact = ContactRepository.toggleFavorite(id)

        return {
            // example of using retargeting instead of OOB
            contactRow(contact).also {
                retarget("#${it.id}")
                reswap("outerHTML")
            }
            triggerEvent("contacts-updated")
        }
    }

    suspend fun search(call: ApplicationCall): RpcResponse {
        val params = call.receiveParameters()
        val query = params["query"] ?: ""
        delay(500) // simulate database call
        val results = if (query.isBlank()) {
            ContactRepository.findAll()
        } else {
            ContactRepository.search(query)
        }

        return {
            tbody("contacts-body", InnerHTML) {
                if (results.isEmpty()) {
                    tr {
                        td {
                            colSpan = "5"
                            classes("px-4", "py-8", "text-center", "text-gray-500")
                            +"No contacts found for \"$query\""
                        }
                    }
                } else {
                    results.forEach { contactRow(it) }
                }
            }
        }
    }

    suspend fun getFavouriteCount(call: ApplicationCall): RpcResponse {
        return {
            reswap("innerHTML")
            +"Favorites: ${ContactRepository.favoriteCount()}"
        }
    }

    suspend fun getContactsCount(call: ApplicationCall): RpcResponse {
        return {
            reswap("innerHTML")
            +"Contacts: ${ContactRepository.count()}"
        }
    }

}

/** Shows a toast notification */
fun HtmxResponseBuilder.toast(message: String, type: String = "info") {
    div("toast-area", AfterBegin) {
        div("toast", "toast-$type") {
            data("auto-dismiss", "3000")
            +message
        }
    }
}
