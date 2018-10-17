package zendeskigorkotlinkoin.ie.screens.listoftickets.mvp

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import zendeskigorkotlinkoin.ie.model.Tickets
import zendeskigorkotlinkoin.ie.model.TicketsResults
/*** Created by igorfrankiv on 12/10/2018.*/

interface IListModel {

    fun getAllTheTicketsObservable(): Observable<TicketsResults>
    fun getAllTheTicketsCallable(): Observable<TicketsResults>
    fun getAllTheTicketsCompletable(): Completable
    fun getAllTheTicketsSingle(): Single<List<Tickets>>

}