package pl.daniel.presenter.home.details

import pl.daniel.domain.entity.information.InformationDetails
import pl.daniel.presenter.base.BaseView

interface DetailsView : BaseView {
    fun showDetails(details: InformationDetails)
}