package zendeskigorkotlinkoin.ie.screens.listoftickets.mvp

/*** Created by igorfrankiv on 12/10/2018.*/
import zendeskigorkotlinkoin.ie.util.mvp.BaseView
import zendeskigorkotlinkoin.ie.util.mvp.BasePresenter
import zendeskigorkotlinkoin.ie.model.Tickets

interface IListViewContract {
    interface IListView : BaseView<Presenter> {
        fun displayNormal()
        fun displayTickets(results:List<Tickets> )
        fun displayProgress()
        fun onTicketsFailed(error: Throwable)
    }

    interface Presenter : BasePresenter<IListView> {
        fun getAllTheTicketsCallable()
        fun getAllTheTicketsObservable()
        fun getAllTheTicketsCompletableSingle()
    }
}