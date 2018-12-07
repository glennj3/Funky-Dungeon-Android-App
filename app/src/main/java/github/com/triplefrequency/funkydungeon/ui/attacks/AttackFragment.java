package github.com.triplefrequency.funkydungeon.ui.attacks;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import github.com.triplefrequency.funkydungeon.R;
import github.com.triplefrequency.funkydungeon.model.Character;
import github.com.triplefrequency.funkydungeon.model.CharacterContent;
import github.com.triplefrequency.funkydungeon.model.CharacterWeapon;
import github.com.triplefrequency.funkydungeon.repository.CharacterRepository;

public class AttackFragment extends Fragment {

    private EditText attackText;
    private EditText attackAttribute;
    private Button attackAccept;
    private Character character;
    private String attackString;
    private String attributeString;

    //private OnFragmentInteractionListener mListener;

    public AttackFragment() {
        // Required empty public constructor
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String charId = getArguments().getString("id");
        character = CharacterContent.INSTANCE.getCharacterMap().get(charId);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_attack, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        attackText = view.findViewById(R.id.attackEditText);
        attackAttribute = view.findViewById(R.id.attackAttribute);
        attackAccept = view.findViewById(R.id.attackAccept);

        attackAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attackString = attackText.getText().toString();
                attributeString = attackText.getText().toString();
                if(attackString == null || attackString == null){
                    Toast.makeText(getActivity(), "All fields must be filled out",Toast.LENGTH_SHORT).show();
                }
                else{
                    CharacterWeapon attack = new CharacterWeapon(character, attackString, attributeString, "1d8");
                    character.getAttacks().add(attack);
                    getActivity().getFragmentManager().popBackStack();
                }
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }

}
