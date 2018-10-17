package zendeskigorkotlinkoin.ie.screens.listoftickets.mvp

import android.util.Log
import io.reactivex.Observable
import zendeskigorkotlinkoin.ie.app.builder.helpers.SchedulerProvider
import zendeskigorkotlinkoin.ie.screens.AbstractPresenter
import zendeskigorkotlinkoin.ie.model.TicketsResults
import zendeskigorkotlinkoin.ie.util.ext.with

/*** Created by igorfrankiv on 12/10/2018.*/
class ListPresenter
        (val mIListModel: IListModel,
         val schedulerProvider: SchedulerProvider,
         override var view: IListViewContract.IListView) :
        AbstractPresenter<IListViewContract.IListView,
                          IListViewContract.Presenter>(),
                          IListViewContract.Presenter {
    
      override fun getAllTheTicketsObservable(){
           launch {
               fun getNetworkProfileUserInfo(): Observable<TicketsResults> {
                   return Observable
                           .fromCallable { view.displayProgress()  } // datasource.getTicketsCall(UserParam.URL)
                           .observeOn(schedulerProvider.io())
                           .flatMap { mIListModel.getAllTheTicketsObservable() }
                           .observeOn(schedulerProvider.ui())
                           .doOnEach { view.displayNormal() }
               }

               Observable.fromCallable {   }  // profileModel.getUserNameFromIntent()
                       .flatMap { username -> getNetworkProfileUserInfo() }
                       .doOnNext { userInfo -> Log.e("333doOnNext=", userInfo.results.size.toString()) } // userInfo -> profileModel.saveUserInfoState(userInfo) //save the userinfo
                       .subscribe({ userInfo ->  view.displayTickets( userInfo.results )
                       }, {throwable -> view.displayNormal(); view.onTicketsFailed(throwable)  })
           }
      }

    override fun getAllTheTicketsCallable() {
                 view.displayProgress()
        launch {
                   mIListModel.getAllTheTicketsCallable()
                   .with(schedulerProvider)
                   .subscribe(
                     {
                       view.displayNormal()
                       view.displayTickets( it.results )
                      },
                      { error -> view.displayNormal()
                        view.onTicketsFailed(error)
                      }
                    )
        }
    }
    
     override fun getAllTheTicketsCompletableSingle(){
         view.displayProgress()
         launch {
             mIListModel.getAllTheTicketsCompletable()
                     .with(schedulerProvider)
                     .subscribe({

                             mIListModel.getAllTheTicketsSingle()
                             .with(schedulerProvider)
                             .subscribe( {
                                 view.displayNormal()
                                 view.displayTickets( it )
                             },{  error ->
                                 view.displayNormal()
                                 view.onTicketsFailed(error)
                             } )

                     }, { error ->
                         view.displayNormal()
                         view.onTicketsFailed(error)
                     })
                 }
             }
}