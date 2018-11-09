package zendeskigorkotlinkoin.ie.zendesk

/*** Created by igorfrankiv on 12/10/2018.*/
import io.reactivex.Observable
import retrofit2.http.GET
import io.reactivex.Single
import retrofit2.http.Url
import retrofit2.Call
import zendeskigorkotlinkoin.ie.model.TicketsResults

interface ZendeskSVDataSource {
//----------------------------------------------------------------------------
    @GET
    fun getTicketsSingle(@Url url: String): Single<TicketsResults>

    @GET
    fun getTicketsCall(@Url url: String): Call<TicketsResults>?

    @GET
    fun getTicketsObservable(@Url url: String): Observable<TicketsResults>
//----------------------------------------------------------------------------
}