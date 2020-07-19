package pl.daniel.cv.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_home.view.*
import pl.daniel.cv.R
import pl.daniel.domain.entity.information.Information
import pl.daniel.presenter.home.HomeItemPresenter

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    private var data: List<Information> = emptyList()
    lateinit var presenter: HomeItemPresenter

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder =
        HomeViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_home, parent, false),
            presenter
        )

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(data[position])
    }

    fun update(list: List<Information>) {
        data = list
        notifyDataSetChanged()
    }

    class HomeViewHolder(private val item: View, private val presenter: HomeItemPresenter) :
        RecyclerView.ViewHolder(item) {

        fun bind(information: Information) = with(item) {
            labelHomeItemName.text = information.company
            setOnClickListener {
                presenter.onItemClicked(information)
            }
            Picasso.get()
                .load(information.logoUrl)
                .into(imageHomeItemLogo)
        }
    }
}