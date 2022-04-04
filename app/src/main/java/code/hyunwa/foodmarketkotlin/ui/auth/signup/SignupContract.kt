package code.hyunwa.foodmarketkotlin.ui.auth.signup

import android.net.Uri
import code.hyunwa.foodmarketkotlin.base.BasePresenter
import code.hyunwa.foodmarketkotlin.base.BaseView
import code.hyunwa.foodmarketkotlin.model.request.RegisterRequest
import code.hyunwa.foodmarketkotlin.model.response.login.LoginResponse

interface SignupContract {

    interface View: BaseView{
        fun onRegisterSuccess(loginResponse: LoginResponse, view: android.view.View)
        fun onRegisterPhotoSuccess(view: android.view.View)
        fun onRegisterFailed(message: String)
    }

    interface Presenter: SignupContract, BasePresenter{
        fun submitRegister(registerRequest: RegisterRequest, view: android.view.View)
        fun submitPhotoRegister(filePath: Uri, view: android.view.View)
    }
}