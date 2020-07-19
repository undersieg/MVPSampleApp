package pl.daniel.cv.settings

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import pl.daniel.core.di.viewmodel.ViewModelKey
import pl.daniel.presenter.settings.SettingsPresenter

@Module
abstract class SettingsModule {

    @ContributesAndroidInjector
    abstract fun bindSettingsFragment(): SettingsFragment

    @Binds
    @IntoMap
    @ViewModelKey(SettingsPresenter::class)
    abstract fun bindSettingsPresenter(settingsPresenter: SettingsPresenter): ViewModel
}