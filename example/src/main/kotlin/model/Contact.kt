package model

data class Contact(
    val id: Int,
    val name: String,
    val email: String,
    val phone: String,
    val favorite: Boolean = false
)

data class ContactForm(
    val name: String,
    val email: String,
    val phone: String
) {
    fun validate(): Map<String, String> {
        val errors = mutableMapOf<String, String>()
        if (name.isBlank()) errors["name"] = "Name is required"
        if (email.isBlank()) errors["email"] = "Email is required"
        else if (!email.contains("@")) errors["email"] = "Invalid email"
        return errors
    }
}
