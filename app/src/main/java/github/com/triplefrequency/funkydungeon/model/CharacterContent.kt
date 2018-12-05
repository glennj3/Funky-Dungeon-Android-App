package github.com.triplefrequency.funkydungeon.model

import android.databinding.ObservableArrayMap
import android.databinding.ObservableMap
import github.com.triplefrequency.funkydungeon.repository.CharacterRepository
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import java.util.concurrent.TimeUnit

object CharacterContent {
    private var cacheTime: Long = 0
    private var cachedCharacterMap: ObservableMap<String, Character> = ObservableArrayMap<String, Character>()
    private var cachedCharacterList: MutableList<Character> = mutableListOf()

    var characters: MutableList<Character>
        get() {
            verifyCache()
            return cachedCharacterList
        }
        set(value) {
            cachedCharacterList = value
        }


    val characterMap: ObservableMap<String, Character>
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

    internal fun updateCache(): Deferred<Unit> = runBlocking {
        async(Dispatchers.IO) {
            synchronized(this) {
                cacheTime = System.currentTimeMillis()
                cachedCharacterMap = CharacterRepository.characters.apply {
                    addOnMapChangedCallback(object: ObservableMap.OnMapChangedCallback<ObservableMap<String, Character>, String, Character>() {
                        override fun onMapChanged(sender: ObservableMap<String, Character>, key: String) {
                            synchronized(CharacterContent) {
                                cachedCharacterList = cachedCharacterMap.map { it.value }.toMutableList()
                                if (sender[key] == null)
                                    CharacterRepository.delete(key)
                            }
                        }

                    })
                }
                cachedCharacterList = cachedCharacterMap.map { it.value }.toMutableList()
            }
        }
    }

    private val MAX_CACHE_AGE = TimeUnit.MINUTES.toMillis(3)
    private val REFRESH_CACHE_AGE = MAX_CACHE_AGE / 2
}