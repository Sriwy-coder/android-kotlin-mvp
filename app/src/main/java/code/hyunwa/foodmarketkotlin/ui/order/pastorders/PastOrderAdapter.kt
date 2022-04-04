package code.hyunwa.foodmarketkotlin.ui.order.pastorders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.recyclerview.widget.RecyclerView
import code.hyunwa.foodmarketkotlin.R
import code.hyunwa.foodmarketkotlin.model.response.transaction.Data
import code.hyunwa.foodmarketkotlin.utils.Helpers.convertToTime
import code.hyunwa.foodmarketkotlin.utils.Helpers.formatPrice
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_pastorders.view.*

class PastOrderAdapter (
    private var listData : List<Data>,
    private var itemAdapterCallback : ItemAdapterCallback
    ) : RecyclerView.Adapter<PastOrderAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PastOrderAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_pastorders,parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: PastOrderAdapter.ViewHolder, position: Int) {
        holder.bind(listData[position], itemAdapterCallback)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(data: Data, itemAdapterCallback: ItemAdapterCallback){
            itemView.apply {
                tvTitle.text = data.food.name
                tvPrice.formatPrice(data.food.price.toString())
                tvDate.text = data.food.createdAt.convertToTime("MMM dd , HH:mm")

                Glide.with(context)
                    .load(data.food.picturePath)
                    .into(ivPoster)

                if(data.status.equals("CANCELLED")){
                    tvCancelled.visibility = View.VISIBLE
                }

                itemView.setOnClickListener { itemAdapterCallback.onClick(it, data) }

            }
        }

    }

    interface ItemAdapterCallback{
        fun onClick(v: View, data: Data)
    }


}