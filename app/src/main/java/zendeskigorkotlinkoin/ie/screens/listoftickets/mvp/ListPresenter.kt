package zendeskigorkotlinkoin.ie.screens.listoftickets.mvp

import zendeskigorkotlinkoin.ie.model.Tickets
import zendeskigorkotlinkoin.ie.util.ext.with
import zendeskigorkotlinkoin.ie.util.rx.SchedulerProvider
import zendeskigorkotlinkoin.ie.screens.AbstractPresenter
import zendeskigorkotlinkoin.ie.zendesk.IZendeskModel

/*** Created by igorfrankiv on 12/10/2018. ***/
class ListPresenter
        (val mIZendeskModel: IZendeskModel,
         val schedulerProvider: SchedulerProvider,

         override var view: IListViewContract.IListView) :
        AbstractPresenter<IListViewContract.IListView,
                          IListViewContract.Presenter>(),
                          IListViewContract.Presenter {

    override fun getAllTheTicketsSingle(){
        launch {
            mIZendeskModel.getAllTheTicketsSingle()
                    .with(schedulerProvider)
                    .subscribe(
                        { ticketsList -> view.displayTickets( ticketsList ) },
                        { error -> view.onTicketsFailed(error) }
                    )
              }
    }

    override fun selectTicketsDetail(detail: Tickets) {
        launch {
            mIZendeskModel.getSelectedTicketDetailSingle( detail.id.toString() )
                    .with(schedulerProvider)
                    .subscribe({
                         view.onDetailSaved( detail.id.toString() )
                    },
                    { err -> view.onTicketsFailed(err) })
        }
    }
}