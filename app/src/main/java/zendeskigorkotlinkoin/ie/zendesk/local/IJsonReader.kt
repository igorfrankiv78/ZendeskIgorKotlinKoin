package zendeskigorkotlinkoin.ie.zendesk.local

import zendeskigorkotlinkoin.ie.model.TicketsResults
/*** Created by igorfrankiv on 22/10/2018.*/
/*** Json reader*/
interface IJsonReader {
    fun getTickets( name: String ): TicketsResults
}