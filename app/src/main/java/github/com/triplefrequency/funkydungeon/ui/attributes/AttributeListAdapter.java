package github.com.triplefrequency.funkydungeon.ui.attributes;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import github.com.triplefrequency.funkydungeon.R;
import github.com.triplefrequency.funkydungeon.model.Character;
import kotlin.Pair;

import java.util.List;
import java.util.Map;

public class AttributeListAdapter extends RecyclerView.Adapter<AttributeListAdapter.AttributeViewHolder> {

    private LayoutInflater mInflater;
    private final List<Pair<String, Integer>> mAttrList;
    private Character character;

    public AttributeListAdapter(Context context, List<Pair<String, Integer>> attrList, Character charac) {
        mInflater = LayoutInflater.from(context);
        character = charac;
        this.mAttrList = attrList;
    }

    @NonNull
    @Override
    public AttributeListAdapter.AttributeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View mAttrView = mInflater.inflate(R.layout.attribute_list_item, parent, false);
        return new AttributeViewHolder(mAttrView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull AttributeListAdapter.AttributeViewHolder attrViewHolder, int position) {
        Pair<String, Integer> entry = mAttrList.get(position);
        attrViewHolder.attrStringView.setText(entry.getFirst());
        attrViewHolder.attrIntView.setText(String.valueOf(entry.getSecond()));
    }

    @Override
    public int getItemCount() {
        return mAttrList.size();
    }

    class AttributeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView attrStringView;
        public final TextView attrIntView;
        final AttributeListAdapter mAdapter;

        public AttributeViewHolder(View itemView, AttributeListAdapter adapter) {
            super(itemView);
            attrStringView = itemView.findViewById(R.id.attribute);
            attrIntView = itemView.findViewById(R.id.attrCount);
            this.mAdapter = adapter;
        }

        @Override
        public void onClick(View v) {
            // Get the position of the item that was clicked.
            int mPosition = getLayoutPosition();
            // Use that to access the affected item in attrStrings.
            Pair<String, Integer> entry = mAttrList.get(mPosition);
            //Get the corresponding key and bundle both
            int value = entry.getSecond();
            Bundle bundle = new Bundle();
            bundle.putInt("Attribute", value);
            String id = character.getId();
            bundle.putString("id", id);

            //Create fragment and add bundle
            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            FragmentManager fragmentManager = activity.getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            Fragment attrAdjust = new AttributeAdjust();
            attrAdjust.setArguments(bundle);
            fragmentTransaction.replace(android.R.id.content, attrAdjust);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

            // Change the word in the mWordList.
            //mWordList.set(mPosition, "Clicked! " + element);
            // Notify the adapter, that the data has changed so it can
            // update the RecyclerView to display the data.
            mAdapter.notifyDataSetChanged();
        }
    }
}
