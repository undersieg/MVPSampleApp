package pl.daniel.cv.home.details

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import pl.daniel.core.di.scops.FragmentScope
import pl.daniel.core.di.viewmodel.ViewModelKey
import pl.daniel.presenter.home.details.DetailsPresenter

@Module
abstract class DetailsModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun bindDetailsFragment(): DetailsFragment

    @Binds
    @IntoMap
    @ViewModelKey(DetailsPresenter::class)
    abstract fun bindDetailsPresenter(detailsPresenter: DetailsPresenter): ViewModel
}