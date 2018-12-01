package github.com.triplefrequency.funkydungeon.model

class CharacterContent {
    var characters: List<Character> = listOf(Character())
        private set

    var characterMap: Map<String?, Character> =
        mutableMapOf<String?, Character>().apply { characters.forEach { this[it.id] = it } }
        private set
}