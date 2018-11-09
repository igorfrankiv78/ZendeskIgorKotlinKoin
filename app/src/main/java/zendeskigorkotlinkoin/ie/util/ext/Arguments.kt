package zendeskigorkotlinkoin.ie.util.ext

import android.support.v7.app.AppCompatActivity

/*** Created by igorfrankiv on 23/10/2018.*/

fun <T : Any> AppCompatActivity.argument(key: String) = lazy { intent.extras[key] as T }