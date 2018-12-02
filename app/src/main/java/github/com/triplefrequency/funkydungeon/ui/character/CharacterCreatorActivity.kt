package github.com.triplefrequency.funkydungeon.ui.character

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import github.com.triplefrequency.funkydungeon.R
import github.com.triplefrequency.funkydungeon.ui.MainActivity
import kotlinx.android.synthetic.main.activity_character_creator.*
import github.com.triplefrequency.funkydungeon.core.Constants.ARG_CHARACTER_ID

class CharacterCreatorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_creator)

        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (savedInstanceState == null) {
            val fragment = CharacterCreatorFragment().apply {
                arguments = Bundle().apply {
                    putString(
                        ARG_CHARACTER_ID,
                        intent.getStringExtra(ARG_CHARACTER_ID)
                    )
                }
            }

            supportFragmentManager.beginTransaction()
                .add(R.id.character_creator_container, fragment)
                .commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            navigateUpTo(Intent(this, MainActivity::class.java))
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}
