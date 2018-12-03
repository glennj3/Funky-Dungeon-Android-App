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
        }, "otherTest" to Character().apply {
            name = "Other Test Name"
            race = "Test Race"
            cClass = "Test Class"
        })
    }

    val characters: Map<String, Character>
        get() = characters()

    /**
     * The actual character saver, with a blocking API that is called from a non-blocking context
     */
    private val characterSaver = CharacterSaver()

    /**
     * The dispatcher used for creating save jobs
     */
    private val saveDispatcher: LazySaveDispatcher = LazySaveDispatcher()
    /**
     * All active save jobs, indexed by [Character.id]
     */
    private var saveJobs: MutableMap<String, Deferred<Unit>> = mutableMapOf()

    /**
     * Return an existing lazy save [Deferred] task or create a new one.
     */
    fun save(character: Character): Deferred<Unit> = saveJobs[character.id] ?: dispatchSave(character)

    /**
     * Return an asynchronous save job, storing it in [saveJobs]
     */
    private fun dispatchSave(character: Character): Deferred<Unit> =
        synchronized(saveJobs) {
            val job = saveDispatcher.dispatch(character, characterSaver::save, this::processPostSave)
            saveJobs[character.id] = job
            job
        }

    /**
     * The callback function used by the [LazySaveDispatcher] to update the [saveJobs]
     */
    private fun processPostSave(character: Character) {
        synchronized(saveJobs) {
            saveJobs.remove(character.id)
        }
    }
}