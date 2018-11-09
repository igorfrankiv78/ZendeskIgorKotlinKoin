package zendeskigorkotlinkoin.ie.app.builder

/*** Created by igorfrankiv on 12/10/2018.*/
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.applicationContext
import zendeskigorkotlinkoin.ie.app.builder.Params.HOME_VIEW
import zendeskigorkotlinkoin.ie.app.builder.Params.LIST_VIEW
import zendeskigorkotlinkoin.ie.app.builder.Params.DETAIL_VIEW
import zendeskigorkotlinkoin.ie.util.rx.ApplicationSchedulerProvider
import zendeskigorkotlinkoin.ie.util.rx.SchedulerProvider
import zendeskigorkotlinkoin.ie.screens.detail.mvp.DetailPresenter
import zendeskigorkotlinkoin.ie.screens.detail.mvp.IDetailViewContract
import zendeskigorkotlinkoin.ie.screens.home.mvp.HomePresenter
import zendeskigorkotlinkoin.ie.screens.home.mvp.IHomeViewContract
import zendeskigorkotlinkoin.ie.screens.listoftickets.mvp.IListViewContract
import zendeskigorkotlinkoin.ie.screens.listoftickets.mvp.ListPresenter
import zendeskigorkotlinkoin.ie.zendesk.IZendeskModel
import zendeskigorkotlinkoin.ie.zendesk.ZendeskModel

val zendeskModule = applicationContext {
    // Presenter for Search View
    factory { params -> HomePresenter( get(), get(), params[HOME_VIEW] ) as IHomeViewContract.Presenter }

    // Presenters for Result View
    factory { params -> ListPresenter(get(), get(), params[LIST_VIEW]) as IListViewContract.Presenter }

    // Presenter for Detail View
    factory { params -> DetailPresenter(get(), get(), params[DETAIL_VIEW]) as IDetailViewContract.Presenter }

    // Zendesk Data Repository
    bean { ZendeskModel( get(), get(), androidApplication() ) as IZendeskModel }
//    bean { ServiceModel( androidApplication() ) as IServiceModel }
}

val rxModule = applicationContext {
    // provided components
    bean { ApplicationSchedulerProvider() as SchedulerProvider }
}

object Params {
    const val HOME_VIEW = "HOME_VIEW"
    const val LIST_VIEW = "LIST_VIEW"
    const val DETAIL_VIEW = "DETAIL_VIEW"
}
// Gather all app modules
val zendeskApp = listOf( zendeskModule, rxModule )