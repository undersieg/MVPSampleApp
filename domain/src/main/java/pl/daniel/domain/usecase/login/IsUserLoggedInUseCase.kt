package pl.daniel.domain.usecase.login

import pl.daniel.domain.datasource.authentication.AuthenticationDataSource
import javax.inject.Inject

class IsUserLoggedInUseCase @Inject constructor(
    private val authenticationDataSource: AuthenticationDataSource
) {
    suspend fun execute(): Boolean =
        authenticationDataSource.restoreAccessToken().isNotEmpty() //todo validate token with backend
}