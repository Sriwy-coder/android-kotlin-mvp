package code.hyunwa.foodmarketkotlin.ui.order

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import code.hyunwa.foodmarketkotlin.FoodMarket
import code.hyunwa.foodmarketkotlin.R
import code.hyunwa.foodmarketkotlin.model.response.login.User
import code.hyunwa.foodmarketkotlin.model.response.transaction.Data
import code.hyunwa.foodmarketkotlin.model.response.transaction.TransactionResponse
import com.bumptech.glide.Glide
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.fragment_order.*
import kotlinx.android.synthetic.main.layout_toolbar.view.*

class OrderFragment : Fragment(), OrderContract.View{

    lateinit var presenter: OrderPresenter
    var progressDialog : Dialog? = null

    var inprogressList : ArrayList<Data>? = ArrayList()
    var pastordersList : ArrayList<Data>? = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_order, container, false)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initView()
        presenter = OrderPresenter(this)
        presenter.getTransaction()
    }

    private fun initView() {
        progressDialog = Dialog(requireContext())
        var dialogLayout = layoutInflater.inflate(R.layout.dialog_loader, null)
        progressDialog?.let {
            it.setContentView(dialogLayout)
            it.setCancelable(false)
            it.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }

        layout_toolbar.toolbar.title = "Your Orders"
        layout_toolbar.toolbar.subtitle ="Wait for the best meal"
    }

    override fun onTransactionSuccess(transactionResponse: TransactionResponse) {
        if(transactionResponse.data.isNullOrEmpty()){
            ll_empty.visibility = View.VISIBLE
            ll_tab.visibility = View.GONE
            layout_toolbar.visibility = View.GONE
        }else{
            for (a in transactionResponse.data.indices){
                if (transactionResponse.data[a].status.equals("ON_DELIVERY", true)
                    || transactionResponse.data[a].status.equals("PENDING", true)){
                    inprogressList?.add(transactionResponse.data[a])
                }else if (transactionResponse.data[a].status.equals("DELIVERY", true)
                    || transactionResponse.data[a].status.equals("CANCELLED", true)
                    || transactionResponse.data[a].status.equals("SUCCESS", true)){
                    pastordersList?.add(transactionResponse.data[a])
                }
            }

            var sectionPagerAdapter = SectionPagerAdapter( childFragmentManager)
            sectionPagerAdapter.setData(inprogressList, pastordersList)
            viewPager.adapter = sectionPagerAdapter
            tabLayout.setupWithViewPager(viewPager)
        }
    }

    override fun onTransactionFailed(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }

}