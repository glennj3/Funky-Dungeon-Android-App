package github.com.triplefrequency.funkydungeon.repository

import android.databinding.ObservableArrayMap
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import github.com.triplefrequency.funkydungeon.model.Character
import github.com.triplefrequency.funkydungeon.model.DatabaseCharacter

val FirebaseFirestore.characterCollection get() = this.collection("characters")

fun CollectionReference.getCharacters(user: FirebaseUser? = FirebaseAuth.getInstance().currentUser) =
    ObservableArrayMap<String, Character>().also { map ->
        map.putAll(mutableMapOf<String, Character>().apply {
            whereEqualTo(DatabaseCharacter::authorUid.name, user?.uid).get(if (user == null) Source.CACHE else Source.DEFAULT).addOnSuccessListener {
                for (document in it.documents) {
                    val character = Character.fromDatabase(document.toObject(DatabaseCharacter::class.java))
                    if (character != null) this@apply[character.id] = character
                }
            }
        })
    }

fun CollectionReference.saveCharacter(character: Character) = document(character.id).set(character)