package zendeskigorkotlinkoin.ie.app.builder

/*** Created by igorfrankiv on 12/10/2018.*/
import retrofit2.Retrofit
import okhttp3.OkHttpClient
import okhttp3.Credentials
import org.koin.dsl.module.applicationContext
import zendeskigorkotlinkoin.ie.model.TicketsResults
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import zendeskigorkotlinkoin.ie.app.builder.DatasourceProperties.USERNAME
import zendeskigorkotlinkoin.ie.app.builder.DatasourceProperties.PASSWORD
import zendeskigorkotlinkoin.ie.app.builder.DatasourceProperties.API_BASE_URL
import zendeskigorkotlinkoin.ie.util.okhttp3.AuthenticationInterceptor
import zendeskigorkotlinkoin.ie.util.rx.SchedulerProvider
import zendeskigorkotlinkoin.ie.util.ext.TObservable
import zendeskigorkotlinkoin.ie.zendesk.ZendeskSVDataSource

val remoteDatasourceModule = applicationContext {
    // provided components
    bean { myObservable<TicketsResults>() }

    bean{ authenticationInterceptor(getProperty( USERNAME ), getProperty( PASSWORD )) }

    bean{ okHttpClient( get() ) }

    bean{ zendeskService<ZendeskSVDataSource>( get(), get(), getProperty( API_BASE_URL ) ) }
}

object DatasourceProperties {
    const val URL="URL"
    const val API_BASE_URL="API_BASE_URL" // UserParam.API_BASE_URL
    const val USERNAME="USERNAME" // UserParam.USERNAME
    const val PASSWORD="PASSWORD" // UserParam.PASSWORD
}

//  0
inline fun <reified T> myObservable(): TObservable<T> {
    return return TObservable<T>()
}
//  1
fun authenticationInterceptor(username:String, password:String): AuthenticationInterceptor {
    return AuthenticationInterceptor(Credentials.basic(username, password))
}
//  2
fun okHttpClient( authenticationInterceptor: AuthenticationInterceptor): OkHttpClient {
    return OkHttpClient.Builder().addNetworkInterceptor(authenticationInterceptor).build()
}
//  3
inline fun <reified T>zendeskService(okHttpClient: OkHttpClient, androidSchedulers: SchedulerProvider, apiBaseUrl:String ): T {
    return Retrofit.Builder().
            baseUrl( apiBaseUrl).
            addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(androidSchedulers.io())).
            addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build().
            create( T::class.java)
}