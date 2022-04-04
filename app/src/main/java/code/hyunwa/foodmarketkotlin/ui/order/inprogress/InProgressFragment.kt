package code.hyunwa.foodmarketkotlin.ui.order.inprogress

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import code.hyunwa.foodmarketkotlin.R
import code.hyunwa.foodmarketkotlin.model.response.transaction.Data
import kotlinx.android.synthetic.main.fragment_in_progress.*

class InProgressFragment : Fragment(), InProgressAdapter.ItemAdapterCallback {

    var adapter : InProgressAdapter? = null
    var inprogressList : ArrayList<Data>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_in_progress, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        inprogressList = arguments?.getParcelableArrayList("data")
        if(!inprogressList.isNullOrEmpty()){
            adapter = InProgressAdapter(inprogressList!!, this)
            val layoutManager = LinearLayoutManager(activity)
            rcList.layoutManager = layoutManager
            rcList.adapter = adapter
        }
    }

    override fun onClick(v: View, data: Data) {
        Toast.makeText(activity, data.food.name, Toast.LENGTH_SHORT).show()
    }
}