package github.com.triplefrequency.funkydungeon.model

import github.com.triplefrequency.funkydungeon.repository.CharacterRepository
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit

object CharacterContent {
    private var cacheTime: Long = 0
    private var cachedCharacterMap: MutableMap<String, Character> = mutableMapOf()
    private var cachedCharacterList: MutableList<Character> = mutableListOf()

    val characters: List<Character>
        get() {
            verifyCache()
            return cachedCharacterList
        }


    val characterMap: MutableMap<String, Character>
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

    private fun updateCache(): Deferred<Unit> = runBlocking {
        async(Dispatchers.IO) {
            synchronized(this) {
                cacheTime = System.currentTimeMillis()
                cachedCharacterMap = CharacterRepository.characters.toMutableMap()
                cachedCharacterList = cachedCharacterMap.map { it.value }.toMutableList()
            }
        }
    }

    private val MAX_CACHE_AGE = TimeUnit.MINUTES.toMillis(3)
    private val REFRESH_CACHE_AGE = MAX_CACHE_AGE / 2
}