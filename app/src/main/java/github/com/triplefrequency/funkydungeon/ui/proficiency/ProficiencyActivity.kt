package github.com.triplefrequency.funkydungeon.ui.proficiency


import android.databinding.ObservableList
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import github.com.triplefrequency.funkydungeon.R
import github.com.triplefrequency.funkydungeon.core.Constants
import github.com.triplefrequency.funkydungeon.model.Character
import github.com.triplefrequency.funkydungeon.model.CharacterContent
import kotlinx.android.synthetic.main.content_proficiency.*

class ProficiencyActivity : AppCompatActivity() {
    private var proficiencyList: ObservableList<String>? = null
    private var mAdapter: ProficiencyListAdapter? = null
    private var character: Character? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val profIntent = intent
        if (profIntent != null) {
            val id = profIntent.getStringExtra(Constants.ARG_CHARACTER_ID)
            if (id != null) {
                character = CharacterContent.characterMap[id]
                if (character != null) {
                    proficiencyList = character?.proficiencies
                }
            }
        }
        mAdapter = ProficiencyListAdapter(this, proficiencyList)
        prof_recyclerview.adapter = mAdapter
        prof_recyclerview.layoutManager = LinearLayoutManager(this)
        val fab = findViewById<FloatingActionButton>(R.id.prof_fab)
        fab.setOnClickListener {
            val wordListSize = proficiencyList!!.size
            proficiencyList!!.add("New Proficiency")
            prof_recyclerview.adapter.notifyItemInserted(wordListSize)
            prof_recyclerview.smoothScrollToPosition(wordListSize)
        }
    }
}