package github.com.triplefrequency.funkydungeon.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import github.com.triplefrequency.funkydungeon.model.Character

object CharacterRepository {
    fun characters(user: FirebaseUser? = FirebaseAuth.getInstance().currentUser): List<Character> {
        return listOf(Character())
    }

    val characters: List<Character>
        get() = characters()

    fun save(character: Character) {

    }
}