package pl.daniel.cv.login

import android.content.Intent
import android.os.Bundle
import androidx.core.widget.doAfterTextChanged
import kotlinx.android.synthetic.main.activity_login.*
import pl.daniel.core.base.BaseActivity
import pl.daniel.cv.R
import pl.daniel.cv.main.MainActivity
import pl.daniel.presenter.login.LoginPresenter
import pl.daniel.presenter.login.LoginView

class LoginActivity : BaseActivity<LoginPresenter, LoginView>(R.layout.activity_login), LoginView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        textLoginEditText.doAfterTextChanged {
            presenter.loginChanged(it.toString())
        }

        textPasswordEditText.doAfterTextChanged {
            presenter.passwordChanged(it.toString())
        }

        buttonLogin.setOnClickListener {
            presenter.loginClicked()
        }
    }

    override fun displayHome() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun showLoginError() {
        textLogin.error = getString(R.string.emptyLoginError)
    }

    override fun showPasswordError() {
        textPassword.error = getString(R.string.emptyPasswordError)
    }

    override fun hideLoginErrorIfNeeded() = with(textLogin) {
        if (error == null) return

        error = null
    }

    override fun hidePasswordErrorIfNeeded() = with(textPassword) {
        if (error == null) return

        error = null
    }

    override fun presenterClass(): Class<LoginPresenter> = LoginPresenter::class.java
}