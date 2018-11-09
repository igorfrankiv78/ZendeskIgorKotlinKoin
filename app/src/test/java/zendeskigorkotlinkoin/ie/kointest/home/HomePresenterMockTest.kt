package zendeskigorkotlinkodein.ie.kointest.home

/*** Created by igorfrankiv on 21/10/2018.*/
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.koin.test.KoinTest
import io.reactivex.Completable
import org.junit.Before
import org.junit.Test
import zendeskigorkotlinkodein.ie.kointest.helpers.util.TestSchedulerProvider
import zendeskigorkotlinkoin.ie.screens.home.mvp.HomePresenter
import zendeskigorkotlinkoin.ie.screens.home.mvp.IHomeViewContract
import zendeskigorkotlinkoin.ie.zendesk.IZendeskModel

class HomePresenterMockTest : KoinTest {

    lateinit var presenter: IHomeViewContract.Presenter

    @Mock
    lateinit var view: IHomeViewContract.IHomeView

    @Mock
    lateinit var repository: IZendeskModel

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this )
        presenter = HomePresenter( repository, TestSchedulerProvider(), view )
    }

    @Test // PASSED
    fun testGetTickets() {
        `when`(repository.getAllTheTicketsCompletable()).thenReturn(Completable.complete())

        presenter.getAllTheTicketsCompletable()

        Mockito.verify(view).displayNormal()
        Mockito.verify(view).onTicketsSuccess()
    }

    @Test // PASSED
    fun testGetTicketsFailed() {
        val error = IllegalStateException("Got an error")

        `when`(repository.getAllTheTicketsCompletable()).thenReturn(Completable.error(error))

        presenter.getAllTheTicketsCompletable()

        Mockito.verify(view).displayNormal()
        Mockito.verify(view).onTicketsFailed(error)
    }
}