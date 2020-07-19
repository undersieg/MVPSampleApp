package pl.daniel.presenter.home.details

import androidx.annotation.VisibleForTesting
import kotlinx.coroutines.launch
import pl.daniel.domain.entity.information.InformationDetails
import pl.daniel.domain.usecase.home.FetchDetailsUseCase
import pl.daniel.presenter.base.BasePresenter
import timber.log.Timber
import javax.inject.Inject

class DetailsPresenter @Inject constructor(
    private val fetchDetailsUseCase: FetchDetailsUseCase
) : BasePresenter<DetailsView>() {

    var detailsId: Int = 0

    override fun onFirstBind() {
        super.onFirstBind()
        if (detailsId == 0) return

        fetchDetails(detailsId)
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun fetchDetails(detailsId: Int) = launch {
        Timber.i("Fetch details!")
        try {
            fetchDetailsSuccess(
                fetchDetailsUseCase.execute(detailsId)
            )
        } catch (throwable: Throwable) {
            fetchDetailsFailed(throwable)
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun fetchDetailsSuccess(details: InformationDetails?) {
        Timber.i("Fetch details success!")
        details?.let {
            present { showDetails(details) }
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun fetchDetailsFailed(throwable: Throwable) {
        Timber.e(throwable, "Fetch details failed!")
    }
}