package pl.daniel.presenter.launch

import androidx.annotation.VisibleForTesting
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pl.daniel.domain.usecase.login.IsUserLoggedInUseCase
import pl.daniel.presenter.base.BasePresenter
import timber.log.Timber
import javax.inject.Inject

class LaunchPresenter @Inject constructor(
    private val isUserLoggedInUseCase: IsUserLoggedInUseCase
) : BasePresenter<LaunchView>() {

    override fun onFirstBind() {
        super.onFirstBind()
        waitForSplashScreen()
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun waitForSplashScreen() = launch {
        Timber.i("Wait for splash screen!")
        delay(SPLASH_SCREEN_DISPLAY_TIME)
        checkIfUserLoggedIn()
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun checkIfUserLoggedIn() = launch {
        Timber.i("Check if user logged in!")
        try {
            checkIfUserLoggedInSuccess(
                isUserLoggedInUseCase.execute()
            )
        } catch (throwable: Throwable) {
            checkIfUserLoggedInFailed(throwable)
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun checkIfUserLoggedInSuccess(userLoggedIn: Boolean) {
        Timber.i("Check if user logged in success!")
        present {
            if (userLoggedIn) displayHome()
            else displayLogin()
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun checkIfUserLoggedInFailed(throwable: Throwable) {
        Timber.e(throwable, "Check if user logged in failed!")
    }

    companion object {
        @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
        const val SPLASH_SCREEN_DISPLAY_TIME = 2000L
    }
}