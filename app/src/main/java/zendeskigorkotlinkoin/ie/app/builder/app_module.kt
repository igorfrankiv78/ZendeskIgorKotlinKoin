package zendeskigorkotlinkoin.ie.app.builder

/*** Created by igorfrankiv on 12/10/2018.*/
import org.koin.dsl.module.applicationContext
import zendeskigorkotlinkoin.ie.app.builder.Params.SEARCH_VIEW
import zendeskigorkotlinkoin.ie.app.builder.helpers.ApplicationSchedulerProvider
import zendeskigorkotlinkoin.ie.app.builder.helpers.SchedulerProvider
import zendeskigorkotlinkoin.ie.screens.listoftickets.mvp.IListViewContract
import zendeskigorkotlinkoin.ie.screens.listoftickets.mvp.ListPresenter
import zendeskigorkotlinkoin.ie.screens.listoftickets.mvp.IListModel
import zendeskigorkotlinkoin.ie.screens.listoftickets.mvp.ListModel

val zendeskModule = applicationContext {
    // Presenter for Search View
    factory { params -> ListPresenter( get(), get(), params[SEARCH_VIEW] ) as IListViewContract.Presenter }
    // Zendesk Data Repository
    bean { ListModel(get(), get() ) as IListModel }
}

val rxModule = applicationContext {
    // provided components
    bean { ApplicationSchedulerProvider() as SchedulerProvider }
}

object Params {
    const val SEARCH_VIEW = "SEARCH_VIEW"
}
// Gather all app modules
val zendeskApp = listOf(
        zendeskModule,
        rxModule)