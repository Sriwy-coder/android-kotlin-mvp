package code.hyunwa.foodmarketkotlin.ui.order.pastorders

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import code.hyunwa.foodmarketkotlin.R
import code.hyunwa.foodmarketkotlin.model.response.transaction.Data
import code.hyunwa.foodmarketkotlin.ui.order.inprogress.InProgressAdapter
import kotlinx.android.synthetic.main.fragment_in_progress.*

class PastOrdersFragment : Fragment(), PastOrderAdapter.ItemAdapterCallback {

    var adapter : PastOrderAdapter? = null
    var pastordersList : ArrayList<Data>? = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_past_orders, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        pastordersList = arguments?.getParcelableArrayList("data")
        if(!pastordersList.isNullOrEmpty()){
            adapter = PastOrderAdapter(pastordersList!!, this)
            val layoutManager = LinearLayoutManager(activity)
            rcList.layoutManager = layoutManager
            rcList.adapter = adapter
        }
    }

    override fun onClick(v: View, data: Data) {
        Toast.makeText(activity, data.food.name, Toast.LENGTH_SHORT).show()
    }
}