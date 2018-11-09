package zendeskigorkotlinkoin.ie.screens.detail.mvp

import zendeskigorkotlinkoin.ie.util.rx.SchedulerProvider
import zendeskigorkotlinkoin.ie.screens.AbstractPresenter
import zendeskigorkotlinkoin.ie.util.ext.with
import zendeskigorkotlinkoin.ie.zendesk.IZendeskModel

/*** Created by igorfrankiv on 12/10/2018.*/
class DetailPresenter
        (val mIZendeskModel: IZendeskModel,
         val schedulerProvider: SchedulerProvider,
         override var view: IDetailViewContract.IDetailView) :
                AbstractPresenter<IDetailViewContract.IDetailView,
                IDetailViewContract.Presenter>(),
                IDetailViewContract.Presenter {

    override fun getDetail(id: String) {
        launch {
            mIZendeskModel.getSelectedTicketDetailSingle(id).
                    with(schedulerProvider).subscribe(
                    { detail -> view.displayDetail(detail)
                    }, { err -> println("DetailPresenter error : $err") })
        }
    }
}