package github.com.triplefrequency.funkydungeon.repository

import com.google.firebase.firestore.FirebaseFirestore
import github.com.triplefrequency.funkydungeon.model.Character

class CharacterSaver {
    /**
     * The database to which data is being saved.
     */
    private val db = FirebaseFirestore.getInstance()

    /**
     * Save the given [Character] to the default Firestore [db]
     */
    fun save(character: Character) {
    }
}