package github.com.triplefrequency.funkydungeon.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import github.com.triplefrequency.funkydungeon.R
import github.com.triplefrequency.funkydungeon.core.Constants
import github.com.triplefrequency.funkydungeon.model.Character
import github.com.triplefrequency.funkydungeon.repository.CharacterRepository
import github.com.triplefrequency.funkydungeon.ui.attributes.Attributes
import kotlinx.android.synthetic.main.activity_overview.*

class OverviewActivity : AppCompatActivity() {

    lateinit var character: Character
    lateinit var ovBar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_overview)

        ovBar = findViewById(R.id.overview_toolbar)
        setSupportActionBar(ovBar)

        val outIntent = intent
        if (outIntent != null) {
            val id = outIntent.getStringExtra(Constants.ARG_CHARACTER_ID)
            if (id != null) {
                character = CharacterRepository.characters[id] ?: newCharacter()
            }
        }
        // Initialize a default character if the character still isn't loaded
        if (!::character.isInitialized) {
            character = newCharacter()
        }
        val btnOverview = findViewById<Button>(R.id.btn_overview)
        val btnAttributes = findViewById<Button>(R.id.btn_attributes)
        val btnSkills = findViewById<Button>(R.id.btn_skills)
        val btnAttacks = findViewById<Button>(R.id.btn_attacks)

        name_edit.newOnTextChanged({ character.name = it ?: "" }, ::notBlank)
        def_edit.newOnTextChanged({ character.defensePoints = it ?: 10 }, ::uintCheck)
        hp_edit.newOnTextChanged({ character.hitPoints = it ?: 10 }, ::uintCheck)
        init_edit.newOnTextChanged({ character.initiative = it ?: 10 }, ::uintCheck)
        prof_edit.newOnTextChanged({ character.proficiencies.size }, ::notBlank)
        speed_edit.newOnTextChanged({ character.speed = it ?: 10 }, ::uintCheck)
        race_edit.newOnTextChanged({ character.race = it ?: "" }, ::notBlank)
        aware_edit.newOnTextChanged({ character.initiative = it ?: 10 }, ::uintCheck)

        lvl_edit.newOnTextChanged({ character.initiative = it ?: 10 }, ::uintCheck)
        class_edit.newOnTextChanged({ character.cClass = it ?: "" }, ::notBlank)
        hit_dice_edit.newOnTextChanged({ character.hitDice = it ?: "1d8" }, ::diceCheck)

        name_edit.setText(character.name, TextView.BufferType.NORMAL)
        def_edit.setText(character.defensePoints.toString(), TextView.BufferType.NORMAL)
        hp_edit.setText(character.hitPoints.toString(), TextView.BufferType.NORMAL)
        init_edit.setText(character.initiative.toString(), TextView.BufferType.NORMAL)
        prof_edit.setText(character.proficiencies.size.toString(), TextView.BufferType.NORMAL)
        speed_edit.setText(character.speed.toString(), TextView.BufferType.NORMAL)
        race_edit.setText(character.race, TextView.BufferType.NORMAL)
        aware_edit.setText(character.awareness.toString(), TextView.BufferType.NORMAL)

        lvl_edit.setText(character.level.toString(), TextView.BufferType.NORMAL)
        class_edit.setText(character.cClass, TextView.BufferType.NORMAL)
        hit_dice_edit.setText(character.hitDice, TextView.BufferType.NORMAL)


        btnOverview.isEnabled = false
        btnAttributes.setOnClickListener {
            //intent.putExtra()
            startActivity(Intent(this, Attributes::class.java).apply {
                putExtra(Constants.ARG_CHARACTER_ID, character.id)
            })
        }
        btnSkills.setOnClickListener {}
        btnAttacks.setOnClickListener {}


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu to use in the action bar
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_overview, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item!!.itemId

        if (id == R.id.character_list_btn) {
            startActivity(Intent(this, MainActivity::class.java))
            return true
        } else if (id == R.id.action_favorite) {
            Toast.makeText(this@OverviewActivity, "Its Save!", Toast.LENGTH_SHORT).show()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun newCharacter() = Character().apply {
        CharacterRepository.save(this)

    }

    private fun notBlank(sequence: CharSequence?) =
        if (sequence?.isNotBlank() == true) sequence.toString() to null else null to "Must not be blank"

    private fun uintCheck(sequence: CharSequence?): Pair<Int?, String?> {
        val int = sequence?.toString()?.toIntOrNull() ?: -1
        return if (int >= 0)
            int to null
        else
            null to "Must be a positive integer"
    }

    private fun diceCheck(sequence: CharSequence?): Pair<String?, String?> {
        val lower = sequence?.toString()?.toLowerCase()
        return if (lower?.matches(Regex("^\\dd(4|6|8|10|12|20)$")) == true)
            lower to null
        else
            null to "Must be a valid dice type"
    }
}

private fun <T> EditText.newOnTextChanged(lam: (T?) -> Unit, validate: ((CharSequence?) -> Pair<T?, String?>)? = null) {
    this.addTextChangedListener(
        object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                var value: T? = null
                var error: String? = null
                if (validate != null) {
                    val (v, e) = validate(p0)
                    value = v
                    error = e
                }
                if (error == null) {
                    this@newOnTextChanged.error = null
                    lam(value)
                } else {
                    this@newOnTextChanged.error = error
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        }
    )
}