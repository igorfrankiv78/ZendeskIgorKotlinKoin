package zendeskigorkotlinkodein.ie.kointest.listoftickets

/*** Created by igorfrankiv on 24/10/2018.*/
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.standalone.inject
import org.koin.test.KoinTest
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.koin.standalone.StandAloneContext.closeKoin
import org.koin.standalone.StandAloneContext.startKoin
import zendeskigorkotlinkoin.ie.app.builder.Params.LIST_VIEW
import zendeskigorkotlinkodein.ie.kointest.helpers.di.testApp
import zendeskigorkotlinkoin.ie.screens.listoftickets.mvp.IListViewContract

class ListPresenterTest : KoinTest {

    val view: IListViewContract.IListView = mock(IListViewContract.IListView::class.java)
    val presenter: IListViewContract.Presenter by inject { mapOf(LIST_VIEW to view) }

    @Before
    fun before() {
        startKoin(testApp)
    }

    @After
    fun after() {
        closeKoin()
    }

    @Test
    fun testDisplayTickets() {
        presenter.getAllTheTicketsSingle()
        Mockito.verify(view).displayTickets( emptyList() )
    }
}