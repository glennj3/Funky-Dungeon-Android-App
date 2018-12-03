package github.com.triplefrequency.funkydungeon.ui.attributes;

import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import github.com.triplefrequency.funkydungeon.R;

public class AttributeAdjust extends Fragment {

    public AttributeAdjust() {
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /**
         * Inflate the layout for this fragment
         */
        Configuration config = getActivity().getResources().getConfiguration();
        return inflater.inflate(R.layout.attribute_adjuster, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

    }
}
