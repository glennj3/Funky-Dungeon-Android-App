package github.com.triplefrequency.funkydungeon.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.view.View
import github.com.triplefrequency.funkydungeon.core.Constants
import github.com.triplefrequency.funkydungeon.model.Character
import kotlin.reflect.KClass

abstract class TabbableActivity: AppCompatActivity() {
    abstract val character: Character

    fun onClickedOverview()

    private fun <T: Any> onClickedGeneric(clazz: KClass<out T>) = { it: View ->
        startActivity(Intent(this, clazz.java).apply {
            putExtra(Constants.ARG_CHARACTER_ID, character.id)
        })
    }
}