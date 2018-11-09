package zendeskigorkotlinkodein.ie.kointest.services_test_interface

/*** Created by igorfrankiv on 20/10/2018.*/
import org.junit.Test
import org.junit.After
import org.junit.Before
import org.koin.test.KoinTest
import org.koin.standalone.inject
import org.junit.Assert.assertEquals
import org.koin.standalone.StandAloneContext.closeKoin
import org.koin.standalone.StandAloneContext.startKoin
import zendeskigorkotlinkoin.ie.zendesk.IZendeskModel
import zendeskigorkotlinkodein.ie.kointest.helpers.di.testApp

class IZendeskModelTest : KoinTest {

    val repository by inject<IZendeskModel>()

    @Before
    fun before() {
        startKoin(testApp)
    }

    @After
    fun after() {
        closeKoin()
    }

    @Test // PASSED
    fun testCachedSearch() {
        val tickets1 = repository.getAllTheTicketsCompletable().blockingGet()
        val tickets2 = repository.getAllTheTicketsCompletable().blockingGet()
        assertEquals( tickets1, tickets2 )
    }

    @Test // PASSED
    fun testGetTicketsSuccess() {
        repository.getAllTheTicketsCompletable().blockingGet()
        val test = repository.getAllTheTicketsSingle().test()
        test.awaitTerminalEvent()
        test.assertComplete()
    }

    @Test // PASSED
    fun testGetTicketsFailed() {
        val test = repository.getAllTheTicketsSingle().test()
        test.awaitTerminalEvent()
        test.assertValue { list -> list.isEmpty() }
    }
}