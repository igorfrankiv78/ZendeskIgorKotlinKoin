package zendeskigorkotlinkoin.ie.util.ext

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/*** Created by igorfrankiv on 17/10/2018.*/
class TObservable<T> {

    protected val onAdd: PublishSubject<T>
    val observable: Observable<T>
        get() = onAdd

    init {
        this.onAdd = PublishSubject.create()
    }

    fun add(value: T) {
        onAdd.onNext(value)
    }
}