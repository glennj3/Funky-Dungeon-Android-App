package github.com.triplefrequency.funkydungeon.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import github.com.triplefrequency.funkydungeon.core.Constants
import github.com.triplefrequency.funkydungeon.model.Character
import github.com.triplefrequency.funkydungeon.model.CharacterContent
import github.com.triplefrequency.funkydungeon.repository.CharacterRepository
import github.com.triplefrequency.funkydungeon.ui.attacks.AttackActivity
import github.com.triplefrequency.funkydungeon.ui.attributes.Attributes
import github.com.triplefrequency.funkydungeon.ui.proficiency.ProficiencyActivity

abstract class CharacterActivity : AppCompatActivity() {
    protected val character: Character by lazy {
        val outIntent = intent
        if (outIntent != null) {
            val id = outIntent.getStringExtra(Constants.ARG_CHARACTER_ID)
            if (id != null) {
                return@lazy CharacterContent.characterMap[id] ?: newCharacter()
            }
        }
        // Initialize a default character if the character still isn't loaded
        return@lazy newCharacter()
    }

    private fun newCharacter() = Character().apply {
        CharacterRepository.save(this)
    }

    fun onClickedOverview(view: View) = onClickedGeneric(view, OverviewActivity::class.java)
    fun onClickedAttributes(view: View) = onClickedGeneric(view, Attributes::class.java)
    fun onClickedProficiencies(view: View) = onClickedGeneric(view, ProficiencyActivity::class.java)
    fun onClickedAttacks(view: View) = onClickedGeneric(view, AttackActivity::class.java)

    private fun onClickedGeneric(view: View, clazz: Class<*>? = null) {
        if ((view !is Button || view.isEnabled) && clazz != null) {
            startActivity(Intent(this, clazz).apply {
                putExtra(Constants.ARG_CHARACTER_ID, character.id)
            })
        }
    }
}