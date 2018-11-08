package github.com.triplefrequency.funkydungeon.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import github.com.triplefrequency.funkydungeon.R
import github.com.triplefrequency.funkydungeon.model.CharacterContent
import github.com.triplefrequency.funkydungeon.ui.characterlist.CharacterRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_character_list.*

class MainActivity : AppCompatActivity() {

    private var sideBySide = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_list)

        setSupportActionBar(toolbar)

        if (!charactersLoaded) {
            characterContent = CharacterContent()
            charactersLoaded = true
        }

        if (character_creator_container != null)
            sideBySide = true

        characterRecyclerView.adapter = CharacterRecyclerViewAdapter(this, characterContent.characters, sideBySide)
    }

    companion object {
        lateinit var characterContent: CharacterContent
            private set
        var charactersLoaded = false
            private set
    }
}
