package pl.daniel.presenter.login

import androidx.annotation.VisibleForTesting
import kotlinx.coroutines.launch
import pl.daniel.data.extension.empty
import pl.daniel.domain.entity.login.Login
import pl.daniel.domain.entity.login.LoginResultType
import pl.daniel.domain.usecase.login.LoginUseCase
import pl.daniel.domain.usecase.login.ValidateLoginUseCase
import pl.daniel.presenter.base.BasePresenter
import timber.log.Timber
import javax.inject.Inject

class LoginPresenter @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val validateLoginUseCase: ValidateLoginUseCase
) : BasePresenter<LoginView>() {

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    var currentLogin: String = String.empty

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    var currentPassword: String = String.empty

    fun loginChanged(login: String) {
        Timber.i("Login changed!")
        currentLogin = login
        present {
            hideLoginErrorIfNeeded()
        }
    }

    fun passwordChanged(password: String) {
        Timber.i("Password changed!")
        currentPassword = password
        present {
            hidePasswordErrorIfNeeded()
        }
    }

    fun loginClicked() {
        Timber.i("Login clicked!")
        validateLogin(
            Login(currentLogin, currentPassword)
        )
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun validateLogin(login: Login) = launch {
        Timber.i("Validate password!")
        try {
            validateLoginSuccess(
                validateLoginUseCase.execute(login),
                login
            )
        } catch (throwable: Throwable) {
            validateLoginFailed(throwable)
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun validateLoginSuccess(loginValid: Boolean, login: Login) {
        Timber.i("Validate login success!")
        if (loginValid) loginUser(login)
        else showIncorrectLogin()
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun showIncorrectLogin() {
        Timber.i("Show incorrect login!")
        present {
            if (currentLogin.isEmpty()) showLoginError()

            if (currentPassword.isEmpty()) showPasswordError()
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun validateLoginFailed(throwable: Throwable) {
        Timber.e(throwable, "Validate login failed!")
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun loginUser(login: Login) = launch {
        Timber.i("Login user!")
        try {
            loginUserSuccess(
                loginUseCase.execute(login)
            )
        } catch (throwable: Throwable) {
            loginUserFailed(throwable)
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun loginUserSuccess(loginResultType: LoginResultType) {
        Timber.i("Login result!")
        present {
            if (loginResultType == LoginResultType.VALID) displayHome()
            else showIncorrectLogin()
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun loginUserFailed(throwable: Throwable) {
        Timber.e(throwable, "Login user failed!")
        showIncorrectLogin()
    }
}