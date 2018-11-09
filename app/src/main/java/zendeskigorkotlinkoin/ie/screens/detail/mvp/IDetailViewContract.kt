package zendeskigorkotlinkoin.ie.screens.detail.mvp

import zendeskigorkotlinkoin.ie.model.Tickets
import zendeskigorkotlinkoin.ie.util.mvp.BasePresenter
import zendeskigorkotlinkoin.ie.util.mvp.BaseView

/*** Created by igorfrankiv on 23/10/2018.*/

interface IDetailViewContract {

    interface IDetailView : BaseView<Presenter> {
      fun displayDetail( tickets: Tickets)
      fun onTicketsFailed( error: Throwable )
    }

    interface Presenter : BasePresenter<IDetailView> {
          fun getDetail(id : String)
    }
}