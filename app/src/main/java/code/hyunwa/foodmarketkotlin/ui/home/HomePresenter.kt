package code.hyunwa.foodmarketkotlin.ui.home

import code.hyunwa.foodmarketkotlin.network.HttpClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class HomePresenter(private val view : HomeContract.View) : HomeContract.Presenter {

    private val mCompositeDisposable: CompositeDisposable?
    init {
        this.mCompositeDisposable = CompositeDisposable()
    }

    override fun getHome() {
        view.showLoading()
        val disposable = HttpClient.getInstance().getApi()!!.home()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view.dismissLoading()
                    if(it.meta?.status.equals("success")){
                        it.data?.let { it1 -> view.onHomeSuccess(it1) }
                    }
                    else{
                        view.onHomeFailed(it.meta?.message)
                    }
                },
                {
                    view.dismissLoading()
                    view.onHomeFailed(it.message.toString())
                }
            )
        mCompositeDisposable!!.add(disposable)
    }

    override fun subscribe() {}

    override fun unSubscribe() {
        mCompositeDisposable!!.clear()
    }
}