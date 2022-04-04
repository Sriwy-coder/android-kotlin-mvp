package code.hyunwa.foodmarketkotlin.ui.home.recommended

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import code.hyunwa.foodmarketkotlin.R
import code.hyunwa.foodmarketkotlin.model.dummy.HomeVericalModel
import code.hyunwa.foodmarketkotlin.model.response.home.Data
import code.hyunwa.foodmarketkotlin.ui.detail.DetailActivity
import code.hyunwa.foodmarketkotlin.ui.home.newtaste.HomeNewTasteAdapter
import kotlinx.android.synthetic.main.fragment_home_new_taste.*

class HomeRecommendedFragment : Fragment(), HomeNewTasteAdapter.ItemAdapterCallback {

    private var recommendedList : ArrayList<Data>? = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_new_taste, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recommendedList = arguments?.getParcelableArrayList("data")
//        initDataDummy()
        var adapter = HomeNewTasteAdapter(recommendedList!!, this)
        var layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rcList.layoutManager = layoutManager
        rcList.adapter = adapter
    }

//    fun initDataDummy(){
//        foodList = ArrayList()
//        foodList.add(HomeVericalModel("Cherry Healthy","100000","", 2.5f))
//        foodList.add(HomeVericalModel("Burger Tamayo","50000","", 3f))
//        foodList.add(HomeVericalModel("Soup Bumil","69000","", 2.8f))
//        foodList.add(HomeVericalModel("Chicken","87900","", 3.9f))
//    }

    override fun onClick(v: View, data: Data) {
        var detail = Intent(activity, DetailActivity::class.java).putExtra("data", data)
        startActivity(detail)
    }
}