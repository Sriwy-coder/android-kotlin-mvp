package code.hyunwa.foodmarketkotlin.ui.auth.signin

import code.hyunwa.foodmarketkotlin.base.BasePresenter
import code.hyunwa.foodmarketkotlin.base.BaseView
import code.hyunwa.foodmarketkotlin.model.response.login.LoginResponse

interface SigninContract {

    interface View: BaseView{
        fun onLoginSuccess(loginResponse: LoginResponse)
        fun onLoginFailed(message: String)
    }

    interface Presenter: SigninContract, BasePresenter{
        fun submitLogin(email: String, password: String)
    }
}