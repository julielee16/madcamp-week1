package com.example.firstproject.ui.main;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.firstproject.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

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
    private Button addButton;

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
        View view = inflater.inflate(R.layout.fragment_add, container, false);
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

        addButton = view.findViewById(R.id.add_button);

        addButton.setOnClickListener(new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        try {
                            String hyphen = addPhoneNumFragment.getText().toString();
                            if(addPhoneNumFragment.getText().toString().length() == 11) {
                                hyphen = hyphen.substring(0,3) + "-" + hyphen.substring(3,7) +
                                        "-" + hyphen.substring(7,11);
                            }
                            addContact(addNameFragment.getText().toString(),
                                    hyphen,
                                    addEmailFragment.getText().toString(), addNicknameFragment.getText().toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        getParentFragmentManager().popBackStack();
                    }
                });
        return view;
    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void addContact(String name, String phone_num, String email, String nickname) throws JSONException, IOException {

        File tempFile = new File(getActivity().getExternalFilesDir(null), "contact_list.json");
        if(!tempFile.exists()) {
            tempFile.createNewFile();
            FileOutputStream tempfos = new FileOutputStream(tempFile);
            BufferedWriter tempBufferedWriter = new BufferedWriter(new OutputStreamWriter(tempfos));

            JSONArray tempPersonList = new JSONArray();

            JSONObject tempPerson = new JSONObject();
            tempPerson.put("name", name);
            tempPerson.put("phone_num", phone_num);
            tempPerson.put("email", email);
            tempPerson.put("nickname", nickname);
            tempPersonList.put(tempPerson);

            JSONObject updated = new JSONObject();
            updated.put("contact_list", tempPersonList);
            tempBufferedWriter.write(updated.toString(3));
            tempBufferedWriter.close();
            tempfos.close();
        }

        else {
            File file = new File(getActivity().getExternalFilesDir(null), "contact_list.json");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line = bufferedReader.readLine();
            while (line != null) {
                stringBuilder.append(line).append("\n");
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
            fileReader.close();

            JSONObject jsonObject = new JSONObject(stringBuilder.toString());
            JSONArray jsonPersonList = jsonObject.getJSONArray("contact_list");

            JSONObject person = new JSONObject();
            person.put("name", name);
            person.put("phone_num", phone_num);
            person.put("email", email);
            person.put("nickname", nickname);

            jsonPersonList.put(person);

            JSONObject updated = new JSONObject();
            updated.put("contact_list", jsonPersonList);

            file = new File(getActivity().getExternalFilesDir(null), "contact_list.json");
            FileOutputStream fos = new FileOutputStream(file);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fos));
            bufferedWriter.write(updated.toString(3));
            bufferedWriter.close();
            fos.close();
        }
    }
}