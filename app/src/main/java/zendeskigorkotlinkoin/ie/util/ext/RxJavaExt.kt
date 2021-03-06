package zendeskigorkotlinkoin.ie.util.ext

/*** Created by igorfrankiv on 12/10/2018. ***/
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import zendeskigorkotlinkoin.ie.util.rx.SchedulerProvider

/*** Use SchedulerProvider configuration for Completable*/
fun Completable.with(schedulerProvider: SchedulerProvider): Completable =
        this.observeOn(schedulerProvider.ui()).subscribeOn(schedulerProvider.io())

/*** Use SchedulerProvider configuration for Single*/
fun <T> Single<T>.with(schedulerProvider: SchedulerProvider): Single<T> =
        this.observeOn(schedulerProvider.ui()).subscribeOn(schedulerProvider.io())

/*** Use SchedulerProvider configuration for Observable*/
fun <T> Observable<T>.with(schedulerProvider: SchedulerProvider): Observable<T> =
        this.observeOn  (schedulerProvider.ui()).
             subscribeOn(schedulerProvider.io())