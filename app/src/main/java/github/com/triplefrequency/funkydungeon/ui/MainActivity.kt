package github.com.triplefrequency.funkydungeon.ui

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import github.com.triplefrequency.funkydungeon.R
import github.com.triplefrequency.funkydungeon.model.CharacterContent
import github.com.triplefrequency.funkydungeon.ui.characterlist.CharacterRecyclerViewAdapter
import github.com.triplefrequency.funkydungeon.ui.dice.TwoDice_Fragment
import kotlinx.android.synthetic.main.activity_character_list.*

class MainActivity : AppCompatActivity() {

    private var sideBySide = false

    internal lateinit var toTheDice: FloatingActionButton

    private var charCount = CharacterContent.characters.size

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_list)
        toTheDice = findViewById(R.id.toTheDice)
        toTheDice.setOnClickListener {
            val config = resources.configuration

            val fragmentManager = fragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            val twodice_fragment = TwoDice_Fragment()
            fragmentTransaction.replace(android.R.id.content, twodice_fragment)
            fragmentTransaction.commit()
        }
        setSupportActionBar(toolbar)

        floatingActionButton.setOnClickListener {
            // Don't pass a character ID, the overview activity will automatically create a new character.
            startActivity(Intent(it.context, OverviewActivity::class.java))
        }

        // if (character_creator_container != null)
        //     sideBySide = true

        characterRecyclerView.adapter = CharacterRecyclerViewAdapter(this, CharacterContent, sideBySide)
    }

    override fun onResume() {
        super.onResume()

        if (CharacterContent.characters.size != charCount) {
            charCount = CharacterContent.characters.size
            // TODO Change this to be more efficient
            characterRecyclerView.adapter.notifyDataSetChanged()
        }
    }
}
