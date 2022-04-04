package code.hyunwa.foodmarketkotlin.ui.auth.signin

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import code.hyunwa.foodmarketkotlin.FoodMarket
import code.hyunwa.foodmarketkotlin.ui.MainActivity
import code.hyunwa.foodmarketkotlin.R
import code.hyunwa.foodmarketkotlin.model.response.login.LoginResponse
import code.hyunwa.foodmarketkotlin.ui.auth.AuthActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_singin.*

class SigninFragment : Fragment(), SigninContract.View {

    lateinit var presenter: SigninPresenter
    var progressDialog : Dialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_singin, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        presenter = SigninPresenter(this)

        if(!FoodMarket.getApp().getToken().isNullOrEmpty()){
            val home = Intent(activity, MainActivity::class.java)
            startActivity(home)
            activity?.finish()
        }

        initView()
        initDummy()
        btnSignUp.setOnClickListener {
            val signup = Intent(activity, AuthActivity::class.java)
            signup.putExtra("page_request", 2)
            startActivity(signup)
        }

        btnSignIn.setOnClickListener {
            var email = etEmail.text.toString()
            var password = etPassword.text.toString()

            if(email.isNullOrEmpty()){
                etEmail.error = "Silahkan masukkan email Anda"
                etEmail.requestFocus()
            }else if (password.isNullOrEmpty()){
                etPassword.error = "Silahkan masukkan password Anda"
                etPassword.requestFocus()
            }else{
                presenter.submitLogin(email,password)
            }
        }
    }

    override fun onLoginSuccess(loginResponse: LoginResponse) {
        FoodMarket.getApp().setToken(loginResponse.access_token)

        val gson = Gson()
        val json = gson.toJson(loginResponse.user)
        FoodMarket.getApp().setUser(json)

        val home = Intent(activity, MainActivity::class.java)
        startActivity(home)
        activity?.finish()
    }

    override fun onLoginFailed(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    private fun initDummy(){
        etEmail.setText("hynwa@gmai.com")
        etPassword.setText("12345678")
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

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }
}