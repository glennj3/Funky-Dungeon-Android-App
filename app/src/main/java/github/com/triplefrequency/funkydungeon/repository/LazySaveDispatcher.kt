package github.com.triplefrequency.funkydungeon.repository

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class LazySaveDispatcher {
    fun dispatch(): Deferred<Unit> = GlobalScope.async {

    }
}