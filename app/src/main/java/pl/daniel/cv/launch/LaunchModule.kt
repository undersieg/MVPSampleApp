package pl.daniel.cv.launch

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import pl.daniel.core.di.viewmodel.ViewModelKey
import pl.daniel.presenter.launch.LaunchPresenter

@Module
abstract class LaunchModule {

    @Binds
    @IntoMap
    @ViewModelKey(LaunchPresenter::class)
    abstract fun bindLaunchPresenter(launchPresenter: LaunchPresenter): ViewModel
}