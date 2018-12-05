package github.com.triplefrequency.funkydungeon.ui.attributes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import github.com.triplefrequency.funkydungeon.R;
import github.com.triplefrequency.funkydungeon.core.Constants;
import github.com.triplefrequency.funkydungeon.model.Character;
import github.com.triplefrequency.funkydungeon.repository.CharacterRepository;

import java.util.*;

public class Attributes extends AppCompatActivity {

    private Map<String, Integer> attrMap;
    private List<Map.Entry<String, Integer>> attrEntryList;
    private RecyclerView mRecyclerView;
    private AttributeListAdapter mAdapter;
    private Character character;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pm_attributes_layout);
        Intent attrIntent = getIntent();
        if (attrIntent != null) {
            String id = attrIntent.getStringExtra(Constants.ARG_CHARACTER_ID);
            if (id != null) {
                character = CharacterRepository.INSTANCE.getCharacters().get(id);
                if (character != null){
                    attrMap = character.getAttributes();
                    attrEntryList = character.getAttributeEntries();
                }
            }
        }

        // Get a handle to the RecyclerView.
        mRecyclerView = findViewById(R.id.attrRecyclerView);
        // Create an adapter and supply the data to be displayed.
        mAdapter = new AttributeListAdapter(this, attrMap, attrEntryList, character);
        // Connect the adapter with the RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
        // Give the RecyclerView a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


}
