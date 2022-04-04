package code.hyunwa.foodmarketkotlin.ui.order

import code.hyunwa.foodmarketkotlin.network.HttpClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class OrderPresenter(private val view : OrderContract.View) : OrderContract.Presenter {

    private val mCompositeDisposable: CompositeDisposable?
    init {
        this.mCompositeDisposable = CompositeDisposable()
    }

    override fun getTransaction() {
        view.showLoading()
        val disposable = HttpClient.getInstance().getApi()!!.transaction()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view.dismissLoading()
                    if(it.meta?.status.equals("success")){
                        it.data?.let { it1 -> view.onTransactionSuccess(it1) }
                    }
                    else{
                        view.onTransactionFailed(it.meta?.message)
                    }
                },
                {
                    view.dismissLoading()
                    view.onTransactionFailed(it.message.toString())
                }
            )
        mCompositeDisposable!!.add(disposable)
    }

    override fun subscribe() {}

    override fun unSubscribe() {
        mCompositeDisposable!!.clear()
    }
}