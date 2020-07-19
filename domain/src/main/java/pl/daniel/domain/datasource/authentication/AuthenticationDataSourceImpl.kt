package pl.daniel.domain.datasource.authentication

import pl.daniel.data.preferences.PreferenceStorage
import pl.daniel.domain.entity.login.Login
import pl.daniel.domain.entity.login.Token
import javax.inject.Inject

class AuthenticationDataSourceImpl @Inject constructor(
    private val preferenceStorage: PreferenceStorage
) : AuthenticationDataSource {

    override suspend fun login(login: Login): Token = Token(
        dummyToken,
        dummyToken
    )

    override suspend fun logout(): Boolean {
        //todo logout from backend
        return true
    }

    override suspend fun storeAccessToken(token: String) {
        preferenceStorage.accessToken = token
    }

    override suspend fun restoreAccessToken(): String = preferenceStorage.accessToken

    override suspend fun storeRefreshToken(token: String) {
        preferenceStorage.refreshToken = token
    }

    override suspend fun restoreRefreshToken(): String = preferenceStorage.refreshToken

    companion object {
        private const val dummyToken = "adsf786SDFSewewqfasdFASDFewqfrqfew#4313r"
    }
}