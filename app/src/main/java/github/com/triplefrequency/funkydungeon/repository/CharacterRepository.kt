package github.com.triplefrequency.funkydungeon.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import github.com.triplefrequency.funkydungeon.model.Character
import kotlinx.coroutines.*

object CharacterRepository {
    /**
     * Return of [Map] of Strings/Characters. The string is simply a string ID, generated using the [java.util.UUID.randomUUID] function.
     */
    fun characters(user: FirebaseUser? = FirebaseAuth.getInstance().currentUser): Map<String, Character> {
        return mapOf("test" to Character().apply {
            name = "Test Name"
            race = "Test Race"
            cClass = "Test Class"
        })
    }

    val characters: Map<String, Character>
        get() = characters()

    private val db
        get() = FirebaseFirestore.getInstance()

    private val saver: LazySaveDispatcher = LazySaveDispatcher()
    private var saveJob: Deferred<Unit>? = null

    /**
     * A non-blocking save call to the Cloud FireStore
     */
    @Synchronized
    fun save(character: Character): Deferred<Unit> =
        if (saveJob != null) {
            // We can null-assert here because we are in a synchronized function, which guarantees saveJob doesn't change
            saveJob!!
        } else {
            val job = saver.dispatch()
            saveJob = job
            job
        }
}