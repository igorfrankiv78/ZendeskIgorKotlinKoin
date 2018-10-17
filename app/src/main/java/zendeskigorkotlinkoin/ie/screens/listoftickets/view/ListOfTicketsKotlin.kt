package zendeskigorkotlinkoin.ie.screens.listoftickets.view

/*** Created by igorfrankiv on 12/10/2018. ***/
import android.util.Log
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_testr.*
import org.koin.android.ext.android.inject
import zendeskigorkotlinkoin.ie.R
import zendeskigorkotlinkoin.ie.app.builder.Params
import zendeskigorkotlinkoin.ie.screens.listoftickets.mvp.IListViewContract
import zendeskigorkotlinkoin.ie.screens.listoftickets.view.viewadapt.ZendeskListViewAdapterKotlin
import zendeskigorkotlinkoin.ie.model.Tickets
import zendeskigorkotlinkoin.ie.model.TicketsResults
/*** Created by igorfrankiv on 30/09/2018. ***/

class ListOfTicketsKotlin: AppCompatActivity(), IListViewContract.IListView {

    // Presenter
    override val presenter: IListViewContract.Presenter by inject { mapOf(Params.SEARCH_VIEW to this) }

    private var mRecyclerViewVirtical: RecyclerView? = null
    private var mZendeskListViewAdapter: ZendeskListViewAdapterKotlin? = null
    private val errorMessage:String = "Error Loading Zendesk Tickets"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView( R.layout.activity_testr)
//  0
        presenter.getAllTheTicketsCallable()
//  1
//      presenter.getAllTheTicketsObservable()
//  2
//      presenter.getAllTheTicketsCompletableSingle()
    }

    override fun onDestroy() {
        presenter.stop()
        super.onDestroy()
    }

    override fun displayNormal() {
        searchProgress.visibility = View.GONE
        Log.e("displayNormal", "displayNormal")
        Log.e("33333333=", "setLoading(false)")
    }

    override fun displayProgress() {
        searchProgress.visibility = View.VISIBLE
        Log.e("setLoading(true)", "displayProgress")
    }

    override fun onTicketsFailed(error: Throwable) {
        Log.e("onWeatherFailed", "onWeatherFailed")
        Snackbar.make(this.currentFocus, "Got error : $error", Snackbar.LENGTH_LONG).show()
        alertDialog( errorMessage )
    }

    override fun displayTickets(results: List<Tickets>) {
        Log.e("displayTickets ",  results.size.toString())

            mZendeskListViewAdapter = ZendeskListViewAdapterKotlin(TicketsResults(results), this)
            mRecyclerViewVirtical = findViewById<View>(R.id.recycler_view) as RecyclerView
            val layoutManager = LinearLayoutManager(getApplicationContext());
            mRecyclerViewVirtical!!.setLayoutManager(layoutManager);
            mRecyclerViewVirtical!!.setAdapter(mZendeskListViewAdapter);
    }

    fun alertDialog(message: String){
        AlertDialog.Builder(this)
                .setTitle(message)
                .setPositiveButton(android.R.string.ok) { dialogInterface, i -> dialogInterface.dismiss() }
                .show()
    }
}