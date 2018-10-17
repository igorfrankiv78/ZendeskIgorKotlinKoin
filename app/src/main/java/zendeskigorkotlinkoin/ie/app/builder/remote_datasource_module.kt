package zendeskigorkotlinkoin.ie.app.builder

/*** Created by igorfrankiv on 12/10/2018.*/
import retrofit2.Retrofit
import okhttp3.OkHttpClient
import okhttp3.Credentials
import org.koin.dsl.module.applicationContext
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import zendeskigorkotlinkoin.ie.app.builder.helpers.AuthenticationInterceptor
import zendeskigorkotlinkoin.ie.app.builder.helpers.SchedulerProvider
import zendeskigorkotlinkoin.ie.constants.UserParam
import zendeskigorkotlinkoin.ie.util.ext.MyObservable
import zendeskigorkotlinkoin.ie.zendesk.ZendeskSVDataSource
import zendeskigorkotlinkoin.ie.model.TicketsResults

val remoteDatasourceModule = applicationContext {
    // provided components
    bean { myObservable<TicketsResults>() }

    bean{ authenticationInterceptor() }

    bean{ okHttpClient( get() ) }

    bean{ zendeskService<ZendeskSVDataSource>( get(), get() ) }
}

// 0
inline fun <reified T> myObservable(): MyObservable<T> {
    return return MyObservable<T>()
}
// 1
fun authenticationInterceptor(): AuthenticationInterceptor {
    return AuthenticationInterceptor(Credentials.basic(UserParam.USERNAME,
            UserParam.PASSWORD))
}
// 2
fun okHttpClient( authenticationInterceptor: AuthenticationInterceptor): OkHttpClient {
    return OkHttpClient.Builder().addNetworkInterceptor(authenticationInterceptor).build()
}
//  3
inline fun <reified T>zendeskService( okHttpClient: OkHttpClient, androidSchedulers: SchedulerProvider): T {
    return Retrofit.Builder().
            baseUrl(UserParam.API_BASE_URL).
            addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(androidSchedulers.io())).
            addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build().
            create( T::class.java)
}