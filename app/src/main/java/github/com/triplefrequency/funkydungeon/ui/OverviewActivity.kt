package github.com.triplefrequency.funkydungeon.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import github.com.triplefrequency.funkydungeon.R
import github.com.triplefrequency.funkydungeon.core.Constants
import github.com.triplefrequency.funkydungeon.model.Character
import github.com.triplefrequency.funkydungeon.repository.CharacterRepository

import kotlinx.android.synthetic.main.activity_overview.*
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KProperty
import kotlin.reflect.KType

class OverviewActivity : AppCompatActivity() {

    lateinit var character: Character

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_overview)

        val outIntent = intent
        if (outIntent != null) {
            val id = outIntent.getStringExtra(Constants.ARG_CHARACTER_ID)
            if (id != null) {
                character = CharacterRepository.characters[id] ?: Character()
            }
        }
        val btnOverview = findViewById<Button>(R.id.btn_overview)
        val btnAttributes = findViewById<Button>(R.id.btn_attributes)
        val btnSkills = findViewById<Button>(R.id.btn_skills)
        val btnAttacks = findViewById<Button>(R.id.btn_attacks)



        name_edit.setText(character.name)
        def_edit.setText(character.defensePoints)
        hp_edit.setText(character.hitPoints)
        init_edit.setText(character.initiative)
        prof_edit.setText(character.proficiencies.size)
        speed_edit.setText(character.speed)
        race_edit.setText(character.race)
        aware_edit.setText(character.awareness)

        name_edit.onTextChanged { character.name = it?.toString() ?: "" }
        def_edit.onTextChanged { character.defensePoints = it?.toString()?.toInt() ?: 10 }
        hp_edit.onTextChanged { character.hitPoints = it?.toString()?.toInt() ?: 10 }
        init_edit.onTextChanged { character.initiative = it?.toString()?.toInt() ?: 10 }
        prof_edit.onTextChanged { character.proficiencies.size }
        speed_edit.onTextChanged { character.speed = it?.toString()?.toInt() ?: 10 }
        race_edit.onTextChanged { character.race = it?.toString() ?: "" }
        aware_edit.onTextChanged { character.awareness = it?.toString()?.toInt() ?: 10 }


        btnOverview.isEnabled = false
        btnAttributes.setOnClickListener {
            //intent.putExtra()
            //startActivity(Intent(this, MainActivity::class.java))
        }
        btnSkills.setOnClickListener {}
        btnAttacks.setOnClickListener {}
        //TODO Need to have a way to dynamically change values from storage

    }


}

private fun EditText.onTextChanged(lam: (CharSequence?) -> Unit) = object: TextWatcher {
    override fun afterTextChanged(p0: Editable?) {}

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { lam(p0) }

}
