package pages

import io.ktor.http.*
import io.paoloconte.htmx.dsl.*
import io.paoloconte.htmx.dsl.SwapStrategy.*
import io.paoloconte.htmx.rpc.RpcResponse
import io.paoloconte.htmx.rpc.RpcRouting
import kotlinx.coroutines.delay
import model.ContactForm
import service.ContactRepository

object ContactPage {

    suspend fun startEdit(params: Parameters): RpcResponse {
        val id = params["contactId"]?.toIntOrNull() ?: error("no contact id found")
        val contact = ContactRepository.get(id)
        return {
            replace { contactEditRow(contact) }
        }
    }

    suspend fun cancelEdit(params: Parameters): RpcResponse {
        val id = params["contactId"]?.toIntOrNull() ?: error("no contact id found")
        val contact = ContactRepository.get(id)
        return {
            replace { contactRow(contact) }
        }
    }

    suspend fun saveEdit(params: Parameters): RpcResponse {
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

    suspend fun addContact(params: Parameters): RpcResponse {
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

    suspend fun deleteContact(params: Parameters): RpcResponse {
        val id = params["contactId"]?.toIntOrNull() ?: error("no contact id found")
        val contact = ContactRepository.get(id)
        ContactRepository.delete(id)

        return {
            removeElement("#contact-$id")
            triggerEvent("contacts-updated")
            toast("${contact.name} deleted", "warning")
        }
    }

    suspend fun toggleFavorite(params: Parameters): RpcResponse {
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

    suspend fun search(params: Parameters): RpcResponse {
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

    suspend fun getFavouriteCount(params: Parameters): RpcResponse {
        return {
            reswap("innerHTML")
            +"Favorites: ${ContactRepository.favoriteCount()}"
        }
    }

    suspend fun getContactsCount(params: Parameters): RpcResponse {
        return {
            reswap("innerHTML")
            +"Contacts: ${ContactRepository.count()}"
        }
    }

    context(rpc: RpcRouting)
    fun register() = with(rpc) {
        action(::startEdit)
        action(::cancelEdit)
        action(::saveEdit)
        action(::addContact)
        action(::deleteContact)
        action(::toggleFavorite)
        action(::search)
        action(::getFavouriteCount)
        action(::getContactsCount)
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
