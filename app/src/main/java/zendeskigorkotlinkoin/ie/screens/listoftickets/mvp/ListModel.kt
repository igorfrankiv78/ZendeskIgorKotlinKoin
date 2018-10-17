package zendeskigorkotlinkoin.ie.screens.listoftickets.mvp

import io.reactivex.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import zendeskigorkotlinkoin.ie.constants.UserParam
import zendeskigorkotlinkoin.ie.model.Tickets

import zendeskigorkotlinkoin.ie.model.TicketsResults
import zendeskigorkotlinkoin.ie.util.ext.MyObservable
import zendeskigorkotlinkoin.ie.zendesk.ZendeskSVDataSource

/*** Created by igorfrankiv on 12/10/2018.*/
class ListModel(private val datasource: ZendeskSVDataSource, val myObservable: MyObservable<TicketsResults>): IListModel {

   companion object { const val ERROR = "No Tickets data" }

   val mListOfAllTheTickets = arrayListOf<Tickets>()
//  Observable
//------------------------------------------------------------------------------------------------------------
    override fun getAllTheTicketsObservable(): Observable<TicketsResults> {
         return datasource.getTicketsObservable(UserParam.URL)
    }
//  Completable
//------------------------------------------------------------------------------------------------------------
    override fun getAllTheTicketsCompletable(): Completable {
        return datasource.getTicketsSingle(UserParam.URL)
                .map { it.results ?: throw IllegalStateException(ERROR) }
                .doOnSuccess { mListOfAllTheTickets.clear();  mListOfAllTheTickets.addAll(it) }
                .toCompletable()
    }

    override fun getAllTheTicketsSingle(): Single<List<Tickets>> = Single.just( mListOfAllTheTickets )
//  Callable
//------------------------------------------------------------------------------------------------------------
     override fun getAllTheTicketsCallable( ): Observable<TicketsResults> {
            datasource.getTicketsCall(UserParam.URL).enqueue(object : Callback<TicketsResults> {

                override fun onResponse(call: Call<TicketsResults>?, response: Response<TicketsResults>?) {
                    if (response != null && response.isSuccessful)
                        myObservable.add( response.body() )
                    else
                        myObservable.observable.doOnError({ throw IllegalStateException(ERROR) })   // response.code()
                }

                override fun onFailure(call: Call<TicketsResults>?, t: Throwable?) {
                    myObservable.observable.doOnError({ throwable -> throwable.cause })
                }
            }
     )
    return myObservable.observable
    }
//------------------------------------------------------------------------------------------------------------
}


