package github.com.triplefrequency.funkydungeon.ui.attacks;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import github.com.triplefrequency.funkydungeon.R;
import github.com.triplefrequency.funkydungeon.model.Character;
import github.com.triplefrequency.funkydungeon.model.CharacterWeapon;

import java.util.List;

public class AttackListAdapter extends RecyclerView.Adapter<AttackListAdapter.AttackViewHolder>  {

    private LayoutInflater mInflater;
    private List<CharacterWeapon> mAttackList;

    public AttackListAdapter(Context context, Character charac) {
        mInflater = LayoutInflater.from(context);
        this.mAttackList = charac.getAttacks();
    }
    @NonNull
    @Override
    public AttackListAdapter.AttackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View mAttackView = mInflater.inflate(R.layout.fragment_attack, parent, false);

        return new AttackListAdapter.AttackViewHolder(mAttackView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull AttackListAdapter.AttackViewHolder attackViewHolder, int position) {
        String mCurrent = mAttackList.get(position).getName();
        attackViewHolder.attackStringView.setText(mCurrent);
    }

    @Override
    public int getItemCount() {
        return mAttackList.size();
    }

    class AttackViewHolder extends RecyclerView.ViewHolder {
        final AttackListAdapter mAdapter;
        private TextView attackStringView;

        public AttackViewHolder(View itemView, AttackListAdapter adapter) {
            super(itemView);
            attackStringView = itemView.findViewById(R.id.attack);
            this.mAdapter = adapter;
        }
    }
}