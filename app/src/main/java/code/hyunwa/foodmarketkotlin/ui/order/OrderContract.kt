package code.hyunwa.foodmarketkotlin.ui.order

import code.hyunwa.foodmarketkotlin.base.BasePresenter
import code.hyunwa.foodmarketkotlin.base.BaseView
import code.hyunwa.foodmarketkotlin.model.response.transaction.TransactionResponse

interface OrderContract {

    interface View:BaseView{
        fun onTransactionSuccess(transactionResponse: TransactionResponse)
        fun onTransactionFailed(message: String)
    }

    interface Presenter: OrderContract, BasePresenter{
        fun getTransaction()
    }
}