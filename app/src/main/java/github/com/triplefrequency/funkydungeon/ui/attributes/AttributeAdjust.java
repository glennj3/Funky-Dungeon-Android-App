package github.com.triplefrequency.funkydungeon.ui.attributes;

import android.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import github.com.triplefrequency.funkydungeon.R;
import github.com.triplefrequency.funkydungeon.model.Character;
import github.com.triplefrequency.funkydungeon.model.CharacterContent;
import github.com.triplefrequency.funkydungeon.repository.CharacterRepository;
import kotlin.Pair;

public class AttributeAdjust extends Fragment {

    private Button accButton;
    private Button incButton;
    private Button decButton;
    private TextView attrLevel;
    private Integer attrInt;
    private String attrString;
    private Character character;
    private int index;

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

        /*
         * Inflate the layout for this fragment
         */

        return inflater.inflate(R.layout.attribute_adjuster, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        index = getArguments().getInt("Index");

        String charId = getArguments().getString("id");
        character = CharacterContent.INSTANCE.getCharacterMap().get(charId);
        attrString = character.getAttributes().get(index).component1();
        attrInt = character.getAttributes().get(index).component2();

        incButton = view.findViewById(R.id.increment);
        decButton = view.findViewById(R.id.decrement);
        attrLevel = view.findViewById(R.id.attrAdj);
        accButton = view.findViewById(R.id.accept);

        if(attrInt == null)
            attrLevel.setText("0");
        else
            attrLevel.setText(attrInt.toString());

        incButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current = Integer.parseInt(attrLevel.getText().toString());
                attrInt = current + 1;
                attrLevel.setText(attrInt.toString());
                //change attribute in database
                character.getAttributes().set(index, new Pair<>(attrString, attrInt));
            }
        });

        decButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current = Integer.parseInt(attrLevel.getText().toString());
                attrInt = current - 1;
                attrLevel.setText(attrInt.toString());
                //change attribute in database
                character.getAttributes().set(index, new Pair<>(attrString, attrInt));
            }
        });
        accButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                getActivity().getFragmentManager().popBackStack();
            }
        });
    }
}
