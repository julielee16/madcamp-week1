package com.example.firstproject.ui.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.firstproject.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContactDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactDetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String NAME = "name";
    private static final String NICKNAME = "nickname";
    private static final String PHONENUM = "phone_num";
    private static final String EMAIL = "email";

    // TODO: Rename and change types of parameters
    private String mName;
    private String mNickname;
    private String mPhoneNum;
    private String mEmail;


    private TextView nameFragment;
    private TextView nicknameFragment;
    private TextView phoneNumFragment;
    private TextView emailFragment;

    public ContactDetailsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ContactDetailsFragment newInstance(String name, String phoneNum, String email, String nickname) {
        ContactDetailsFragment fragment = new ContactDetailsFragment();
        Bundle args = new Bundle();
        args.putString(NAME, name);
        args.putString(PHONENUM, phoneNum);
        args.putString(EMAIL, email);
        args.putString(NICKNAME, nickname);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mName = getArguments().getString(NAME);
            mPhoneNum = getArguments().getString(PHONENUM);
            mEmail = getArguments().getString(EMAIL);
            mNickname = getArguments().getString(NICKNAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_contact_details, container, false);
        nameFragment = view.findViewById(R.id.name_fragment);
        phoneNumFragment = view.findViewById(R.id.phone_num_fragment);
        emailFragment = view.findViewById(R.id.email_fragment);
        nicknameFragment = view.findViewById(R.id.nickname_fragment);

        nameFragment.setText(mName);
        phoneNumFragment.setText(mPhoneNum);
        emailFragment.setText(mEmail);
        nicknameFragment.setText(mNickname);
        return view;
    }
}