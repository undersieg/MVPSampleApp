package pl.daniel.presenter.main

import pl.daniel.presenter.base.BasePresenter
import timber.log.Timber
import javax.inject.Inject

class MainPresenter @Inject constructor(

): BasePresenter<MainView>() {
    fun homeClicked() {
        Timber.i("Home clicked!")
    }

    fun settingsClicked() {
        Timber.i("Settings clicked!")
    }

    fun addNewClicked() {
        Timber.i("Add new clicked!")
    }
}