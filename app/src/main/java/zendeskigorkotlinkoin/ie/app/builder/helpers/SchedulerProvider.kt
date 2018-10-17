package zendeskigorkotlinkoin.ie.app.builder.helpers

import io.reactivex.Scheduler
/**
 * Created by igorfrankiv on 12/10/2018.
 */
interface SchedulerProvider {
    fun io(): Scheduler
    fun ui(): Scheduler
    fun computation(): Scheduler
}