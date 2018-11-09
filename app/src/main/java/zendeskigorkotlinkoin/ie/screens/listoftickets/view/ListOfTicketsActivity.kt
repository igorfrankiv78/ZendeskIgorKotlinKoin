package zendeskigorkotlinkoin.ie.screens.listoftickets.view

/*** Created by igorfrankiv on 12/10/2018. ***/
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import org.jetbrains.anko.startActivity
import org.koin.android.ext.android.inject
import zendeskigorkotlinkoin.ie.app.builder.Params
import zendeskigorkotlinkoin.ie.model.Tickets
import zendeskigorkotlinkoin.ie.model.TicketsResults
import zendeskigorkotlinkoin.ie.screens.listoftickets.mvp.IListViewContract
import zendeskigorkotlinkoin.ie.screens.listoftickets.view.viewadapt.ZendeskListViewAdapter
import zendeskigorkotlinkoin.ie.screens.Arguments.ARG_TICKET
import zendeskigorkotlinkoin.ie.screens.Arguments.ARG_TICKET_ITEM_ID
import zendeskigorkotlinkoin.ie.screens.detail.view.ZendeskDetailActivity
import zendeskigorkotlinkoin.ie.util.ext.argument
import zendeskigorkotlinkoin.ie.R
/*** Created by igorfrankiv on 30/09/2018. ***/

class ListOfTicketsActivity : AppCompatActivity(), IListViewContract.IListView {

    // Presenter
    override val presenter: IListViewContract.Presenter by inject { mapOf(Params.LIST_VIEW to this) }

    val address: String by argument(ARG_TICKET)

    private var mRecyclerViewVirtical: RecyclerView? = null
    private var mZendeskListViewAdapter: ZendeskListViewAdapter? = null
    private val errorMessage:String = "Error Loading Zendesk Tickets"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView( R.layout.activity_zendesk_list)
        Log.e("ListOfTicketsActivity", "ListOfTicketsActivity")
        presenter.getAllTheTicketsSingle()
    }

    override fun onDestroy() {
        presenter.stop()
        super.onDestroy()
    }

    override fun onTicketsFailed(error: Throwable) {
        Snackbar.make(this.currentFocus, "Got error : $error", Snackbar.LENGTH_LONG).show()
        alertDialog( errorMessage )
    }

    override fun displayTickets(results: List<Tickets>) {

            mZendeskListViewAdapter = ZendeskListViewAdapter( TicketsResults(results), this,
                    { weatherDetail -> presenter.selectTicketsDetail(weatherDetail) })

            mRecyclerViewVirtical = findViewById<View>(R.id.recycler_view) as RecyclerView
            val layoutManager = LinearLayoutManager(getApplicationContext());
            mRecyclerViewVirtical!!.setLayoutManager(layoutManager);
            mRecyclerViewVirtical!!.setAdapter(mZendeskListViewAdapter);
    }

    override fun onDetailSaved(id : String){
        startActivity<ZendeskDetailActivity>(ARG_TICKET_ITEM_ID to id )
    }

    fun alertDialog(message: String){
        AlertDialog.Builder(this)
                .setTitle(message)
                .setPositiveButton(android.R.string.ok) { dialogInterface, i -> dialogInterface.dismiss() }
                .show()
    }
}