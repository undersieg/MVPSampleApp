package pl.daniel.cv.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import pl.daniel.core.base.BaseActivity
import pl.daniel.core.base.BottomNavigationOwner
import pl.daniel.core.extensions.setVisible
import pl.daniel.cv.R
import pl.daniel.data.utils.doNothing
import pl.daniel.presenter.main.MainPresenter
import pl.daniel.presenter.main.MainView

class MainActivity : BaseActivity<MainPresenter, MainView>(R.layout.activity_main),
    MainView, NavController.OnDestinationChangedListener {

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        mainNavHostFragment
            .childFragmentManager
            .registerFragmentLifecycleCallbacks(
                fragmentLifecycleCallbacks,
                false
            )
        findNavController(R.id.mainNavHostFragment).addOnDestinationChangedListener(this)
        bottomNavigation.itemIconTintList = null
        bottomNavigation.setupWithNavController(findNavController(R.id.mainNavHostFragment))
        findNavController(R.id.mainNavHostFragment).addOnDestinationChangedListener(this)
        fabHome.setOnClickListener {
            presenter.addNewClicked()
        }
    }

    override fun onDestroy() {
        findNavController(R.id.mainNavHostFragment).removeOnDestinationChangedListener(this)
        super.onDestroy()
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) = when (destination.id) {
        R.id.homeFragment -> presenter.homeClicked()
        R.id.settingsFragment -> presenter.settingsClicked()
        else -> doNothing
    }

    private val fragmentLifecycleCallbacks = object : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentResumed(
            fragmentManager: FragmentManager,
            fragment: Fragment
        ) {
            super.onFragmentResumed(fragmentManager, fragment)
            bottomNavigationVisibility(fragment is BottomNavigationOwner)
        }
    }

    fun bottomNavigationVisibility(visible: Boolean) {
        bottomNavigation.setVisible(visible)
        bottomAppBar.setVisible(visible)
        fabHome.setVisible(visible)
    }

    override fun presenterClass(): Class<MainPresenter> = MainPresenter::class.java

}