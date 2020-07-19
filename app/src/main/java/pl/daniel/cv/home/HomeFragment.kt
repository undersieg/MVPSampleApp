package pl.daniel.cv.home

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_home.*
import pl.daniel.core.base.BaseFragment
import pl.daniel.core.base.BottomNavigationOwner
import pl.daniel.cv.R
import pl.daniel.domain.entity.information.Information
import pl.daniel.presenter.home.HomePresenter
import pl.daniel.presenter.home.HomeView

class HomeFragment : BaseFragment<HomePresenter, HomeView>(R.layout.fragment_home),
    HomeView, BottomNavigationOwner {

    private val homeAdapter = HomeAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerHome.adapter = homeAdapter
    }

    override fun displayList(list: List<Information>) {
        homeAdapter.update(list)
    }

    override fun displayDetails(id: Int) {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToDetailsFragment(id)
        )
    }

    override fun presenterClass(): Class<HomePresenter> = HomePresenter::class.java

    override fun onPresenterPrepared() {
        homeAdapter.presenter = presenter
    }

}