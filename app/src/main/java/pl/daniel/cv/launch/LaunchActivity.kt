package pl.daniel.cv.launch

import android.content.Intent
import pl.daniel.core.base.BaseActivity
import pl.daniel.cv.R
import pl.daniel.cv.login.LoginActivity
import pl.daniel.cv.main.MainActivity
import pl.daniel.presenter.launch.LaunchPresenter
import pl.daniel.presenter.launch.LaunchView

class LaunchActivity : BaseActivity<LaunchPresenter, LaunchView>(R.layout.activity_launch),
    LaunchView {

    override fun displayHome() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun displayLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
    override fun presenterClass(): Class<LaunchPresenter> = LaunchPresenter::class.java

}