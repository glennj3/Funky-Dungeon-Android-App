package github.com.triplefrequency.funkydungeon.ui.attributes;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import github.com.triplefrequency.funkydungeon.R;
import github.com.triplefrequency.funkydungeon.ui.CharacterActivity;

public class Attributes extends CharacterActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pm_attributes_layout);
        findViewById(R.id.btn_attributes).setEnabled(false);

        // Get a handle to the RecyclerView.
        RecyclerView mRecyclerView = findViewById(R.id.attrRecyclerView);
        // Create an adapter and supply the data to be displayed.
        AttributeListAdapter mAdapter = new AttributeListAdapter(this, getCharacter());
        // Connect the adapter with the RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
        // Give the RecyclerView a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
