package com.exam.home.wanandroid.customview

import java.util.concurrent.ScheduledThreadPoolExecutor
import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.ReentrantLock

/**
 * Created by Yangxy on 2020-01-14
 * description -- 可以暂停的周期性线程池
 */
class PausableScheduledExecutor(coreSize: Int) : ScheduledThreadPoolExecutor(coreSize) {

    private var isPaused: Boolean = false
    private val pauseLock: ReentrantLock = ReentrantLock()
    private val unpaused: Condition = pauseLock.newCondition()


    override fun beforeExecute(t: Thread, r: Runnable) {
        super.beforeExecute(t, r)
        pauseLock.lock()
        try {
            while (isPaused) unpaused.await()
        } catch (ex: InterruptedException) {
            t.interrupt()
        } finally {
            pauseLock.unlock()
        }
    }

    fun pause() {
        pauseLock.lock()
        try {
            isPaused = true
        } finally {
            pauseLock.unlock()
        }
    }

    fun resume() {
        pauseLock.lock()
        try {
            isPaused = false
            unpaused.signalAll()
        } finally {
            pauseLock.unlock()
        }
    }


}