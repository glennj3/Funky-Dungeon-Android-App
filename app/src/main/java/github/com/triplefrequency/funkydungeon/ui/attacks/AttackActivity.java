package github.com.triplefrequency.funkydungeon.ui.attacks;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import github.com.triplefrequency.funkydungeon.R;
import github.com.triplefrequency.funkydungeon.core.Constants;
import github.com.triplefrequency.funkydungeon.model.Character;
import github.com.triplefrequency.funkydungeon.model.CharacterWeapon;
import github.com.triplefrequency.funkydungeon.repository.CharacterRepository;
import github.com.triplefrequency.funkydungeon.ui.CharacterActivity;
import github.com.triplefrequency.funkydungeon.ui.attributes.AttributeAdjust;
import github.com.triplefrequency.funkydungeon.ui.attributes.AttributeListAdapter;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AttackActivity extends CharacterActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attack_layout);
        findViewById(R.id.btn_attacks).setEnabled(false);
        FloatingActionButton addAttack = findViewById(R.id.addAttack);

        // Get a handle to the RecyclerView.
        RecyclerView mRecyclerView = findViewById(R.id.attackRecyclerView);
        // Create an adapter and supply the data to be displayed.
        AttackListAdapter mAdapter = new AttackListAdapter(this, getCharacter());
        // Connect the adapter with the RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
        // Give the RecyclerView a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        addAttack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create fragment and add bundle
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                FragmentManager fragmentManager = activity.getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment attackFragment = new AttackFragment();
                fragmentTransaction.replace(android.R.id.content, attackFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }
}