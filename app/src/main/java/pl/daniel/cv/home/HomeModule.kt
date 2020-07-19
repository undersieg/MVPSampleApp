package pl.daniel.cv.home

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import pl.daniel.core.di.scops.FragmentScope
import pl.daniel.core.di.viewmodel.ViewModelKey
import pl.daniel.presenter.home.HomePresenter

@Module
abstract class HomeModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun bindHomeFragment(): HomeFragment

    @Binds
    @IntoMap
    @ViewModelKey(HomePresenter::class)
    abstract fun bindHomePresenter(homePresenter: HomePresenter): ViewModel
}