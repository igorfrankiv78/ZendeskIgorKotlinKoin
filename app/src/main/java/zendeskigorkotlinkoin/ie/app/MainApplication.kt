package zendeskigorkotlinkoin.ie.app

/*** Created by igorfrankiv on 12/10/2018.*/
import android.app.Application
import org.koin.ContextCallback
import org.koin.android.ext.android.startKoin
import org.koin.standalone.StandAloneContext.registerContextCallBack
import zendeskigorkotlinkoin.ie.app.builder.zendeskApp
import zendeskigorkotlinkoin.ie.app.builder.remoteDatasourceModule

/*** Main Application*/
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // start Koin context
        startKoin(this,zendeskApp + remoteDatasourceModule)

        // Listen with ContextCallback
        registerContextCallBack(object : ContextCallback {
            // Notified on context dropped
            override fun onContextReleased(contextName: String) {
                println("Context $contextName has been dropped")
            }

        })
        
    }
}