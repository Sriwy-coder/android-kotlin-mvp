package code.hyunwa.foodmarketkotlin.ui.auth.signup

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import code.hyunwa.foodmarketkotlin.R
import code.hyunwa.foodmarketkotlin.model.request.RegisterRequest
import code.hyunwa.foodmarketkotlin.ui.auth.AuthActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.dhaval2404.imagepicker.ImagePicker
import kotlinx.android.synthetic.main.fragment_singup.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class SignupFragment : Fragment() {

    var filePath: Uri?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_singup, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initDummy()
        initListener()
    }

    private fun initListener(){
        ivProfile.setOnClickListener {
            ImagePicker.with(this)
                .cameraOnly()
                .start()
        }

        btnContinue.setOnClickListener {
            var fullName = etFullName.text.toString()
            var email =etEmail.text.toString()
            var password=etPassword.text.toString()
            if(fullName.isNullOrEmpty()){
                etFullName.error = "lengkapi nama lengkap Anda"
                etFullName.requestFocus()
            }else if (email.isNullOrEmpty()){
                etEmail.error = "lengkapi email Anda"
                etEmail.requestFocus()
            }else if(password.isNullOrEmpty()){
                etPassword.error = "lengkapi password Anda"
                etPassword.requestFocus()
            }else{
                var data = RegisterRequest(
                    fullName, email, password, password,
                    "","","","",
                    filePath
                )
                var bundle = Bundle()
                bundle.putParcelable("data", data)
                Navigation.findNavController(it).navigate(R.id.action_signup_address, bundle)
                (activity as AuthActivity).toolbarSignupAddress()
            }
        }
    }

    private fun initDummy(){
        etFullName.setText("Sri Wahyuni")
        etEmail.setText("sri.wahyuni@gmail.com")
        etPassword.setText("12345678")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            filePath = data?.data
            Glide.with(this)
                .load(filePath)
                .apply(RequestOptions.circleCropTransform())
                .into(ivProfile)
        }else if(resultCode == ImagePicker.RESULT_ERROR){
            Toast.makeText(context, ImagePicker.getError(data), Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(context, "Task Cancel", Toast.LENGTH_SHORT).show()
        }
    }
}