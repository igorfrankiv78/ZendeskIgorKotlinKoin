package zendeskigorkotlinkoin.ie.screens.home.view

/*** Created by igorfrankiv on 23/10/2018.*/
import android.os.Bundle
import android.util.Log
import android.view.View
import zendeskigorkotlinkoin.ie.R
import org.jetbrains.anko.startActivity
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import org.koin.android.ext.android.inject
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_zendesk_home.*
import zendeskigorkotlinkoin.ie.app.builder.Params
import zendeskigorkotlinkoin.ie.screens.home.mvp.IHomeViewContract
import zendeskigorkotlinkoin.ie.screens.Arguments.ARG_TICKET
import zendeskigorkotlinkoin.ie.screens.listoftickets.view.ListOfTicketsActivity

class ZendeskHomeActivity : AppCompatActivity(), IHomeViewContract.IHomeView {

    // Presenter
    override val presenter: IHomeViewContract.Presenter by inject { mapOf(Params.HOME_VIEW to this) }
    private val errorMessage:String = "(Network) Error Loading Zendesk Tickets"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView( R.layout.activity_zendesk_home )

        searchButton.setOnClickListener {
//  0
//      presenter.getAllTheTicketsCompletable()
//  1
//      presenter.getAllTheTicketsObservable()
//  2  Callable does not work on local resources. I did not implement the method "getTicketsCall" in LocalDataSource class
//        presenter.getAllTheTicketsCallable()
//  3
      presenter.getAllTheTicketsMergeObservable()
        }
    }

    override fun onDestroy() {
        presenter.stop()
        super.onDestroy()
    }

    override fun displayNormal() {
        searchProgress.visibility = View.GONE
        searchButton.visibility = View.VISIBLE
        Log.e("displayNormal", "displayNormal")
    }

    override fun displayProgress() {
        searchProgress.visibility = View.VISIBLE
        searchButton.visibility = View.GONE
        Log.e("displayProgress", "displayProgress")
    }

    override fun onTicketsFailed(error: Throwable) {
        Snackbar.make(this.currentFocus, "Got error : $error", Snackbar.LENGTH_LONG).show()
        alertDialog( errorMessage )
    }

    override fun onTicketsSuccess(){
        startActivity<ListOfTicketsActivity>(ARG_TICKET to getSearchText() )
    }

    fun getSearchText() = searchEditText.text.trim().toString()

    fun alertDialog(message: String){
        AlertDialog.Builder(this)
                .setTitle(message)
                .setPositiveButton(android.R.string.ok) { dialogInterface, i -> dialogInterface.dismiss() }
                .show()
    }
}