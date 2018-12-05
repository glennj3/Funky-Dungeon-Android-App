package github.com.triplefrequency.funkydungeon.ui.proficiency


import android.content.Intent
import android.databinding.ObservableList
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import github.com.triplefrequency.funkydungeon.R
import github.com.triplefrequency.funkydungeon.core.Constants
import github.com.triplefrequency.funkydungeon.repository.CharacterRepository
import github.com.triplefrequency.funkydungeon.model.Character;

class ProficiencyActivity : AppCompatActivity() {
    private var proficiencyList: ObservableList<String>? = null
    private var mRecyclerView: RecyclerView? = null
    private var mAdapter: ProficiencyListAdapter? = null
    private var character: Character? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val profIntent = intent
        if (profIntent != null) {
            val id = profIntent.getStringExtra(Constants.ARG_CHARACTER_ID)
            if (id != null) {
                character = CharacterRepository.characters[id]
                if (character != null) {
                    proficiencyList = character!!.proficiencies
                }
            }
        }
        mRecyclerView = findViewById(R.id.prof_recyclerview)
        mAdapter = ProficiencyListAdapter(this, proficiencyList)
        mRecyclerView!!.adapter = mAdapter
        mRecyclerView!!.layoutManager = LinearLayoutManager(this)
        val fab = findViewById<FloatingActionButton>(R.id.prof_fab)
        fab.setOnClickListener {
            val wordListSize = proficiencyList!!.size
            proficiencyList!!.add("New Proficiency")
            mRecyclerView!!.adapter.notifyItemInserted(wordListSize)
            mRecyclerView!!.smoothScrollToPosition(wordListSize)
        }
    }
}