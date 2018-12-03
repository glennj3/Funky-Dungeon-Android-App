package github.com.triplefrequency.funkydungeon.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import github.com.triplefrequency.funkydungeon.model.Character

val FirebaseFirestore.characterCollection get() = this.collection("characters")

fun CollectionReference.getCharacters(user: FirebaseUser? = FirebaseAuth.getInstance().currentUser) =
    mutableMapOf<String, Character>().apply {
        if (user != null) {
            whereEqualTo(Character::authorUid.name, user.uid).get().addOnSuccessListener {
                for (document in it.documents) {
                    val character = document.toObject(Character::class.java)
                    if (character != null) this@apply[character.id] = character
                }
            }
        }
    }

fun CollectionReference.saveCharacter(character: Character) = document(character.id).set(character)