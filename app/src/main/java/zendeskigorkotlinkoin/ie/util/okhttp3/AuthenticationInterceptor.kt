package zendeskigorkotlinkoin.ie.util.okhttp3

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/*** Created by igorfrankiv on 07/10/2018.*/
class AuthenticationInterceptor(private val authToken: String) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
                chain.request().newBuilder().header("Authorization", authToken).build())
    }
}