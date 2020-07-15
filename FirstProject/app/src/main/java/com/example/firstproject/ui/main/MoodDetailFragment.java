package com.example.firstproject.ui.main;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.firstproject.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContactDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MoodDetailFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String MOOD = "mood";
    private static final String PICTURE = "picture";
    private static final String TEXT = "text";

    // TODO: Rename and change types of parameters
    private String mMood;
    private String mPicture;
    private String mText;

    private TextView moodFragment;
    private ImageView pictureFragment;
    private TextView textFragment;

    public MoodDetailFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MoodDetailFragment newInstance(String mood, String picture, String text) {
        MoodDetailFragment fragment = new MoodDetailFragment();
        Bundle args = new Bundle();
        args.putString(MOOD, mood);
        args.putString(PICTURE, picture);
        args.putString(TEXT, text);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mMood = getArguments().getString(MOOD);
            mPicture = getArguments().getString(PICTURE);
            mText = getArguments().getString(TEXT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_mood_detail, container, false);
        moodFragment = view.findViewById(R.id.mood_fragment);
        pictureFragment = view.findViewById(R.id.picture_fragment);
        textFragment = view.findViewById(R.id.text_fragment);

        switch (mMood) {
            case "not chosen":view.setBackgroundColor(Color.WHITE); break;
            case "joy":
                view.setBackgroundColor(getResources().getColor(R.color.Joy)); break;
            case "sadness":
                view.setBackgroundColor(getResources().getColor(R.color.Sadness)); break;
            case "anger":
                view.setBackgroundColor(getResources().getColor(R.color.Anger)); break;
            case "neutral":
                view.setBackgroundColor(getResources().getColor(R.color.Neutral)); break;
        }

        moodFragment.setText(mMood);
        //pictureFragment.setText(mPhoneNum);
        textFragment.setText(mText);
        return view;
    }
}