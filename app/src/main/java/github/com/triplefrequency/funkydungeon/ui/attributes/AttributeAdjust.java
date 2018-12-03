package github.com.triplefrequency.funkydungeon.ui.attributes;

import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import github.com.triplefrequency.funkydungeon.R;

public class AttributeAdjust extends Fragment {

    private Button incButton;
    private Button decButton;
    private TextView attrLevel;
    private int attrInt;

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
        attrInt = getArguments().getInt("Attribute");
        /**
         * Inflate the layout for this fragment
         */
        Configuration config = getActivity().getResources().getConfiguration();
        return inflater.inflate(R.layout.attribute_adjuster, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        incButton = view.findViewById(R.id.increment);
        decButton = view.findViewById(R.id.decrement);
        attrLevel = view.findViewById(R.id.attrCount);

        attrLevel.setText(attrInt);

        incButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current = Integer.parseInt(attrLevel.getText().toString());
                attrLevel.setText(current+1);
            }
        });

        decButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current = Integer.parseInt(attrLevel.getText().toString());
                attrLevel.setText(current-1);
            }
        });
    }
    @Override
    public void onDestroy(){
        super.onDestroy();

    }
}
