package pl.daniel.cv.home.details

import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_details.*
import pl.daniel.core.base.BaseFragment
import pl.daniel.cv.R
import pl.daniel.domain.entity.information.InformationDetails
import pl.daniel.presenter.home.details.DetailsPresenter
import pl.daniel.presenter.home.details.DetailsView

class DetailsFragment : BaseFragment<DetailsPresenter, DetailsView>(R.layout.fragment_details),
    DetailsView {

    private val args: DetailsFragmentArgs by navArgs()


    override fun showDetails(details: InformationDetails) = with(details){
        labelDetailsCompanyName.text = company
        labelDetailsCompanyRole.text = role
        labelDetailsDateFrom.text = dateFrom
        labelDetailsDateTo.text = dateTo
        labelDetailsMainResponsibilities.text = mainResponsibilities
        labelDetailsAchievements.text = achievements
        Picasso.get()
            .load(details.logoUrl)
            .into(imageDetailsLogo)
    }

    override fun presenterClass(): Class<DetailsPresenter> = DetailsPresenter::class.java

    override fun onPresenterPrepared() {
        presenter.detailsId = args.itemId
    }
}