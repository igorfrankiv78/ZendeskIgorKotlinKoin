package zendeskigorkotlinkoin.ie.util.str

/*** Created by igorfrankiv on 17/10/2018.*/
class UtilReduceStrKotlin {

    companion object {

        fun reduceStr(str: String, numBiggerThan: Int, sizeOfStr: Int): String {
            val strToBeReturned: String

            if (str.length > numBiggerThan)
                strToBeReturned = str.substring(0, sizeOfStr) + "..."
            else
                strToBeReturned = str

            return strToBeReturned
        }

    }
}