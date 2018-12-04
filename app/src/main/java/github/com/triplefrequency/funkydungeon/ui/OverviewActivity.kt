package github.com.triplefrequency.funkydungeon.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import github.com.triplefrequency.funkydungeon.R
import kotlinx.android.synthetic.main.activity_overview.*


class OverviewActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_overview)

        val btnOverview = findViewById<Button>(R.id.btn_overview)
        val btnAttributes = findViewById<Button>(R.id.btn_attributes)
        val btnSkills = findViewById<Button>(R.id.btn_skills)
        val btnAttacks = findViewById<Button>(R.id.btn_attacks)

        var nameEdit = findViewById<EditText>(R.id.name_edit)
        var defEdit = findViewById<EditText>(R.id.def_edit)
        var hpEdit = findViewById<EditText>(R.id.hp_edit)
        var initEdit = findViewById<EditText>(R.id.init_edit)
        var profEdit = findViewById<EditText>(R.id.prof_edit)
        var speedEdit = findViewById<EditText>(R.id.speed_edit)
        var raceEdit = findViewById<EditText>(R.id.race_edit)
        var awareEdit = findViewById<EditText>(R.id.aware_edit)

        var etList =
            listOf<EditText>(nameEdit, defEdit, hpEdit, initEdit, profEdit, speedEdit, raceEdit, awareEdit)


        for (et in etList) {
            et.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(editable: Editable?) {}

                override fun beforeTextChanged(str: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(str: CharSequence?, start: Int, count: Int, after: Int) {
                    //TODO change database when entry changed, find out how to get the given id.
                    updateDatabase(et, str)
                }
            })
        }

        btnOverview.isEnabled = false
        btnAttributes.setOnClickListener {
            //intent.putExtra(the id would go here if not already in intent from main activity)
            //startActivity(Intent(this, MainActivity::class.java))
        }
        btnSkills.setOnClickListener {}
        btnAttacks.setOnClickListener {}
        //TODO Need to have a way to dynamically change values from storage

    }

    private fun updateDatabase(et: EditText, str: CharSequence?) {

        if(str == null){}
        else{
            when(et.id){
                R.id.name_edit -> print(str)
                R.id.def_edit -> print(str)
                R.id.hp_edit -> print(str)
                R.id.init_edit -> print(str)
                R.id.prof_edit -> print(str)
                R.id.speed_edit -> print(str)
                R.id.race_edit -> print(str)
                R.id.aware_edit -> print(str)

            }
        }
    }
}
