package zendeskigorkotlinkoin.ie.zendesk.local

/*** Created by igorfrankiv on 22/10/2018.*/
import retrofit2.Call
import retrofit2.http.Url
import io.reactivex.Single
import io.reactivex.Observable
import zendeskigorkotlinkoin.ie.model.Tickets
import zendeskigorkotlinkoin.ie.model.TicketsResults
import zendeskigorkotlinkoin.ie.zendesk.ZendeskSVDataSource

/*** Read json files and render weather data*/
class LocalDataSource(val IJsonReader: IJsonReader) : ZendeskSVDataSource {

    val mListOfAllTheTickets = arrayListOf<Tickets>()

    //  @GET
    override fun getTicketsSingle(@Url url: String): Single<TicketsResults>{

    return Single.create { s ->
        val geocode = IJsonReader.getTickets(ALL_ZENDESK_TICKETS)
        s.onSuccess(geocode)
    }
    }

//  @GET
    override fun getTicketsCall(@Url url: String): Call<TicketsResults>? {

//    val authenticationInterceptor = AuthenticationInterceptor(Credentials.basic(UserParam.USERNAME,
//            UserParam.PASSWORD))
//
//    val okHttpClient = OkHttpClient.Builder().addNetworkInterceptor(authenticationInterceptor).build()
//
//    val datasource: ZendeskSVDataSource =
//            Retrofit.Builder().
//                    baseUrl(UserParam.API_BASE_URL).
//                    addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build().
//                    create( ZendeskSVDataSource::class.java)
//    return datasource.getTicketsCall("")
    return null
    }

    //    @GET
    override fun getTicketsObservable(@Url url: String): Observable<TicketsResults>{

    return Observable.just(IJsonReader.getTickets(ALL_ZENDESK_TICKETS) )
    }

    companion object {
        const val ALL_ZENDESK_TICKETS = "200_ok_response"
    }
}