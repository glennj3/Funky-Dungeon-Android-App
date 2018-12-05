package github.com.triplefrequency.funkydungeon.ui.proficiency;

import android.content.Context;
import android.databinding.ObservableList;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import github.com.triplefrequency.funkydungeon.R;

public class ProficiencyListAdapter extends RecyclerView.Adapter<ProficiencyListAdapter.ProficiencyViewHolder>{
    private final ObservableList<String> mProficiencyList;
    private LayoutInflater mInflater;
    class ProficiencyViewHolder extends RecyclerView.ViewHolder {
        public final EditText proficiencyItemView;
        final ProficiencyListAdapter mAdapter;
        public ProficiencyViewHolder(View itemView, ProficiencyListAdapter adapter) {
            super(itemView);
            proficiencyItemView = itemView.findViewById(R.id.prof_item);
            proficiencyItemView.addTextChangedListener(new TextWatcher() {

                public void afterTextChanged(Editable s) {
                    int mPosition = getLayoutPosition();
                    mProficiencyList.set(mPosition, proficiencyItemView.getText().toString());
                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                public void onTextChanged(CharSequence s, int start, int before, int count) {}
            });
            this.mAdapter = adapter;
        }

    }
    public ProficiencyListAdapter(Context context,
                           ObservableList<String> proficiencyList) {
        mInflater = LayoutInflater.from(context);
        this.mProficiencyList = proficiencyList;
    }
    @Override
    public ProficiencyListAdapter.ProficiencyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.proficiency_list_item, parent, false);
        return new ProficiencyViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(ProficiencyViewHolder holder, int position) {
        String mCurrent = mProficiencyList.get(position);
        holder.proficiencyItemView.setText(mCurrent);
    }

    @Override
    public int getItemCount() {
        return mProficiencyList.size();
    }
}
