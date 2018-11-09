package zendeskigorkotlinkoin.ie.screens.listoftickets.mvp

/*** Created by igorfrankiv on 12/10/2018.*/
import zendeskigorkotlinkoin.ie.model.Tickets
import zendeskigorkotlinkoin.ie.util.mvp.BaseView
import zendeskigorkotlinkoin.ie.util.mvp.BasePresenter

interface IListViewContract {

    interface IListView : BaseView<Presenter> {
        fun onDetailSaved(id : String)
        fun displayTickets(results:List<Tickets> )
        fun onTicketsFailed(error: Throwable)
    }

    interface Presenter : BasePresenter<IListView> {
        fun getAllTheTicketsSingle()
        fun selectTicketsDetail(detail: Tickets)
    }
}