package github.com.triplefrequency.funkydungeon.repository

import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class LazySaveDispatcher(
    private val delayTime: Long = 1500
) {
    private val scope = runBlocking { this }

    fun <T> dispatch(item: T, saveFunc: (T) -> Unit, callback: (T) -> Unit) = scope.async {
        Thread.sleep(delayTime)
        saveFunc(item)
        callback(item)
    }
}