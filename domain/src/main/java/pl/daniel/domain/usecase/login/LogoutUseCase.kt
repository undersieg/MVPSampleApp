package pl.daniel.domain.usecase.login

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import pl.daniel.domain.datasource.authentication.AuthenticationDataSource
import pl.daniel.domain.datasource.storage.StorageDataSource
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val authenticationDataSource: AuthenticationDataSource,
    private val storageDataSource: StorageDataSource
) {
    suspend fun execute(): Boolean = coroutineScope {
        val logout = async { authenticationDataSource.logout() }
        val clear = async { storageDataSource.clear() }

        logout.await() && clear.await()
    }
}