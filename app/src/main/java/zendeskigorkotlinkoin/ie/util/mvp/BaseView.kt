package zendeskigorkotlinkoin.ie.util.mvp

/**
 * Created by igorfrankiv on 12/10/2018.
 */
interface BaseView<out T : BasePresenter<*>> {

    val presenter: T

}