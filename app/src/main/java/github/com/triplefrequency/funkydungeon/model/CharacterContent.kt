package github.com.triplefrequency.funkydungeon.model

import github.com.triplefrequency.funkydungeon.repository.CharacterRepository
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import java.util.concurrent.TimeUnit

class CharacterContent {
    private var cacheTime: Long = 0
    private var cachedCharacterMap: Map<String, Character> = mapOf()
    private var cachedCharacterList: List<Character> = listOf()

    val characters: List<Character>
        get() {
            verifyCache()
            return cachedCharacterList
        }


    val characterMap: Map<String, Character>
        get() {
            verifyCache()
            return cachedCharacterMap
        }

    private fun verifyCache() = runBlocking {
        when (System.currentTimeMillis() - cacheTime) {
            // Do nothing if the cache is recent enough
            in 0..REFRESH_CACHE_AGE -> Unit
            // If the cache age is half that of the max, then asynchronously update the cache
            in REFRESH_CACHE_AGE..MAX_CACHE_AGE -> {
                updateCache()
                Unit
            }
            // If the cache age is more than the max, then update the cache and block
            else -> {
                updateCache().await()
            }
        }
    }

    private fun updateCache(): Deferred<Unit> = GlobalScope.async {
        synchronized(this) {
            cacheTime = System.currentTimeMillis()
            cachedCharacterMap = CharacterRepository.characters
            cachedCharacterList = cachedCharacterMap.map { it.value }
        }
    }

    companion object {
        val MAX_CACHE_AGE = TimeUnit.MINUTES.toMillis(3)
        val REFRESH_CACHE_AGE = MAX_CACHE_AGE / 2
    }
}