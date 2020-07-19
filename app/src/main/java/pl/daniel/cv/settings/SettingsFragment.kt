package pl.daniel.cv.settings

import android.content.Intent
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_settings.*
import pl.daniel.core.base.BaseFragment
import pl.daniel.core.base.BottomNavigationOwner
import pl.daniel.cv.R
import pl.daniel.cv.login.LoginActivity
import pl.daniel.presenter.settings.SettingsPresenter
import pl.daniel.presenter.settings.SettingsView

class SettingsFragment : BaseFragment<SettingsPresenter, SettingsView>(R.layout.fragment_settings),
    SettingsView, BottomNavigationOwner {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonLogout.setOnClickListener {
            presenter.logoutClicked()
        }
    }

    override fun displayLogin() {
        startActivity(Intent(requireContext(), LoginActivity::class.java))
        requireActivity().finish()
    }

    override fun presenterClass(): Class<SettingsPresenter> = SettingsPresenter::class.java

}