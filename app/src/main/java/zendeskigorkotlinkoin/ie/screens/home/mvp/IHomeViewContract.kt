package zendeskigorkotlinkoin.ie.screens.home.mvp

import zendeskigorkotlinkoin.ie.util.mvp.BasePresenter
import zendeskigorkotlinkoin.ie.util.mvp.BaseView

/*** Created by igorfrankiv on 23/10/2018. */
interface IHomeViewContract {

    interface IHomeView : BaseView<Presenter> {
        fun displayProgress()
        fun displayNormal()
        fun onTicketsSuccess()
        fun onTicketsFailed(error: Throwable)
    }

    interface Presenter : BasePresenter<IHomeView> {
        fun getAllTheTicketsCallable()
        fun getAllTheTicketsObservable()
        fun getAllTheTicketsMergeObservable()
        fun getAllTheTicketsCompletable()
    }
}
