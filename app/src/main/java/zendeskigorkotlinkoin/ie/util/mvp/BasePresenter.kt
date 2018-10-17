package zendeskigorkotlinkoin.ie.util.mvp

/*** Created by igorfrankiv on 12/10/2018.*/
interface BasePresenter<T> {

    fun stop()

    var view: T
}