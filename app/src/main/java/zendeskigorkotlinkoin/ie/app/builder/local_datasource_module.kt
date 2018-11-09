package zendeskigorkotlinkoin.ie.app.builder

import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.applicationContext
import zendeskigorkotlinkoin.ie.model.TicketsResults
import zendeskigorkotlinkoin.ie.zendesk.ZendeskSVDataSource
import zendeskigorkotlinkoin.ie.zendesk.local.AndroidIJsonReader
import zendeskigorkotlinkoin.ie.zendesk.local.IJsonReader
import zendeskigorkotlinkoin.ie.zendesk.local.LocalDataSource

/*** Created by igorfrankiv on 22/10/2018. */

val localAndroidDatasourceModule = applicationContext {
    bean { AndroidIJsonReader( androidApplication() ) as IJsonReader }
    bean { LocalDataSource( get() ) as ZendeskSVDataSource }
    bean { myObservable<TicketsResults>() }
}