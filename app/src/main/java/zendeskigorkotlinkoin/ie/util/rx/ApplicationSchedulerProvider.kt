package zendeskigorkotlinkoin.ie.util.rx

/*** Created by igorfrankiv on 12/10/2018. */
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/*** Application providers*/

class ApplicationSchedulerProvider : SchedulerProvider {
    override fun io() = Schedulers.io()

    override fun ui() = AndroidSchedulers.mainThread()

    override fun computation() = Schedulers.computation()

}