package code.hyunwa.foodmarketkotlin.ui.detail

import code.hyunwa.foodmarketkotlin.base.BasePresenter
import code.hyunwa.foodmarketkotlin.base.BaseView
import code.hyunwa.foodmarketkotlin.model.response.checkout.CheckoutResponse
import io.reactivex.disposables.CompositeDisposable

interface PaymentContract {
    interface View: BaseView{
        fun onCheckoutSuccess(checkoutResponse: CheckoutResponse, view : android.view.View)
        fun onCheckoutFailed(message :String)
    }

    interface Presenter: PaymentContract, BasePresenter{
        fun getCheckout(foodId : String, userId: String, quantity : String, total: String, view:android.view.View)
    }
}