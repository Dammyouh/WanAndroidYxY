package com.exam.home.wanandroid.base

import io.reactivex.Observer
import io.reactivex.disposables.Disposable


/**
 * Created by Yangxy on 2020-01-07
 * description --
 */
open class RxBaseCallBack<T> : Observer<T> {
    override fun onError(e: Throwable) {
        onFailed(e)

    }

    override fun onComplete() {

    }

    override fun onSubscribe(d: Disposable) {
    }


    final override fun onNext(t: T) {
        onSuccess(t)

    }


    open fun onFailed(e: Throwable?) {

    }

    open fun onSuccess(t: T) {

    }

}