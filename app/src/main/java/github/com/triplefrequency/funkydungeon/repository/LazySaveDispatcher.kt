package github.com.triplefrequency.funkydungeon.repository

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay

class LazySaveDispatcher(
    private val delayTime: Long = 1500
) {
    fun <T> dispatch(item: T, saveFunc: (T) -> Unit, callback: (T) -> Unit): Deferred<Unit> = GlobalScope.async {
        delay(delayTime)
        saveFunc(item)
        callback(item)
    }
}