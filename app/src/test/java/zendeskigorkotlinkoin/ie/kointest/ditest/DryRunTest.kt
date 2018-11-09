package zendeskigorkotlinkodein.ie.kointest.ditest

/*** Created by igorfrankiv on 18/10/2018.*/
// Libraries imports
import org.mockito.Mockito.mock
import org.koin.test.KoinTest
import org.koin.test.dryRun
import org.junit.After
import org.junit.Test
import org.koin.standalone.StandAloneContext.closeKoin
import org.koin.standalone.StandAloneContext.startKoin
import zendeskigorkotlinkoin.ie.app.builder.Params.DETAIL_VIEW
import zendeskigorkotlinkoin.ie.app.builder.Params.LIST_VIEW
// Project's imports
import zendeskigorkotlinkoin.ie.app.builder.remoteDatasourceModule
import zendeskigorkotlinkoin.ie.app.builder.Params.HOME_VIEW
import zendeskigorkotlinkoin.ie.app.builder.zendeskApp
import zendeskigorkotlinkodein.ie.kointest.helpers.di.testApp
import zendeskigorkotlinkoin.ie.screens.detail.mvp.IDetailViewContract
import zendeskigorkotlinkoin.ie.screens.home.mvp.IHomeViewContract
import zendeskigorkotlinkoin.ie.screens.listoftickets.mvp.IListViewContract

class DryRunTest : KoinTest {

    val params = mapOf(
            DETAIL_VIEW to mock(IDetailViewContract.IDetailView::class.java),
                   HOME_VIEW to mock(IHomeViewContract.IHomeView::class.java),
                   LIST_VIEW to mock(IListViewContract.IListView::class.java)
    )

    @After
    fun after() {
        closeKoin()
    }

    @Test
    fun testRemoteConfiguration() {
        startKoin(zendeskApp + remoteDatasourceModule)
        dryRun { params }
    }

    @Test
    fun testLocalConfiguration() {
        startKoin(testApp)
        dryRun { params }
    }
}
