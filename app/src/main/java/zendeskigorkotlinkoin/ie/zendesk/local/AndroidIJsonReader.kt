package zendeskigorkotlinkoin.ie.zendesk.local

/*** Created by igorfrankiv on 22/10/2018.*/
import android.app.Application
import java.io.BufferedReader
import java.io.InputStreamReader

/*** Read Json File from assets/json */
class AndroidIJsonReader(val application: Application) : BaseReaderI() {

    override fun getAllFiles(): List<String> = application.assets.list("json").toList()

    override fun readJsonFile(jsonFile: String): String {
        val buf = StringBuilder()
        val json = application.assets.open("json/" + jsonFile)
        BufferedReader(InputStreamReader(json, "UTF-8"))
                .use {
                    val list = it.lineSequence().toList()
                    buf.append(list.joinToString("\n"))
                }
        return buf.toString()
    }
}