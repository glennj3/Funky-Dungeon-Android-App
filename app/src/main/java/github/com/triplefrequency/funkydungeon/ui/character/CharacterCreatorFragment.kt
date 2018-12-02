package github.com.triplefrequency.funkydungeon.ui.character

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import github.com.triplefrequency.funkydungeon.R
import github.com.triplefrequency.funkydungeon.model.Character
import github.com.triplefrequency.funkydungeon.ui.MainActivity
import kotlinx.android.synthetic.main.activity_character_list.*
import github.com.triplefrequency.funkydungeon.core.Constants.ARG_CHARACTER_ID

class CharacterCreatorFragment : Fragment() {
    private var item: Character? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(ARG_CHARACTER_ID)) {
                item = MainActivity.characterContent.characterMap[it.getString(ARG_CHARACTER_ID)]
                activity?.toolbar?.title = item?.name
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_character_creator, container, false)

        item?.let {

        }

        return rootView
    }
}