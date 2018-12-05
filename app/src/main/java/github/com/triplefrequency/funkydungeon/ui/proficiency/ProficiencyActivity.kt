package github.com.triplefrequency.funkydungeon.ui.proficiency


import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.LinearLayoutManager
import github.com.triplefrequency.funkydungeon.R
import github.com.triplefrequency.funkydungeon.ui.CharacterActivity
import kotlinx.android.synthetic.main.activity_proficiency.*
import kotlinx.android.synthetic.main.content_proficiency.*
import kotlinx.android.synthetic.main.toolbar_bottom.*

class ProficiencyActivity : CharacterActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_proficiency)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        btn_skills.isEnabled = false

        val mAdapter = ProficiencyListAdapter(this, character.proficiencies)
        prof_recyclerview.adapter = mAdapter
        prof_recyclerview.layoutManager = LinearLayoutManager(this)
        val fab = findViewById<FloatingActionButton>(R.id.prof_fab)
        fab.setOnClickListener {
            val wordListSize = character.proficiencies.size
            character.proficiencies.add("New Proficiency")
            prof_recyclerview.adapter.notifyItemInserted(wordListSize)
            prof_recyclerview.smoothScrollToPosition(wordListSize)
        }
    }
}