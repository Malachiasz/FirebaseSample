package sample.de.firebasesample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A placeholder fragment containing a simple view.
 */
public class FirebaseDBFragment extends Fragment implements View.OnClickListener {

    public static final String DEFAULT_TEXT_KEY = "default_text";
    DatabaseReference rootReference = FirebaseDatabase.getInstance().getReference();
    DatabaseReference textReference = rootReference.child(DEFAULT_TEXT_KEY);

    TextView mainText;
    Button updateDBButton;
    EditText dbTextEdit;

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";


    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static FirebaseDBFragment newInstance(int sectionNumber) {
        FirebaseDBFragment fragment = new FirebaseDBFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_firebase_db, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        mainText = (TextView) rootView.findViewById(R.id.content_text);
        updateDBButton = (Button) rootView.findViewById(R.id.dbSubmitButton);
        dbTextEdit = (EditText) rootView.findViewById(R.id.dbTextEdit);

        textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
        mainText.setText("Local text");
        updateDBButton.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
       // textReference.addChildEventListener()
        textReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mainText.setText(dataSnapshot.getValue(String.class));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        textReference.setValue(dbTextEdit.getText().toString());
    }
}
