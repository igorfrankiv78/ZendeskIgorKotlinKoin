package zendeskigorkotlinkoin.ie.screens

/**
 * Created by igorfrankiv on 12/10/2018.
 */
import android.support.annotation.CallSuper
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import zendeskigorkotlinkoin.ie.util.mvp.BasePresenter
import zendeskigorkotlinkoin.ie.util.mvp.BaseView
/**
 * Base Preenter feature - for Rx Jobs
 *
 * launch() - launch a Rx request
 * clear all request on stop
 */
abstract class AbstractPresenter<V : BaseView<P>, out P : BasePresenter<V>> : BasePresenter<V> {

    override lateinit var view: V

    val disposables = CompositeDisposable()

    fun launch(job: () -> Disposable) {
        disposables.add(job())
    }

    @CallSuper
    override fun stop() {
        disposables.clear()
    }
}