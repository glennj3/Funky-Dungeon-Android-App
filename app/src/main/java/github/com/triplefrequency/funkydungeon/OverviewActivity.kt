package github.com.triplefrequency.funkydungeon

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button


class OverviewActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {

        var btnOverview = findViewById<Button>(R.id.btn_overview)
        var btnAttributes= findViewById<Button>(R.id.btn_attributes)
        var btnSkills = findViewById<Button>(R.id.btn_skills)
        var btnAttacks = findViewById<Button>(R.id.btn_attacks)

        
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_overview)
        btnOverview.setOnClickListener {}
        btnAttributes.setOnClickListener {}
        btnSkills.setOnClickListener {}
        btnAttacks.setOnClickListener {}
        //TODO Need to have a way to dynamically change values from storage

    }


}
