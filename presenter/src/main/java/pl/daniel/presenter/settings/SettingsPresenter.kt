package pl.daniel.presenter.settings

import kotlinx.coroutines.launch
import pl.daniel.domain.usecase.login.LogoutUseCase
import pl.daniel.presenter.base.BasePresenter
import timber.log.Timber
import javax.inject.Inject

class SettingsPresenter @Inject constructor(
    private val logoutUseCase: LogoutUseCase
) : BasePresenter<SettingsView>() {

    fun logoutClicked() {
        Timber.i("Logout clicked!")
        logoutUser()
    }

    private fun logoutUser() = launch {
        Timber.i("Logout user!")
        try {
            logoutUserSuccess(
                logoutUseCase.execute()
            )
        } catch (throwable: Throwable) {
            logoutUserFailed(throwable)
        }
    }

    private fun logoutUserSuccess(logout: Boolean) {
        Timber.i("Logout user success!")
        if (!logout) return
        present { displayLogin() }
    }

    private fun logoutUserFailed(throwable: Throwable) {
        Timber.e(throwable, "Logout user failed!")
    }


}