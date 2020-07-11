package com.example.firstproject.ui.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import com.example.firstproject.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import java.util.LinkedList;

import android.provider.ContactsContract;
import android.view.ViewGroup;

public class Tab1 extends Fragment {
    private static LinkedList<Person> mContactList = new LinkedList<>();
    private static RecyclerView mRecyclerView;
    private static ContactListAdapter mAdapter;
    public static boolean fabVisible;
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
            mContactList = getContactList();
            // Get a handle to the RecyclerView.
            mRecyclerView = view.findViewById(R.id.recyclerview);
            // Create an adapter and supply the data to be displayed.
            mAdapter = new ContactListAdapter(context, mContactList);
            // Connect the adapter with the RecyclerView.
            mRecyclerView.setAdapter(mAdapter);
            // Give the RecyclerView a default layout manager.
            mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        }
        return view;
    }

    @SuppressLint("MissingPermission")
    private LinkedList<Person> getContactList () {
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
        }
        return contactList;
    }
}