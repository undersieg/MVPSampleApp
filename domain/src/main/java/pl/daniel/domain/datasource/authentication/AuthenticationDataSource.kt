package pl.daniel.domain.datasource.authentication

import pl.daniel.domain.entity.login.Login
import pl.daniel.domain.entity.login.Token

interface AuthenticationDataSource {

    suspend fun login(login: Login): Token
    suspend fun logout(): Boolean
    suspend fun storeAccessToken(token: String)
    suspend fun restoreAccessToken(): String
    suspend fun storeRefreshToken(token: String)
    suspend fun restoreRefreshToken(): String
}