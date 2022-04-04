package code.hyunwa.foodmarketkotlin.ui.order

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import code.hyunwa.foodmarketkotlin.model.response.transaction.Data
import code.hyunwa.foodmarketkotlin.ui.order.inprogress.InProgressFragment
import code.hyunwa.foodmarketkotlin.ui.order.pastorders.PastOrdersFragment
import code.hyunwa.foodmarketkotlin.ui.profile.account.ProfileAccountFragment

class SectionPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    var inprogressList : ArrayList<Data>? = ArrayList()
    var pastordersList : ArrayList<Data>? = ArrayList()

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "In Progress"
            1 -> "Past Orders"
            else -> ""
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        var fragment : Fragment
        return when(position){
            0 -> {
                fragment = InProgressFragment()
                var bundle = Bundle()
                bundle.putParcelableArrayList("data", inprogressList)
                fragment.arguments = bundle
                return fragment
            }
            1 -> {
                fragment = PastOrdersFragment()
                var bundle = Bundle()
                bundle.putParcelableArrayList("data", pastordersList)
                fragment.arguments = bundle
                return fragment
            }
            else -> {
                fragment = InProgressFragment()
                var bundle = Bundle()
                bundle.putParcelableArrayList("data", inprogressList)
                fragment.arguments = bundle
                return fragment
            }
        }
    }

    fun setData(inprogressListParms : ArrayList<Data>?, pastOrdersListParms : ArrayList<Data>?){
        inprogressList = inprogressListParms
        pastordersList = pastOrdersListParms
    }
}