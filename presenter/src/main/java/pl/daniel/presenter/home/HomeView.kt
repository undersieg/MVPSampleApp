package pl.daniel.presenter.home

import pl.daniel.domain.entity.information.Information
import pl.daniel.presenter.base.BaseView

interface HomeView : BaseView {
    fun displayList(list: List<Information>)
    fun displayDetails(id: Int)
}