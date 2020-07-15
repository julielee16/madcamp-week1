package com.example.firstproject.ui.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import com.example.firstproject.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

import android.provider.ContactsContract;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Tab1 extends Fragment {
    private static LinkedList<Person> mContactList = new LinkedList<>();
    private static RecyclerView mRecyclerView;
    private static ContactListAdapter mAdapter;
    public static boolean allowRefresh = false;
    public static boolean fabVisible;
    private FloatingActionButton fab;
    private Context context;

    public Tab1() {
        // Required empty public constructor
    }

    public static Tab1 newInstance() {
        Tab1 fragment = new Tab1();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_tab1, container, false);
        context = getActivity();

        if(context != null) {
            // Get the list of contacts from the phone
            try {
                mContactList = getContactList();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            // Get a handle to the RecyclerView.
            mRecyclerView = view.findViewById(R.id.recyclerview);
            // Create an adapter and supply the data to be displayed.
            mAdapter = new ContactListAdapter(context, mContactList);
            // Connect the adapter with the RecyclerView.
            mRecyclerView.setAdapter(mAdapter);
            // Give the RecyclerView a default layout manager.
            mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            fab = view.findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openAddFragment();
                    mAdapter.notifyDataSetChanged();
                }
            });

        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (allowRefresh) {
            allowRefresh = false;
            final FragmentTransaction ft =
                    getActivity().getSupportFragmentManager().beginTransaction();
            ft.detach(this);
            ft.attach(this);
            ft.commit();
        }
    }

    public void openAddFragment() {
        allowRefresh = true;
        AddFragment fragment = AddFragment.newInstance();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
        transaction.addToBackStack(null);
        transaction.replace(R.id.contact_info_fragment_container, fragment,
                "CONTACT_DETAILS_FRAGMENT").commit();
    }

    @SuppressLint("MissingPermission")
    private LinkedList<Person> getContactList () throws IOException, JSONException {
        LinkedList<Person> contactList = new LinkedList<Person>();

        if (context != null) {
            String email = "N/A";
            String nickname = "N/A";
            String[] PROJECTION = new String[]{
                    ContactsContract.Contacts._ID,
                    ContactsContract.Contacts.DISPLAY_NAME,
                    ContactsContract.Contacts.HAS_PHONE_NUMBER,
            };
            String SELECTION = ContactsContract.Contacts.HAS_PHONE_NUMBER + "='1'";
            Cursor contacts = context.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, PROJECTION, SELECTION, null, "UPPER(" + ContactsContract.Contacts.DISPLAY_NAME + ") ASC");


            if (contacts.getCount() > 0) {
                while (contacts.moveToNext()) {
                    Person aContact = new Person();
                    int idFieldColumnIndex = 0;
                    int nameFieldColumnIndex = 0;
                    int numberFieldColumnIndex = 0;

                    String contactId = contacts.getString(contacts.getColumnIndex(ContactsContract.Contacts._ID));

                    nameFieldColumnIndex = contacts.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME);
                    if (nameFieldColumnIndex > -1) {
                        aContact.setName(contacts.getString(nameFieldColumnIndex));
                    }

                    PROJECTION = new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER};
                    final Cursor phone = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PROJECTION, ContactsContract.Data.CONTACT_ID + "=?", new String[]{String.valueOf(contactId)}, null);
                    if (phone.moveToFirst()) {
                        while (!phone.isAfterLast()) {
                            numberFieldColumnIndex = phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                            if (numberFieldColumnIndex > -1) {
                                aContact.setPhoneNum(phone.getString(numberFieldColumnIndex));
                                phone.moveToNext();
                            }
                        }
                    }
                    phone.close();

                    Cursor emailCursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, PROJECTION, ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", new String[]{String.valueOf(contactId)}, null);

                    if (emailCursor.getCount() > 0) {
                        while (emailCursor.moveToNext()) {
                            email = emailCursor.getString(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                            aContact.setEmail(email + "\n" + aContact.getEmail());
                        }
                    }
                    emailCursor.close();

                    String where = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
                    String[] params = new String[]{String.valueOf(contactId), ContactsContract.CommonDataKinds.Nickname.CONTENT_ITEM_TYPE};
                    Cursor nicknameCursor = context.getContentResolver().query(ContactsContract.Data.CONTENT_URI, null, where, params, null);
                    while (nicknameCursor.moveToNext()) {
                        nickname = nicknameCursor.getString(nicknameCursor.getColumnIndex(ContactsContract.CommonDataKinds.Nickname.NAME));
                    /* For implementing different types of nicknames
                    String nicknameType = nicknameCursor.getString(nicknameCursor.getColumnIndex(ContactsContract.CommonDataKinds.Nickname.TYPE));
                    switch (Integer.valueOf(nicknameType)) {
                        case 1: nicknameType = "Default"; break;
                        case 2: nicknameType = "OtherName"; break;
                        case 3: nicknameType = "MaidenName"; break;
                        case 4: nicknameType = "ShortName"; break;
                        case 5: nicknameType = "Initials"; break;
                    } */
                        aContact.setNickname(nickname);
                    }
                    nicknameCursor.close();

                    contactList.add(aContact);
                }
                contacts.close();
            }

            File file = new File(context.getExternalFilesDir(null), "contact_list.json");
            if (file.exists()) {
                Log.d("d", "file exists!");
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                StringBuilder stringBuilder = new StringBuilder();
                String line = bufferedReader.readLine();
                while (line != null) {
                    stringBuilder.append(line).append("\n");
                    line = bufferedReader.readLine();
                }
                bufferedReader.close();

                JSONObject jsonObject = new JSONObject(stringBuilder.toString());
                JSONArray jsonPersonList = jsonObject.getJSONArray("contact_list");

                for (int i = 0; i < jsonPersonList.length(); i++) {
                    JSONObject personObject = jsonPersonList.getJSONObject(i);
                    Person aPerson = new Person();
                    aPerson.setName(personObject.getString("name"));
                    aPerson.setPhoneNum(personObject.getString("phone_num"));
                    aPerson.setEmail(personObject.getString("email"));
                    aPerson.setNickname(personObject.getString("nickname"));
                    contactList.add(aPerson);
                }
            }
        }
        return contactList;
    }
}