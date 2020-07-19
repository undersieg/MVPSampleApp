package pl.daniel.domain.validation.rules

class PasswordNotEmptyRule(private val password: String) : Rule {
    override fun valid(): Boolean = password.isNotEmpty()
}