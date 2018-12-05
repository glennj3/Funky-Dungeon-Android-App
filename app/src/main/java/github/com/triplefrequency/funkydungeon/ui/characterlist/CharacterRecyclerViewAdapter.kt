package github.com.triplefrequency.funkydungeon.ui.characterlist

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import github.com.triplefrequency.funkydungeon.R
import github.com.triplefrequency.funkydungeon.core.Constants.ARG_CHARACTER_ID
import github.com.triplefrequency.funkydungeon.model.Character
import github.com.triplefrequency.funkydungeon.model.CharacterContent
import github.com.triplefrequency.funkydungeon.ui.MainActivity
import github.com.triplefrequency.funkydungeon.ui.OverviewActivity
import github.com.triplefrequency.funkydungeon.ui.character.CharacterCreatorActivity
import github.com.triplefrequency.funkydungeon.ui.character.CharacterCreatorFragment
import kotlinx.android.synthetic.main.rv_character_list_item.view.*

class CharacterRecyclerViewAdapter(
    private val parentActivity: MainActivity,
    private val content: CharacterContent,
    private val sideBySide: Boolean
) : RecyclerView.Adapter<CharacterRecyclerViewAdapter.CharacterViewHolder>() {
    private val onClickListener: View.OnClickListener

    init {
        onClickListener = View.OnClickListener {
            val item = it.tag as Character
            if (sideBySide)
                setFragment(item)
            else {
                val intent = Intent(it.context, OverviewActivity::class.java).apply {
                    putExtra(ARG_CHARACTER_ID, item.id)
                }
                it.context.startActivity(intent)
            }
        }
    }

    private fun setFragment(item: Character) {
        val fragment = CharacterCreatorFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_CHARACTER_ID, item.id)
            }
        }
        parentActivity.supportFragmentManager
            .beginTransaction()
            .replace(R.id.character_creator_container, fragment)
            .commit()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_character_list_item, parent, false)
        return CharacterViewHolder(view)
    }

    override fun getItemCount(): Int = content.characterMap.size

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val item = content.characters[position]
        holder.contentView.text = item.name

        with(holder.itemView) {
            tag = item
            setOnClickListener(onClickListener)
        }
    }

    inner class CharacterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val contentView: TextView = view.characterName
    }
}