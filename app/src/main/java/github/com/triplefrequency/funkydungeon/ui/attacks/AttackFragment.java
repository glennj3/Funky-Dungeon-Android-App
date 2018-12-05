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

import android.widget.EditText;
import github.com.triplefrequency.funkydungeon.R;
import github.com.triplefrequency.funkydungeon.model.Character;
import github.com.triplefrequency.funkydungeon.model.CharacterWeapon;
import github.com.triplefrequency.funkydungeon.repository.CharacterRepository;

public class AttackFragment extends Fragment {

    private EditText attackText;
    private EditText attackAttribute;
    private Character character;
    private String attackString;
    private String attributeString;

    private OnFragmentInteractionListener mListener;

    public AttackFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String charId = getArguments().getString("id");
        character = CharacterRepository.INSTANCE.getCharacters().get(charId);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_attack, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        attackText = view.findViewById(R.id.attackEditText);
        attackAttribute = view.findViewById(R.id.attackAttribute);

            attackText.addTextChangedListener(new TextWatcher() {

                public void afterTextChanged(Editable s) {

                    attackString = attackText.getText().toString();
                    //Return string to attack activity

                    attributeString = attackAttribute.getText().toString();

                    if (attributeString != null) {

                        CharacterWeapon attack = new CharacterWeapon(character, attackString, "null");
                        character.getAttacks().add(attack);

                    }

                    CharacterWeapon attack = new CharacterWeapon(character, attackString, "null");
                    character.getAttacks().add(attack);
                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

            }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
