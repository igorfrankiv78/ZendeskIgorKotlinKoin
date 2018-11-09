package zendeskigorkotlinkodein.ie.kointest.helpers.di

/*** Created by igorfrankiv on 24/10/2018.*/
import org.koin.dsl.module.applicationContext
import zendeskigorkotlinkoin.ie.util.rx.SchedulerProvider
import zendeskigorkotlinkoin.ie.app.builder.myObservable
import zendeskigorkotlinkoin.ie.app.builder.zendeskApp
import zendeskigorkotlinkodein.ie.kointest.helpers.datasource.JavaReaderI
import zendeskigorkotlinkodein.ie.kointest.helpers.util.TestSchedulerProvider
import zendeskigorkotlinkoin.ie.zendesk.ZendeskSVDataSource
import zendeskigorkotlinkoin.ie.zendesk.local.LocalDataSource
import zendeskigorkotlinkoin.ie.model.TicketsResults

val localJavaDatasourceModule = applicationContext {
    provide { myObservable<TicketsResults>() }
    provide { LocalDataSource(JavaReaderI()) as ZendeskSVDataSource }
}

val testRxModule = applicationContext {
    // provided components
    provide { TestSchedulerProvider() as SchedulerProvider }
}

val testApp = zendeskApp + testRxModule + localJavaDatasourceModule