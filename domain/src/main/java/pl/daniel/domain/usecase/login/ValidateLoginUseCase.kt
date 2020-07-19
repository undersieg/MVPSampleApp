package pl.daniel.domain.usecase.login

import pl.daniel.domain.entity.login.Login
import pl.daniel.domain.validation.ValidateService
import pl.daniel.domain.validation.rules.PasswordNotEmptyRule
import pl.daniel.domain.validation.rules.UserNotEmptyRule
import javax.inject.Inject

class ValidateLoginUseCase @Inject constructor(
    private val validateService: ValidateService
) {
    fun execute(login: Login) = validateService
        .clear()
        .addRule(PasswordNotEmptyRule(login.password))
        .addRule(UserNotEmptyRule(login.userName))
        .validate()

}