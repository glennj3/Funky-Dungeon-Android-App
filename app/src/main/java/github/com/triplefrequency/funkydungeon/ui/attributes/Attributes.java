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

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public class Attributes extends AppCompatActivity {
    //private final LinkedList<String> attributeList = new LinkedList<>();
    //private final LinkedList<Integer> attrIntList = new LinkedList<>();
    private Map<String, Integer> attrMap;
    private RecyclerView mRecyclerView;
    private AttributeListAdapter mAdapter;
    private Character character;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent attrIntent = getIntent();
        if (attrIntent != null) {
            String id = attrIntent.getStringExtra(Constants.ARG_CHARACTER_ID);
            if (id != null) {
                character = CharacterRepository.INSTANCE.getCharacters().get(id);
                if (character != null){
                    attrMap = character.getAttributes();
                }
            }
        }

        importAttributes();
        // Get a handle to the RecyclerView.
        mRecyclerView = findViewById(R.id.attrRecyclerView);
        // Create an adapter and supply the data to be displayed.
        mAdapter = new AttributeListAdapter(this, attrMap);
        // Connect the adapter with the RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
        // Give the RecyclerView a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    private void importAttributes(){
        //attrMap =
    }

}
