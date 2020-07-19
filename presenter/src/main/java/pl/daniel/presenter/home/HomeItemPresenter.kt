package pl.daniel.presenter.home

import pl.daniel.domain.entity.information.Information

interface HomeItemPresenter {
    fun onItemClicked(item: Information)
}