package zendeskigorkotlinkodein.ie.kointest.helpers.util

/*** Created by igorfrankiv on 18/10/2018.*/
import io.reactivex.schedulers.Schedulers
import zendeskigorkotlinkoin.ie.util.rx.SchedulerProvider

class TestSchedulerProvider : SchedulerProvider {
    override fun io() = Schedulers.trampoline()

    override fun ui() = Schedulers.trampoline()

    override fun computation() = Schedulers.trampoline()
}
