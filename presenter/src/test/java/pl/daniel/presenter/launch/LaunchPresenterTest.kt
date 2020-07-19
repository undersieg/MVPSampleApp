package pl.daniel.presenter.launch

import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import pl.daniel.data.CoroutineTestRule
import pl.daniel.domain.usecase.login.IsUserLoggedInUseCase
import timber.log.Timber

@ExperimentalCoroutinesApi
class LaunchPresenterTest {

    private val dummyError = RuntimeException("Dummy exception")

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @RelaxedMockK
    lateinit var view: LaunchView

    @MockK
    lateinit var isUserLoggedInUseCase: IsUserLoggedInUseCase

    private lateinit var presenter: LaunchPresenter

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        presenter = spyk(
            LaunchPresenter(
                isUserLoggedInUseCase
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
    fun `onFirstBind success`() {
        every { presenter.waitForSplashScreen() } returns Job()

        presenter.onFirstBind()

        verify {
            presenter.waitForSplashScreen()
        }
    }

    @Test
    fun `checkIfUserLoggedIn success`() = runBlockingTest {
        coEvery { isUserLoggedInUseCase.execute() } returns true
        coEvery { presenter.checkIfUserLoggedInSuccess(any()) } just Runs

        presenter.checkIfUserLoggedIn()

        coVerify {
            Timber.i(any<String>())
            isUserLoggedInUseCase.execute()
            presenter.checkIfUserLoggedInSuccess(true)
        }
        coVerify(exactly = 0) {
            presenter.checkIfUserLoggedInFailed(any())
        }
    }

    @Test
    fun `checkIfUserLoggedIn failed`() = runBlockingTest {
        coEvery { isUserLoggedInUseCase.execute() } throws dummyError
        coEvery { presenter.checkIfUserLoggedInFailed(any()) } just Runs

        presenter.checkIfUserLoggedIn()

        coVerify {
            Timber.i(any<String>())
            isUserLoggedInUseCase.execute()
            presenter.checkIfUserLoggedInFailed(dummyError)
        }
        coVerify(exactly = 0) {
            presenter.checkIfUserLoggedInSuccess(any())
        }
    }

    @Test
    fun `checkIfUserLoggedInSuccess user logged in`() {
        presenter.checkIfUserLoggedInSuccess(true)

        verify {
            Timber.i(any<String>())
            view.displayHome()
        }
        verify(exactly = 0) {
            view.displayLogin()
        }
    }

    @Test
    fun `checkIfUserLoggedInSuccess user not logged in`() {
        presenter.checkIfUserLoggedInSuccess(false)

        verify {
            Timber.i(any<String>())
            view.displayLogin()
        }
        verify(exactly = 0) {
            view.displayHome()
        }
    }

    @Test
    fun `checkIfUserLoggedInFailed success`() {
        presenter.checkIfUserLoggedInFailed(dummyError)

        verify {
            Timber.e(dummyError, any())
        }
    }
}