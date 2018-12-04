package github.com.triplefrequency.funkydungeon.repository

import java.util.concurrent.Executors

class LazySaveDispatcher(
    private val delayTime: Long = 1500
) {
    private val pool = Executors.newFixedThreadPool(1)

    fun <T> dispatch(item: T, saveFunc: (T) -> Unit, callback: (T) -> Unit) = pool.submit {
        Thread.sleep(delayTime)
        saveFunc(item)
        callback(item)
    }
}