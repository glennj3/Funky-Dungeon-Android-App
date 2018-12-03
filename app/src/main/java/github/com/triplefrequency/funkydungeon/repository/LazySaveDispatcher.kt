package github.com.triplefrequency.funkydungeon.repository

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class LazySaveDispatcher {
    fun dispatch(lock: Any): Deferred<Unit> = GlobalScope.async {
        synchronized(lock) {

        }
    }
}