package zendeskigorkotlinkoin.ie.zendesk.local

/*** Created by igorfrankiv on 22/10/2018. ***/
import com.google.gson.Gson
import zendeskigorkotlinkoin.ie.model.TicketsResults
/*** Common parts for Json reader*/

abstract class BaseReaderI : IJsonReader {

    private val gson = Gson()
    private val tickets_prefix = "tickets_" // full file name is tickets_200_ok_response.json
    private val json_file = ".json"

    override fun getTickets(name: String): TicketsResults = gson.fromJson<TicketsResults>(
            readJsonFile(tickets_prefix + name + json_file), TicketsResults::class.java )

    abstract fun getAllFiles(): List<String>

    abstract fun readJsonFile(jsonFile: String): String
}
