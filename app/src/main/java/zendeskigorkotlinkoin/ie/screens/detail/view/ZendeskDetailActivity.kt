package zendeskigorkotlinkoin.ie.screens.detail.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_zendesk_detail.*
import org.koin.android.ext.android.inject
import zendeskigorkotlinkoin.ie.app.builder.Params
import zendeskigorkotlinkoin.ie.model.Tickets
import zendeskigorkotlinkoin.ie.screens.Arguments.ARG_TICKET_ITEM_ID
import zendeskigorkotlinkoin.ie.screens.detail.mvp.IDetailViewContract
import zendeskigorkotlinkoin.ie.util.ext.argument
import zendeskigorkotlinkoin.ie.R

/*** Created by igorfrankiv on 23/10/2018.*/

class ZendeskDetailActivity : AppCompatActivity(), IDetailViewContract.IDetailView {

    private val detailId by argument<String>(ARG_TICKET_ITEM_ID)
    // Presenter
    override val presenter: IDetailViewContract.Presenter by inject { mapOf(Params.DETAIL_VIEW to this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zendesk_detail)
    }

    override fun onStart() {
        super.onStart()
        presenter.getDetail(detailId)
    }

    override fun onDestroy() {
        presenter.stop()
        super.onDestroy()
    }

    override fun displayDetail( tickets: Tickets) {
        idOfTheTicket.text =  tickets.id.toString()
        subjectOfTheTicket.text = tickets.subject
        descriptionOfTheTicket.text = tickets.description
        statusOfTheTicket.text = tickets.status
    }

    override fun onTicketsFailed( error: Throwable ){

    }
}
