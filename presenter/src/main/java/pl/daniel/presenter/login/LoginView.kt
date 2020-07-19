package pl.daniel.presenter.login

import pl.daniel.presenter.base.BaseView

interface LoginView : BaseView {
    fun displayHome()
    fun hidePasswordErrorIfNeeded()
    fun hideLoginErrorIfNeeded()
    fun showLoginError()
    fun showPasswordError()
}