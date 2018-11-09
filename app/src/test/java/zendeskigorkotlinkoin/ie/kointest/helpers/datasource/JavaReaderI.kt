package zendeskigorkotlinkodein.ie.kointest.helpers.datasource

/*** Created by igorfrankiv on 18/10/2018.*/
import zendeskigorkotlinkoin.ie.zendesk.local.BaseReaderI
import java.io.File

class JavaReaderI : BaseReaderI()
{
    fun basePath(): String? {
        val classLoader: ClassLoader = JavaReaderI::class.java.classLoader
        val path: String? = classLoader.getResource("json/")?.path
        return path
    }

    override fun getAllFiles(): List<String> {
        return basePath()?.let {
            val list = File(it).list()
            list.toList()
        }!!
    }

    override fun readJsonFile(jsonFile: String): String = File("${basePath()}/$jsonFile").readLines().joinToString(separator = "\n")
}
