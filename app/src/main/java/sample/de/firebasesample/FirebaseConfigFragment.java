package sample.de.firebasesample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.util.HashMap;

/**
 * A placeholder fragment containing a simple view.
 */
public class FirebaseConfigFragment extends Fragment {

    public static final String DEFAULT_TEXT_KEY = "default_text";
    FirebaseRemoteConfig remoteConfig = FirebaseRemoteConfig.getInstance();
    TextView mainText;

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";


    public FirebaseConfigFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static FirebaseConfigFragment newInstance(int sectionNumber) {
        FirebaseConfigFragment fragment = new FirebaseConfigFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        remoteConfig.setConfigSettings(new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(true).build());

        HashMap<String, Object> defaultSettings = new HashMap<>();
        defaultSettings.put(DEFAULT_TEXT_KEY, "This is sample text");

        remoteConfig.setDefaults(defaultSettings);
        Task<Void> fetch = remoteConfig.fetch(0);
        fetch.addOnSuccessListener(getActivity(), new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                remoteConfig.activateFetched();
                updateMainText();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_firebase_config, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        mainText = (TextView) rootView.findViewById(R.id.content_text);

        textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
        updateMainText();
        return rootView;
    }

    private void updateMainText() {
        mainText.setText(remoteConfig.getString(DEFAULT_TEXT_KEY));
    }
}
