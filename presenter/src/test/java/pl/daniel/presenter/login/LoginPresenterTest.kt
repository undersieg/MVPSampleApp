package pl.daniel.presenter.login

import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import pl.daniel.data.extension.empty
import pl.daniel.data.CoroutineTestRule
import pl.daniel.domain.entity.login.Login
import pl.daniel.domain.entity.login.LoginResultType
import pl.daniel.domain.usecase.login.LoginUseCase
import pl.daniel.domain.usecase.login.ValidateLoginUseCase
import timber.log.Timber

@ExperimentalCoroutinesApi
class LoginPresenterTest {

    private val dummyError = RuntimeException("Dummy exception")
    private val dummyUserLogin = "dummy login"
    private val dummyPassword = "dsfsfh34523fweD"
    private val dummyLogin = Login(dummyUserLogin, dummyPassword)

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @RelaxedMockK
    lateinit var view: LoginView

    @MockK
    lateinit var loginUseCase: LoginUseCase

    @MockK
    lateinit var validateLoginUseCase: ValidateLoginUseCase

    private lateinit var presenter: LoginPresenter

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        presenter = spyk(
            LoginPresenter(
                loginUseCase,
                validateLoginUseCase
            )
        )
        presenter.view = view

        every { presenter.coroutineContext } returns coroutineTestRule.testDispatcher

        excludeRecords {
            presenter.view = any()
            presenter.coroutineContext
        }
        mockkStatic(Timber::class)
    }

    @Test
    fun `loginChanged success`() {
        presenter.currentLogin = String.empty

        presenter.loginChanged(dummyUserLogin)

        verify {
            Timber.i(any<String>())
            view.hideLoginErrorIfNeeded()
        }

        assertEquals(dummyUserLogin, presenter.currentLogin)
    }

    @Test
    fun `passwordChanged success`() {
        presenter.currentPassword = String.empty

        presenter.passwordChanged(dummyPassword)

        verify {
            Timber.i(any<String>())
            view.hidePasswordErrorIfNeeded()
        }

        assertEquals(dummyPassword, presenter.currentPassword)
    }

    @Test
    fun `loginClicked success`() {
        presenter.currentLogin = dummyUserLogin
        presenter.currentPassword = dummyPassword
        every { presenter.validateLogin(any()) } returns Job()

        presenter.loginClicked()

        verify {
            Timber.i(any<String>())
            presenter.validateLogin(dummyLogin)
        }
    }

    @Test
    fun `validateLogin success`() = runBlockingTest {
        coEvery { validateLoginUseCase.execute(any()) } returns true
        coEvery { presenter.validateLoginSuccess(any(), any()) } just Runs

        presenter.validateLogin(dummyLogin)

        coVerify {
            Timber.i(any<String>())
            validateLoginUseCase.execute(dummyLogin)
            presenter.validateLoginSuccess(true, dummyLogin)
        }
        coVerify(exactly = 0) {
            presenter.validateLoginFailed(any())
        }
    }

    @Test
    fun `validateLogin failed`() = runBlockingTest {
        coEvery { validateLoginUseCase.execute(any()) } throws dummyError
        coEvery { presenter.validateLoginFailed(any()) } just Runs

        presenter.validateLogin(dummyLogin)

        coVerify {
            Timber.i(any<String>())
            validateLoginUseCase.execute(dummyLogin)
            presenter.validateLoginFailed(dummyError)
        }
        coVerify(exactly = 0) {
            presenter.validateLoginSuccess(any(), any())
        }
    }

    @Test
    fun `validateLoginSuccess login valid`() {
        every { presenter.loginUser(any()) } returns Job()

        presenter.validateLoginSuccess(true, dummyLogin)

        verify {
            Timber.i(any<String>())
            presenter.loginUser(dummyLogin)
        }
        verify(exactly = 0) {
            presenter.showIncorrectLogin()
        }
    }

    @Test
    fun `validateLoginSuccess login not valid`() {
        every { presenter.showIncorrectLogin() } just Runs

        presenter.validateLoginSuccess(false, dummyLogin)

        verify {
            Timber.i(any<String>())
            presenter.showIncorrectLogin()
        }
        verify(exactly = 0) {
            presenter.loginUser(any())
        }
    }

    @Test
    fun `showIncorrectLogin login empty`() {
        presenter.currentLogin = String.empty
        presenter.currentPassword = dummyPassword

        presenter.showIncorrectLogin()

        verify {
            Timber.i(any<String>())
            view.showLoginError()
        }
        verify(exactly = 0) {
            view.showPasswordError()
        }
    }

    @Test
    fun `showIncorrectLogin password empty`() {
        presenter.currentLogin = dummyUserLogin
        presenter.currentPassword = String.empty

        presenter.showIncorrectLogin()

        verify {
            Timber.i(any<String>())
            view.showPasswordError()
        }
        verify(exactly = 0) {
            view.showLoginError()
        }
    }

    @Test
    fun `validateLoginFailed success`() {
        presenter.validateLoginFailed(dummyError)

        verify {
            Timber.e(dummyError, any())
        }
    }

    @Test
    fun `loginUser success`() = runBlockingTest {
        coEvery { loginUseCase.execute(any()) } returns LoginResultType.VALID
        coEvery { presenter.loginUserSuccess(any()) } just Runs

        presenter.loginUser(dummyLogin)

        coVerify {
            Timber.i(any<String>())
            loginUseCase.execute(dummyLogin)
            presenter.loginUserSuccess(LoginResultType.VALID)
        }
        coVerify(exactly = 0) {
            presenter.loginUserFailed(any())
        }
    }

    @Test
    fun `loginUser failed`() = runBlockingTest {
        coEvery { loginUseCase.execute(any()) } throws dummyError
        coEvery { presenter.loginUserFailed(any()) } just Runs

        presenter.loginUser(dummyLogin)

        coVerify {
            Timber.i(any<String>())
            loginUseCase.execute(dummyLogin)
            presenter.loginUserFailed(dummyError)
        }
        coVerify(exactly = 0) {
            presenter.loginUserSuccess(any())
        }
    }

    @Test
    fun `loginUserSuccess login valid`() {
        presenter.loginUserSuccess(LoginResultType.VALID)

        verify {
            Timber.i(any<String>())
            view.displayHome()
        }
        verify(exactly = 0) {
            presenter.showIncorrectLogin()
        }
    }

    @Test
    fun `loginUserSuccess login invalid`() {
        every { presenter.showIncorrectLogin() } just Runs

        presenter.loginUserSuccess(LoginResultType.INVALID)

        verify {
            Timber.i(any<String>())
            presenter.showIncorrectLogin()
        }
        verify(exactly = 0) {
            view.displayHome()
        }
    }

    @Test
    fun `loginUserFailed success`() {
        every { presenter.showIncorrectLogin() } just Runs

        presenter.loginUserFailed(dummyError)

        verify {
            Timber.e(dummyError,any())
            presenter.showIncorrectLogin()
        }
    }
}