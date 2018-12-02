package github.com.triplefrequency.funkydungeon.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import github.com.triplefrequency.funkydungeon.model.Character

object CharacterRepository {
    /**
     * Return of [Map] of Strings/Characters. The string is simply a string ID, generated using the [java.util.UUID.randomUUID] function.
     */
    fun characters(user: FirebaseUser? = FirebaseAuth.getInstance().currentUser): Map<String, Character> {
        return mapOf("test" to Character())
    }

    val characters: Map<String, Character>
        get() = characters()

    fun save(character: Character) {

    }
}