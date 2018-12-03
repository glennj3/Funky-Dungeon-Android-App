package github.com.triplefrequency.funkydungeon.model

import github.com.triplefrequency.funkydungeon.repository.CharacterRepository
import java.util.concurrent.TimeUnit

class CharacterContent {
    private var cacheTime: Long = 0
    private var cachedCharacterMap: Map<String, Character> = mapOf()
    private var cachedCharacterList: List<Character> = listOf()

    val characters: List<Character>
        get() {
            updateCache()
            return cachedCharacterList
        }


    val characterMap: Map<String, Character>
        get() {
            updateCache()
            return cachedCharacterMap
        }

    @Synchronized
    private fun updateCache() {
        if (System.currentTimeMillis() - cacheTime > MAX_CACHE_AGE) {
            cacheTime = System.currentTimeMillis()
            cachedCharacterMap = CharacterRepository.characters
            cachedCharacterList = cachedCharacterMap.map { it.value }
        }
    }

    companion object {
        val MAX_CACHE_AGE = TimeUnit.MINUTES.toMillis(3)
    }
}