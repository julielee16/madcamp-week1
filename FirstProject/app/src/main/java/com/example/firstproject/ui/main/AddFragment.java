package com.example.firstproject.ui.main;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.firstproject.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String NAME = "name";
    private static final String PHONENUM = "phone_num";
    private static final String EMAIL = "email";
    private static final String NICKNAME = "nickname";

    // TODO: Rename and change types of parameters
    private String mName;
    private String mPhoneNum;
    private String mEmail;
    private String mNickname;

    private EditText addNameFragment;
    private EditText addPhoneNumFragment;
    private EditText addEmailFragment;
    private EditText addNicknameFragment;

    public AddFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AddFragment newInstance() {
        AddFragment fragment = new AddFragment();
        Bundle args = new Bundle();
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
        View view =inflater.inflate(R.layout.fragment_add, container, false);
        addNameFragment = view.findViewById(R.id.add_name_fragment);
        addPhoneNumFragment = view.findViewById(R.id.add_phone_num_fragment);
        addEmailFragment = view.findViewById(R.id.add_email_fragment);
        addNicknameFragment = view.findViewById(R.id.add_nickname_fragment);

        addNameFragment.setHint("Name");
        addPhoneNumFragment.setHint("Phone Number");
        addEmailFragment.setHint("Email Address");
        addNicknameFragment.setHint("Nickname");

        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(addNameFragment.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
        inputManager.hideSoftInputFromWindow(addPhoneNumFragment.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
        inputManager.hideSoftInputFromWindow(addEmailFragment.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
        inputManager.hideSoftInputFromWindow(addNicknameFragment.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);


        //addNameFragment.getText();
        //addPhoneNumFragment.getText();
        //addEmailFragment.getText();
        //addNicknameFragment.getText();

        return view;
    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}