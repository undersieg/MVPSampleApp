package pl.daniel.presenter.home

import androidx.annotation.VisibleForTesting
import kotlinx.coroutines.launch
import pl.daniel.domain.entity.information.Information
import pl.daniel.domain.usecase.home.FetchListUseCase
import pl.daniel.presenter.base.BasePresenter
import timber.log.Timber
import javax.inject.Inject

class HomePresenter @Inject constructor(
        private val fetchListUseCase: FetchListUseCase
) : BasePresenter<HomeView>(), HomeItemPresenter {

    override fun onFirstBind() {
        super.onFirstBind()

        fetchList()
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun fetchList() = launch {
        Timber.i("Fetch list!")
        try {
            fetchListSuccess(
                fetchListUseCase.execute()
            )
        } catch (throwable: Throwable) {
            fetchListFailed(throwable)
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun fetchListSuccess(list: List<Information>) {
        Timber.i("Fetch list success!")
        present { displayList(list) }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun fetchListFailed(throwable: Throwable) {
        Timber.e(throwable, "Fetch list failed!")
    }

    override fun onItemClicked(item: Information) {
        Timber.i("On item clicked!")
        present { displayDetails(item.id) }
    }
}