package service

import model.Contact
import model.ContactForm

object ContactRepository {
    private var nextId = 1
    private val contacts = mutableMapOf<Int, Contact>()

    init {
        // Sample contacts
        listOf(
            ContactForm("Mario Rossi", "mario.rossi@example.com", "+39 333 1234567"),
            ContactForm("Giulia Bianchi", "giulia.bianchi@example.com", "+39 347 9876543"),
            ContactForm("Luca Verdi", "luca.verdi@example.com", "+39 320 5551234"),
            ContactForm("Francesca Russo", "francesca.russo@example.com", "+39 338 4445678"),
            ContactForm("Alessandro Conti", "alessandro.conti@example.com", "+39 351 7778899"),
        ).forEach { create(it) }

        // Mark one as favorite
        toggleFavorite(1)
    }

    fun findAll(): List<Contact> = contacts.values.sortedBy { it.id }

    fun get(id: Int): Contact =
        contacts[id] ?: throw NoSuchElementException("Contact not found: $id")

    fun create(form: ContactForm): Contact {
        val contact = Contact(
            id = nextId++,
            name = form.name,
            email = form.email,
            phone = form.phone
        )
        contacts[contact.id] = contact
        return contact
    }

    fun update(id: Int, form: ContactForm): Contact {
        val existing = get(id)
        val updated = existing.copy(
            name = form.name,
            email = form.email,
            phone = form.phone
        )
        contacts[id] = updated
        return updated
    }

    fun delete(id: Int) {
        contacts.remove(id) ?: throw NoSuchElementException("Contact not found: $id")
    }

    fun toggleFavorite(id: Int): Contact {
        val existing = get(id)
        val toggled = existing.copy(favorite = !existing.favorite)
        contacts[id] = toggled
        return toggled
    }

    fun search(query: String): List<Contact> {
        val lower = query.lowercase()
        return contacts.values.filter {
            it.name.lowercase().contains(lower) ||
                it.email.lowercase().contains(lower) ||
                it.phone.contains(lower)
        }.sortedBy { it.id }
    }

    fun count(): Int = contacts.size

    fun favoriteCount(): Int = contacts.values.count { it.favorite }
}
