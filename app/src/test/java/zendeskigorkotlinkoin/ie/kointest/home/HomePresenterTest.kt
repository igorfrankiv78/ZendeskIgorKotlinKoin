package zendeskigorkotlinkodein.ie.kointest.home

/*** Created by igorfrankiv on 21/10/2018. */
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.test.KoinTest
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.koin.standalone.inject
import org.koin.standalone.StandAloneContext.closeKoin
import org.koin.standalone.StandAloneContext.startKoin
import zendeskigorkotlinkoin.ie.app.builder.Params.HOME_VIEW
import zendeskigorkotlinkodein.ie.kointest.helpers.di.testApp
import zendeskigorkotlinkoin.ie.screens.home.mvp.IHomeViewContract

class HomePresenterTest : KoinTest {

    val view: IHomeViewContract.IHomeView by lazy { mock(IHomeViewContract.IHomeView::class.java) }
    val presenter: IHomeViewContract.Presenter by inject { mapOf(HOME_VIEW to view) }

    @Before
    fun before() {
        startKoin(testApp)
    }

    @After
    fun after() {
        closeKoin()
    }

    @Test // PASSED
    fun testGetTickets() {
        presenter.getAllTheTicketsCompletable()

        Mockito.verify(view).displayNormal()
        Mockito.verify(view).onTicketsSuccess()
    }
}