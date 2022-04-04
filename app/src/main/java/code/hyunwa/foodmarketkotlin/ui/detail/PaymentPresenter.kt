package code.hyunwa.foodmarketkotlin.ui.detail

import android.view.View
import code.hyunwa.foodmarketkotlin.network.HttpClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class PaymentPresenter (private val view : PaymentContract.View) : PaymentContract.Presenter {
    private val mCompositeDisposable : CompositeDisposable?

    init {
        this.mCompositeDisposable = CompositeDisposable()
    }

    override fun getCheckout(
        foodId: String,
        userId: String,
        quantity: String,
        total: String,
        view2: View
    ) {
        view.showLoading()
        val disposable = HttpClient.getInstance().getApi()!!.checkout(foodId, userId, quantity, total, "DELIVERY")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view.dismissLoading()
                    if(it.meta?.status.equals("success")){
                        it.data?.let { it1 -> view.onCheckoutSuccess(it1, view2)}
                    }else{
                        view.onCheckoutFailed(it.meta?.message)
                    }
                },
                {
                    view.dismissLoading()
                    view.onCheckoutFailed(it.message.toString())
                }
            )
        mCompositeDisposable!!.add(disposable)
    }


    override fun subscribe() {}

    override fun unSubscribe() {
        mCompositeDisposable?.clear()
    }
}