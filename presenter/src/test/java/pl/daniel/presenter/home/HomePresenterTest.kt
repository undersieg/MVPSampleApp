package pl.daniel.presenter.home

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
import pl.daniel.domain.entity.information.Information
import pl.daniel.domain.usecase.home.FetchListUseCase
import timber.log.Timber
import java.lang.RuntimeException

@ExperimentalCoroutinesApi
class HomePresenterTest {

    private val dummyError = RuntimeException("Dummy exception")
    private val dummyInformation1 = Information(1, "test", "test")
    private val dummyInformation2 = Information(1, "test", "test")
    private val dummyInformation3 = Information(1, "test", "test")
    private val dummyListOfInformation =
        listOf(dummyInformation1, dummyInformation2, dummyInformation3)

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @RelaxedMockK
    lateinit var view: HomeView

    @MockK
    lateinit var fetchListUseCase: FetchListUseCase

    private lateinit var presenter: HomePresenter

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        presenter = spyk(
            HomePresenter(
                fetchListUseCase
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
        every { presenter.fetchList() } returns Job()

        presenter.onFirstBind()

        verify {
            presenter.fetchList()
        }
    }

    @Test
    fun `fetchList success`() = runBlockingTest {
        coEvery { fetchListUseCase.execute() } returns dummyListOfInformation
        coEvery { presenter.fetchListSuccess(any()) } just Runs

        presenter.fetchList()

        coVerify {
            Timber.i(any<String>())
            fetchListUseCase.execute()
            presenter.fetchListSuccess(dummyListOfInformation)
        }
        coVerify(exactly = 0) {
            presenter.fetchListFailed(any())
        }
    }

    @Test
    fun `fetchList failed`() = runBlockingTest {
        coEvery { fetchListUseCase.execute() } throws dummyError
        coEvery { presenter.fetchListFailed(any()) } just Runs

        presenter.fetchList()

        coVerify {
            Timber.i(any<String>())
            fetchListUseCase.execute()
            presenter.fetchListFailed(dummyError)
        }
        coVerify(exactly = 0) {
            presenter.fetchListSuccess(any())
        }
    }

    @Test
    fun `fetchListSuccess success`() {
        presenter.fetchListSuccess(dummyListOfInformation)

        verify {
            Timber.i(any<String>())
            view.displayList(dummyListOfInformation)
        }
    }

    @Test
    fun `fetchListFailed success`() {
        presenter.fetchListFailed(dummyError)

        verify { Timber.e(dummyError, any()) }
    }

    @Test
    fun `onItemClicked success`() {
        presenter.onItemClicked(dummyInformation2)

        verify {
            view.displayDetails(dummyInformation2.id)
        }
    }
}