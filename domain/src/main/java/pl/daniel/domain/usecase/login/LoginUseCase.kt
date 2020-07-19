package pl.daniel.domain.usecase.login

import pl.daniel.domain.datasource.authentication.AuthenticationDataSource
import pl.daniel.domain.entity.login.Login
import pl.daniel.domain.entity.login.LoginResultType
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authenticationDataSource: AuthenticationDataSource
) {
    suspend fun execute(login: Login): LoginResultType = with(authenticationDataSource) {
        val token = login(login)

        storeAccessToken(token.accessToken)
        storeRefreshToken(token.refreshToken)
        LoginResultType.VALID
    }
}