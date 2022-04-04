package code.hyunwa.foodmarketkotlin.ui.home

import code.hyunwa.foodmarketkotlin.base.BasePresenter
import code.hyunwa.foodmarketkotlin.base.BaseView
import code.hyunwa.foodmarketkotlin.model.response.home.HomeResponse

interface HomeContract {

    interface View:BaseView{
        fun onHomeSuccess(homeResponse: HomeResponse)
        fun onHomeFailed(message: String)
    }

    interface Presenter: HomeContract, BasePresenter{
        fun getHome()
    }
}