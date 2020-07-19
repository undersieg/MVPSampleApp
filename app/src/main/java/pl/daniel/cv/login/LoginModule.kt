package pl.daniel.cv.login

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import pl.daniel.core.di.viewmodel.ViewModelKey
import pl.daniel.presenter.login.LoginPresenter

@Module
abstract class LoginModule{

    @Binds
    @IntoMap
    @ViewModelKey(LoginPresenter::class)
    abstract fun bindAgendaViewModel(presenter: LoginPresenter): ViewModel
}