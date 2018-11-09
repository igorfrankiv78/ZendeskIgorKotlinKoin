package zendeskigorkotlinkoin.ie.zendesk

import java.io.*
import io.reactivex.*
import retrofit2.Call
import android.util.Log
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList
import android.content.Context
import zendeskigorkotlinkoin.ie.constants.UserParam
import zendeskigorkotlinkoin.ie.model.Tickets
import zendeskigorkotlinkoin.ie.model.TicketsResults
import zendeskigorkotlinkoin.ie.util.ext.TObservable
import zendeskigorkotlinkoin.ie.zendesk.localjava.JSONUtil
import java.util.concurrent.locks.ReentrantReadWriteLock

/*** Created by igorfrankiv on 12/10/2018.*/
class ZendeskModel(private val datasource: ZendeskSVDataSource,
                   val tObservable: TObservable<TicketsResults>,
                   val mContext: Context
                   ): IZendeskModel {

    companion object { const val ERROR = "No Tickets data" }

   val mListOfAllTheTickets = arrayListOf<Tickets>()
//-----NEW-----NEW----NEW----------------------------------------------------------------------------------------------
    private val rwlock = ReentrantReadWriteLock()
//    private val mListOfUserLocations: List<Tickets> = arrayListOf<Tickets>()
    val JSON_LOCATION_FILE = "jsontickets.json"
    val DEFAULT_FILE_ENCODING = "UTF-8"
    val READD_ERROR_MESSAGE = "Could not read the file!"

    private fun readJSONFileFromLocalDIR(context: Context): String {
        rwlock.readLock().lock()
        Log.e("-------", "---------------------")
        Log.e("readJSONFileFromLolDIR", "displayNormal")
        val buf = StringBuilder()
//        val json = context.assets.open("json/" + jsonFile)
        val file = File(context.filesDir, JSON_LOCATION_FILE)
        BufferedReader(InputStreamReader(FileInputStream(file), "UTF-8"))
                .use {
                    val list = it.lineSequence().toList()
                    buf.append( list.joinToString("\n") )
                    Log.e("append", "append") }
        Log.e("-------", "---------------------")
        rwlock.readLock().unlock()
        return buf.toString()
    }

    private fun writingJSONFileToLocalDIR(context: Context, jsonObj: String): Boolean {
        Log.e("-------", "---------------------")
        rwlock.writeLock().lock()

        try {
            val output = context.openFileOutput(JSON_LOCATION_FILE, Context.MODE_PRIVATE)//
            val writer = OutputStreamWriter(output)
            writer.write(jsonObj)
            Log.e("writingJSONFile ", "jsonObj")
            writer.flush()
            writer.close()
        } catch (e: Exception) {
            return false
        } finally {

            rwlock.writeLock().unlock()

        }
        Log.e("-------", "---------------------")
        return true
    }

    override fun readIOdataRX(): Observable<TicketsResults> {

        var listOfTickets: List<Tickets>? = null
        val defaultTickets = ArrayList<Tickets>()
        var ticketsResults: TicketsResults? = null

        if (File(mContext.filesDir, JSON_LOCATION_FILE).exists()) {

            val jsonStr = readJSONFileFromLocalDIR(mContext)
            val userLocations = JSONUtil.readJsonString(jsonStr)
            //            String isRecorded  = userLocations.getIsRecorded();
            //            if (isRecorded.contains( ServicePresenter.STATE_OF_RECORDDING_YES )) {
            listOfTickets = userLocations.getResults()
            mListOfAllTheTickets.clear();  mListOfAllTheTickets.addAll( listOfTickets )
            ticketsResults = TicketsResults( listOfTickets )

            Log.e("readIOdataRX ", listOfTickets.size.toString())
            //            } else
            //                listOfTickets = new ArrayList<>();
        } else
        {
            defaultTickets.add(Tickets( 0, "subject", "description", "status" ) )

            listOfTickets = defaultTickets

        }
        return Observable.just( TicketsResults( listOfTickets ) )
    }

    override fun finalWriteIODataRX( mListOfUserLocations: List<Tickets> ): Boolean? {
        Log.e("finalWriteIODataRX ", mListOfUserLocations.size.toString())
        mListOfAllTheTickets.clear();  mListOfAllTheTickets.addAll(mListOfUserLocations)
        var isWrittenIO: Boolean? = false
        if (mListOfUserLocations != null && mListOfUserLocations.size > 0) {
            val jsonListOfUserLocationsObj = JSONUtil.createJsonString(mListOfUserLocations )
            isWrittenIO = writingJSONFileToLocalDIR(mContext, jsonListOfUserLocationsObj)
        }

        return isWrittenIO
    }
//-----NEW-----NEW----NEW->->->-------------------------------------------------------------------------------
//  Observable
//------------------------------------------------------------------------------------------------------------
    override fun getAllTheTicketsObservable(): Observable<TicketsResults> {
         return datasource.getTicketsObservable(UserParam.URL)
    }

    override fun cacheTicketsResults(tickets: List<Tickets>) {
        mListOfAllTheTickets.clear();  mListOfAllTheTickets.addAll( tickets )
    }

//  Completable
//------------------------------------------------------------------------------------------------------------
    override fun getAllTheTicketsCompletable(): Completable {
        return datasource.getTicketsSingle(UserParam.URL)
                .map { it.results ?: throw IllegalStateException(ERROR) }
                .doOnSuccess { mListOfAllTheTickets.clear();  mListOfAllTheTickets.addAll(it) }
                .toCompletable()
    }

//  Single
//------------------------------------------------------------------------------------------------------------
    override fun getAllTheTicketsSingle(): Single<List<Tickets>> = Single.just( mListOfAllTheTickets )

    override fun getSelectedTicketDetailSingle(id: String) = Single.just(mListOfAllTheTickets.first { it.id.toString() == id })

//  Callable
//------------------------------------------------------------------------------------------------------------
     override fun getAllTheTicketsCallable( ): Observable<TicketsResults> {
          datasource.getTicketsCall(UserParam.URL)?.enqueue( object : Callback<TicketsResults> {

                override fun onResponse(call: Call<TicketsResults>?, response: Response<TicketsResults>?) {
                    if (response != null && response.isSuccessful) {
                        tObservable.add(response.body())
                        Log.e("getAllTheTickCallable ", response.body().results.size.toString() )
                    }else
                        tObservable.observable.doOnError({ throw IllegalStateException(ERROR) })   // response.code()
                }

                override fun onFailure(call: Call<TicketsResults>?, t: Throwable?) {
                    tObservable.observable.doOnError({ throwable -> throwable.cause })
                }
           }  )

        return tObservable.observable
    }
//------------------------------------------------------------------------------------------------------------
}


