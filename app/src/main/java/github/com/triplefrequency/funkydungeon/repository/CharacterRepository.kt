package github.com.triplefrequency.funkydungeon.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import github.com.triplefrequency.funkydungeon.model.Character
import github.com.triplefrequency.funkydungeon.model.CharacterContent
import kotlinx.coroutines.Deferred

object CharacterRepository {
    /**
     * Get all characters for the locally signed-in [FirebaseUser]
     */
    val characters: Map<String, Character>
        get() = characters()

    private val characterLoader = CharacterLoader()

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
     * Return of [Map] of Strings/Characters. The string is simply a string ID, generated using the [java.util.UUID.randomUUID] function.
     */
    fun characters(user: FirebaseUser? = FirebaseAuth.getInstance().currentUser): Map<String, Character> {
        return mapOf("test" to Character(id = "test").apply {
            name = "Test Name"
            race = "Test Race"
            cClass = "Test Class"
        }, "otherTest" to Character(id = "otherTest").apply {
            name = "Other Test Name"
            race = "Test Race"
            cClass = "Test Class"
        })
        // characterLoader.load(user)
    }

    /**
     * Return an existing lazy save [Deferred] task or create a new one.
     */
    fun save(character: Character): Deferred<Unit> =
        synchronized(saveJobs) { saveJobs[character.id] ?: dispatchSave(character) }

    /**
     * Return an asynchronous save job, storing it in [saveJobs]
     */
    private fun dispatchSave(character: Character): Deferred<Unit> {
        // Update the local cache if necessary
        if (CharacterContent.characterMap[character.id] == null)
            CharacterContent.characterMap[character.id] = character
        val job = saveDispatcher.dispatch(character, characterSaver::save, this::processPostSave)
        saveJobs[character.id] = job
        return job
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