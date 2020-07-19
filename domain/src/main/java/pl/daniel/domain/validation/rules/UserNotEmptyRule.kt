package pl.daniel.domain.validation.rules

class UserNotEmptyRule(private val user: String) : Rule {
    override fun valid(): Boolean = user.isNotEmpty()

}