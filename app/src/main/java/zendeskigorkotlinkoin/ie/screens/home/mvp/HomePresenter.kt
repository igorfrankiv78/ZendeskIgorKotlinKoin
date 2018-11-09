package zendeskigorkotlinkoin.ie.screens.home.mvp

import android.util.Log
import io.reactivex.Observable
import zendeskigorkotlinkoin.ie.util.ext.with
import zendeskigorkotlinkoin.ie.model.TicketsResults
import zendeskigorkotlinkoin.ie.zendesk.IZendeskModel
import zendeskigorkotlinkoin.ie.util.rx.SchedulerProvider
import zendeskigorkotlinkoin.ie.screens.AbstractPresenter
//import rx.schedulers.Timestamped
//import io.reactivex.Scheduler.
import io.reactivex.Completable
import io.reactivex.functions.Action
import java.security.Timestamp

/*** Created by igorfrankiv on 12/10/2018. ***/
class HomePresenter
( val mIZendeskModel: IZendeskModel,
  val schedulerProvider: SchedulerProvider,
  override var view: IHomeViewContract.IHomeView ) :
                AbstractPresenter<IHomeViewContract.IHomeView,
                IHomeViewContract.Presenter>(),
                IHomeViewContract.Presenter {
//////      ======   1  switchIfEmpty
//    override fun getAllTheTicketsObservable(){
//            fun getNetworkTicketsResults(): Observable<TicketsResults> {
//
//                return Observable
//                        .fromCallable { view.displayProgress()  } // datasource.getTicketsCall(UserParam.URL)
//                        .observeOn( schedulerProvider.io())
//                        .flatMap {  mIZendeskModel.getAllTheTicketsObservable() } // network server call
//                        .observeOn( schedulerProvider.ui())
//                        .doOnEach { view.displayNormal() }
//            }
//
//            Observable.fromCallable {   }  // profileModel.getUserNameFromIntent() -> getting name from local resources
//                    .flatMap { username ->
//                   //Get ticketsResults from the saved state and if that observable is empty then switch to network
//                        mIZendeskModel.readIOdataRX()
//                            .switchIfEmpty(  getNetworkTicketsResults()  ) // call local fun
//                    }
//                    .doOnNext {
//                        ticketsResults -> mIZendeskModel.finalWriteIODataRX( ticketsResults.results ) //save the ticketsResults
//                    }
//                    .subscribe({ view.onTicketsSuccess() // ticketsResults -> view.onTicketsSuccess( ticketsResults )
//                    }, {throwable -> view.displayNormal(); view.onTicketsFailed(throwable)  })
//    }
//////      ======    2
//    override fun getAllTheTicketsObservable(){
//        launch {
//            fun getNetworkTicketsResults(): Observable<TicketsResults> {
//
//                return Observable
//                        .fromCallable { view.displayProgress() }
//                        .observeOn ( schedulerProvider.io() )
//                        .flatMap {  mIZendeskModel.getAllTheTicketsObservable() } // network server call
//                        .observeOn ( schedulerProvider.ui() )
//                        .doOnEach  { view.displayNormal() }
//            }
//
//            Observable.fromCallable {   }  // profileModel.getUserNameFromIntent() -> getting name from local resources
//                    .flatMap { username -> getNetworkTicketsResults() } // call local fun
//                    .doOnNext { ticketsResults -> mIZendeskModel.finalWriteIODataRX( ticketsResults.results )  } //save the ticketsResults
//                    .subscribe({ view.onTicketsSuccess() //  ticketsResults ->  view.displayTickets( ticketsResults )
//                    }, {throwable -> view.displayNormal(); view.onTicketsFailed(throwable) })
//        }
//    }


//      override fun getAllTheTicketsMergeObservable(){
//           launch {
//                  Observable. mergeDelayError( // or merge(
//                          mIZendeskModel.readIOdataRX().subscribeOn( schedulerProvider.io() ),
//                          mIZendeskModel.getAllTheTicketsObservable(). // or mIZendeskModel.getAllTheTicketsCallable().
//      //                     timestamp().
//      //                          flatMap { mIZendeskModel.getAllTheTicketsObservable() }.
//                                  doOnNext( { ticketsResults -> Log.e("doOnNext1 ", "doOnNext1 " );
//                                               Log.e("doOnNext ", ticketsResults.results.size.toString() )
//                                             }
//      //                                    object : Action<Timestamp<TicketsResults>>() {
//      //                                        fun call(recentPhotosResponse: Timestamp<TicketsResults>) {
//      ////                                            Log.d(CLASSNAME, "flickrApiRepository.getRecentPhotos().doOnNext() - Saving photos to disk - thread=" + Thread.currentThread().name)
//      ////                                            flickrDiskRepository.savePhotos(recentPhotosResponse)
//      //                                    { ticketsResults -> Log.e("doOnNext ", ticketsResults.time().toString() ) }
//      //                                        }
//      //                                    }
//
//                                          ////               ticketsResults -> mIZendeskModel.cacheTicketsResults( ticketsResults.results )
//      ////                Action1<TicketsResults>() {
//      ////          override
//      ////          fun void call(TicketsResults data) {
//      ////            mIZendeskModel.finalWriteIODataRX(data); // <-- save to cache
//      ////          }
//
//                                           ) .
//                          subscribeOn( schedulerProvider.io() ).
//                          observeOn( schedulerProvider.ui() )
//                          .doOnNext { //  ticketsResults -> Log.e("doOnNext ", ticketsResults.results.size.toString() )
//                              ticketsResults -> Log.e("doOnNext2 ", "doOnNext2 " )
//                          }
//                        ).subscribe( { it -> Log.e("subscribe ", "subscribe " )
//                                view.displayNormal(); view.onTicketsSuccess() },
//                              { throwable -> view.displayNormal(); view.onTicketsFailed(throwable) } )// show the results on the list view Activity
//           }
//       }
//
//







//////      ======    Observable Merge ======
 override fun getAllTheTicketsMergeObservable(){
     launch {
            Observable. mergeDelayError( // or merge(
                    mIZendeskModel.readIOdataRX().subscribeOn( schedulerProvider.io() ),
                    mIZendeskModel.getAllTheTicketsObservable().// or mIZendeskModel.getAllTheTicketsCallable().
//                              timestamp().
                                   doOnNext( {
                                       ticketsResults -> mIZendeskModel.finalWriteIODataRX( ticketsResults.results );
                                                         mIZendeskModel.cacheTicketsResults( ticketsResults.results );
//       Action1<Timestamped<RecentPhotosResponse>>() {
//                            @Override
//                            public void call(Timestamped<RecentPhotosResponse> recentPhotosResponse) {
//                                Log.d(CLASSNAME, "flickrApiRepository.getRecentPhotos().doOnNext() - Saving photos to disk - thread=" + Thread.currentThread().getName());
//                                flickrDiskRepository.savePhotos(recentPhotosResponse.);
//                            }
//                        }
                                 Log.e("Network ", "Network " )
                                             } ) .
                    subscribeOn( schedulerProvider.io() ) .
                    observeOn( schedulerProvider.ui() )
                  ).subscribe( { view.displayNormal(); view.onTicketsSuccess(); Log.e("subscribe ", "subscribe " )
            },{ throwable -> view.displayNormal(); view.onTicketsFailed(throwable) } )// show the results on the list view Activity
            }
 }
//////      ======    Observable      ======
override fun getAllTheTicketsObservable(){
    launch {
            Observable.fromCallable { view.displayProgress() }
                      .observeOn  ( schedulerProvider.io() )
                      .flatMap {   mIZendeskModel.getAllTheTicketsObservable() } // network server call
                      .observeOn ( schedulerProvider.ui() )
                      .doOnNext { ticketsResults -> mIZendeskModel.cacheTicketsResults( ticketsResults.results )  }// cache list result to arraylist Single
                      .subscribe( { view.displayNormal(); view.onTicketsSuccess() }, // show the results on the list view Activity
                                  { throwable -> view.displayNormal(); view.onTicketsFailed(throwable) } )
           }
}
////// ======    Completable      ======
    override fun getAllTheTicketsCompletable(){
        view.displayProgress()
        launch {
            mIZendeskModel.getAllTheTicketsCompletable()
                    .with(schedulerProvider)
                    .subscribe({
                        view.displayNormal()
                        view.onTicketsSuccess()

                    }, { error ->
                        view.displayNormal()
                        view.onTicketsFailed(error)
                    })
        }
    }
////// ======    Callable      ======
    override fun getAllTheTicketsCallable() {
        view.displayProgress()
        launch {
            mIZendeskModel.getAllTheTicketsCallable()
                    .with(schedulerProvider)
                    .subscribe(
                            {
                                mIZendeskModel.cacheTicketsResults( it.results )
                                view.displayNormal()
                                view.onTicketsSuccess()
                            },
                            { error -> view.displayNormal()
                                view.onTicketsFailed(error)
                            }
                    )
        }
    }
}
//      @RxLogObservable
//      fun getMergedPhotos():Observable<
////        Timestamped
//        <TicketsResults>>  {
//          return Observable.mergeDelayError(
//                  mIZendeskModel.readIOdataRX().subscribeOn(schedulerProvider.io()),
//                  mIZendeskModel.getAllTheTicketsObservable().timestamp().doOnNext(
////                          new Action1<Timestamped<RecentPhotosResponse>>() {
////                      @Override
////                      public void call(Timestamped<RecentPhotosResponse> recentPhotosResponse) {
////                          Log.d(CLASSNAME, "flickrApiRepository.getRecentPhotos().doOnNext() - Saving photos to disk - thread=" + Thread.currentThread().getName());
////                          flickrDiskRepository.savePhotos(recentPhotosResponse);
////                      }
////                  }
//    ).subscribeOn(schedulerProvider.io())
//          );
//      }                                                                                                                                                                          