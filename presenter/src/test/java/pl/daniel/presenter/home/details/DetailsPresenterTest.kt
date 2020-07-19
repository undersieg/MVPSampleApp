package pl.daniel.presenter.home.details

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
import pl.daniel.domain.entity.information.InformationDetails
import pl.daniel.domain.usecase.home.FetchDetailsUseCase
import timber.log.Timber
import java.lang.RuntimeException

@ExperimentalCoroutinesApi
class DetailsPresenterTest {

    private val dummyInformationDetails =
        InformationDetails(
            2,
            "test",
            "test",
            "test",
            "test",
            "test",
            "test",
            "Test"
        )
    private val dummyError = RuntimeException("Dummy exception")

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @RelaxedMockK
    lateinit var view: DetailsView

    @MockK
    lateinit var fetchDetailsUseCase: FetchDetailsUseCase

    private lateinit var presenter: DetailsPresenter

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        presenter = spyk(
            DetailsPresenter(
                fetchDetailsUseCase
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
    fun `onFirstBind details id not empty`() {
        presenter.detailsId = 2

        presenter.onFirstBind()

        verify {
            presenter.fetchDetails(2)
        }
    }

    @Test
    fun `onFirstBind details id empty`() {
        presenter.detailsId = 0
        every { presenter.fetchDetails(any()) } returns Job()

        presenter.onFirstBind()

        verify(exactly = 0) {
            presenter.fetchDetails(any())
        }
    }

    @Test
    fun `fetchDetails success`() = runBlockingTest {
        val detailsID = 2
        coEvery { fetchDetailsUseCase.execute(any()) } returns dummyInformationDetails
        coEvery { presenter.fetchDetailsSuccess(any()) } just Runs

        presenter.fetchDetails(detailsID)

        coVerify {
            Timber.i(any<String>())
            fetchDetailsUseCase.execute(detailsID)
            presenter.fetchDetailsSuccess(dummyInformationDetails)
        }
        coVerify(exactly = 0) {
            presenter.fetchDetailsFailed(any())
        }
    }

    @Test
    fun `fetchDetails failed`() = runBlockingTest {
        val detailsID = 2
        coEvery { fetchDetailsUseCase.execute(any()) } throws dummyError
        coEvery { presenter.fetchDetailsFailed(any()) } just Runs

        presenter.fetchDetails(detailsID)

        coVerify {
            Timber.i(any<String>())
            fetchDetailsUseCase.execute(detailsID)
            presenter.fetchDetailsFailed(dummyError)
        }
        coVerify(exactly = 0) {
            presenter.fetchDetailsSuccess(any())
        }
    }

    @Test
    fun `fetchDetailsSuccess details not null`() {
        presenter.fetchDetailsSuccess(dummyInformationDetails)

        verify {
            Timber.i(any<String>())
            view.showDetails(dummyInformationDetails)
        }
    }

    @Test
    fun `fetchDetailsSuccess details null`() {
        presenter.fetchDetailsSuccess(null)

        verify {
            Timber.i(any<String>())
        }
        verify(exactly = 0) {
            view.showDetails(any())
        }
    }

    @Test
    fun `fetchDetailsFailed success`() {
        presenter.fetchDetailsFailed(dummyError)

        verify {
            Timber.e(dummyError, any())
        }
    }
}