package github.com.triplefrequency.funkydungeon.ui

import android.app.Activity
import android.content.Intent
import android.databinding.ObservableMap
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import github.com.triplefrequency.funkydungeon.R
import github.com.triplefrequency.funkydungeon.model.Character
import github.com.triplefrequency.funkydungeon.model.CharacterContent
import github.com.triplefrequency.funkydungeon.repository.CharacterRepository
import github.com.triplefrequency.funkydungeon.ui.characterlist.CharacterRecyclerViewAdapter
import github.com.triplefrequency.funkydungeon.ui.dice.TwoDice_Fragment
import kotlinx.android.synthetic.main.activity_character_list.*

class MainActivity : AppCompatActivity() {

    private var sideBySide = false

    internal lateinit var toTheDice: FloatingActionButton

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

        CharacterContent.characterMap.addOnMapChangedCallback(object :
            ObservableMap.OnMapChangedCallback<ObservableMap<String, Character>, String, Character>() {
            override fun onMapChanged(sender: ObservableMap<String, Character>, key: String) {
                characterRecyclerView.adapter.notifyDataSetChanged()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        characterRecyclerView.adapter.notifyDataSetChanged()
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu?.clear()
        if (FirebaseAuth.getInstance().currentUser != null)
            menu?.add(0, MENU_LOGOUT, Menu.NONE, R.string.logout)
        else
            menu?.add(0, MENU_LOGIN, Menu.NONE, R.string.login)
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)

        when (item.itemId) {
            MENU_LOGIN -> {
                startActivityForResult(
                    AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(
                        AUTH_PROVIDERS
                    ).setLogo(R.drawable.funky_dungeon_logo).setTheme(R.style.AppTheme).build(), RC_SIGN_IN
                )
            }
            MENU_LOGOUT -> {
                AuthUI.getInstance().signOut(this).continueWith {
                    FirebaseFirestore.getInstance().disableNetwork()
                }.addOnCompleteListener {
                    Toast.makeText(this, "Successfully Logged Out", Toast.LENGTH_SHORT).show()
                }
            }
        }
        return false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                FirebaseFirestore.getInstance().enableNetwork().addOnCompleteListener {
                    val user = FirebaseAuth.getInstance().currentUser!!
                    CharacterRepository.characters(null).forEach { it.value.authorUid = user.uid }
                    CharacterContent.updateCache()
                        .invokeOnCompletion { characterRecyclerView.adapter.notifyDataSetChanged() }
                }
            } else {
                Toast.makeText(this, "Failed to Log In", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        private const val MENU_LOGIN = Menu.FIRST
        private const val MENU_LOGOUT = MENU_LOGIN + 1

        private val AUTH_PROVIDERS
            get() = listOf(
                AuthUI.IdpConfig.EmailBuilder().build(),
                AuthUI.IdpConfig.GoogleBuilder().build(),
                AuthUI.IdpConfig.GitHubBuilder().build()
            )

        private const val RC_SIGN_IN = 889
    }
}
