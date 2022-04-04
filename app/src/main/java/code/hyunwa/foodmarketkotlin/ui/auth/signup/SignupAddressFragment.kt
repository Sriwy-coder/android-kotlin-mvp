package code.hyunwa.foodmarketkotlin.ui.auth.signup

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import code.hyunwa.foodmarketkotlin.FoodMarket
import code.hyunwa.foodmarketkotlin.R
import code.hyunwa.foodmarketkotlin.model.request.RegisterRequest
import code.hyunwa.foodmarketkotlin.model.response.login.LoginResponse
import code.hyunwa.foodmarketkotlin.ui.auth.AuthActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_singup.*
import kotlinx.android.synthetic.main.fragment_singup_address.*

class SignupAddressFragment : Fragment(), SignupContract.View {

    private lateinit var data : RegisterRequest
    lateinit var presenter: SignupPresenter
    var progressDialog : Dialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_singup_address, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        presenter = SignupPresenter(this)
        data = arguments?.getParcelable<RegisterRequest>("data")!!
        initDummy()
        initListener()
        initView()
    }

    private fun initListener(){

        btnSignUpNow.setOnClickListener {

            var phone = etPhoneNo.text.toString()
            var address = etAddress.text.toString()
            var houseNo = etHouseNo.text.toString()
            var city = etCity.text.toString()

            data.let {
                it.phoneNumber = phone
                it.address=address
                it.houseNumber= houseNo
                it.city = city
            }

            if(phone.isNullOrEmpty()){
                etPhoneNo.error = "lengkapi nomor telp Anda"
                etPhoneNo.requestFocus()
            }else if (address.isNullOrEmpty()){
                etAddress.error = "lengkapi alamat Anda"
                etAddress.requestFocus()
            }else if(houseNo.isNullOrEmpty()) {
                etHouseNo.error = "lengkapi nomor rumah Anda"
                etHouseNo.requestFocus()
            }else if(city.isNullOrEmpty()) {
                etCity.error = "lengkapi nama kota Anda"
                etCity.requestFocus()
            }else{
                presenter.submitRegister(data, it)
            }
        }
    }

    private fun initView(){
        progressDialog = Dialog(requireContext())
        var dialogLayout = layoutInflater.inflate(R.layout.dialog_loader, null)
        progressDialog?.let {
            it.setContentView(dialogLayout)
            it.setCancelable(false)
            it.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
    }

    private fun initDummy(){
        etPhoneNo.setText("081314165156")
        etAddress.setText("Gurun")
        etHouseNo.setText("198")
        etCity.setText("Tanah Datar, Suamatera Barat")
    }

    override fun onRegisterSuccess(loginResponse: LoginResponse, view: View) {
        FoodMarket.getApp().setToken(loginResponse.access_token)

        val gson = Gson()
        val json = gson.toJson(loginResponse.user)
        FoodMarket.getApp().setUser(json)

        if(data.filePath == null){
            (activity as AuthActivity).toolbarSignupSuccess()
            Navigation.findNavController(view).navigate(R.id.action_signup_success, null)
        }else{
            presenter.submitPhotoRegister(data.filePath!!, view)
        }
    }

    override fun onRegisterPhotoSuccess(view: View) {
        (activity as AuthActivity).toolbarSignupSuccess()
        Navigation.findNavController(view).navigate(R.id.action_signup_success, null)
    }

    override fun onRegisterFailed(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    override fun showLoading() {
       progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }


}
