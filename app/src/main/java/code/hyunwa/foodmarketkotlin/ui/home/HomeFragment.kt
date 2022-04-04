package code.hyunwa.foodmarketkotlin.ui.home

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import code.hyunwa.foodmarketkotlin.FoodMarket
import code.hyunwa.foodmarketkotlin.R
import code.hyunwa.foodmarketkotlin.databinding.FragmentHomeBinding
import code.hyunwa.foodmarketkotlin.model.dummy.HomeModel
import code.hyunwa.foodmarketkotlin.model.response.home.Data
import code.hyunwa.foodmarketkotlin.model.response.home.HomeResponse
import code.hyunwa.foodmarketkotlin.model.response.login.User
import code.hyunwa.foodmarketkotlin.ui.detail.DetailActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(), HomeAdapter.ItemAdapterCallback, HomeContract.View {

    private var newTasteList: ArrayList<Data> = ArrayList()
    private var popularList: ArrayList<Data> = ArrayList()
    private var recommendedList: ArrayList<Data> = ArrayList()
    private lateinit var presenter: HomePresenter
    var progressDialog: Dialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val root = inflater.inflate(R.layout.fragment_home, container, false)

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initView()
        presenter = HomePresenter(this)
        presenter.getHome()
//        initDataDummy()
    }

    private fun initView() {
        progressDialog = Dialog(requireContext())
        var dialogLayout = layoutInflater.inflate(R.layout.dialog_loader, null)
        progressDialog?.let {
            it.setContentView(dialogLayout)
            it.setCancelable(false)
            it.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }

        var user = FoodMarket.getApp().getUser()
        var userResponse = Gson().fromJson(user, User::class.java)
        if (!userResponse.profile_photo_url.isNullOrEmpty()){
            Glide.with(requireContext())
                .load(userResponse.profile_photo_url)
                .apply(RequestOptions.circleCropTransform())
                .into(ivProfile)
        }
    }

//    fun initDataDummy() {
//        foodList = ArrayList()
//        foodList.add(HomeModel("Cherry Healthy", "", 4.5f))
//        foodList.add(HomeModel("Burger Tamayo", "", 5f))
//        foodList.add(HomeModel("Soup Bumil", "", 4f))
//        foodList.add(HomeModel("Chicken", "", 3f))
//    }

    override fun onClick(v: View, data: Data) {
        var detail = Intent(activity, DetailActivity::class.java).putExtra("data", data)
        startActivity(detail)
    }

    override fun onHomeSuccess(homeResponse: HomeResponse) {

        for (a in homeResponse.data.indices){
            var items : List<String> = homeResponse.data[a].types?.split(",") ?: ArrayList()
            for (x in items.indices){
                if(items[x].equals("new_food", true)){
                    newTasteList?.add(homeResponse.data[a])
                }else if(items[x].equals("recommended", true)){
                    recommendedList?.add(homeResponse.data[a])
                }else if(items[x].equals("popular", true)){
                    popularList?.add(homeResponse.data[a])
                }
            }
        }

        var adapter = HomeAdapter(homeResponse.data, this)
        var layoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rcList.layoutManager = layoutManager
        rcList.adapter = adapter

        var sectionPagerAdapter = SectionPagerAdapter(
            childFragmentManager
        )
        sectionPagerAdapter.setData(newTasteList, popularList, recommendedList)
        viewPager.adapter = sectionPagerAdapter
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun onHomeFailed(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }

}