package github.com.triplefrequency.funkydungeon.model

class CharacterContent {
    var characters: List<Character> = listOf(CharacterJson("meme", "Super Meme"))
        private set

    var characterMap: Map<String?, Character> =
        mutableMapOf<String?, Character>().apply { characters.forEach { this[it.id] = it } }
        private set
}

interface Character {
    val id: String

    val name: String
}

data class CharacterJson(
    override val id: String,
    override val name: String
) : Character {
    override fun toString() = name
}