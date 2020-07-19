package pl.daniel.cv.main

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import pl.daniel.core.di.viewmodel.ViewModelKey
import pl.daniel.presenter.main.MainPresenter

@Module
abstract class MainModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainPresenter::class)
    abstract fun bindMainPresenter(mainPresenter: MainPresenter): ViewModel
}