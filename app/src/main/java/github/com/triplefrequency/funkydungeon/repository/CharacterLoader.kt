package github.com.triplefrequency.funkydungeon.repository

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import github.com.triplefrequency.funkydungeon.model.Character

class CharacterLoader(
    /**
     * The database from which character data is being loaded.
     */
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    /**
     * Load all [Character]s for the given [FirebaseUser]
     */
    fun load(user: FirebaseUser?) = db.characterCollection.getCharacters(user)
}