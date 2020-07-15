package com.example.firstproject.ui.main;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstproject.R;

import java.util.LinkedList;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactViewHolder> {
    private static final int REQUEST_CALL = 1;

    private LinkedList<Person> mContactList;
    private LayoutInflater mInflater;
    private Context context;

    public ContactListAdapter(Context context, LinkedList<Person> contactList) {
        mInflater = LayoutInflater.from(context);
        this.mContactList = contactList;
        this.context = context;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.contactlist_item, parent, false);
        return new ContactViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        String mName = mContactList.get(position).getName();
        String mPhoneNum = mContactList.get(position).getPhoneNum();
        String mEmail = mContactList.get(position).getEmail();
        String mNickname = mContactList.get(position).getNickname();
        holder.nameItemView.setText(mName);
        holder.phoneNumItemView.setText(mPhoneNum);
    }

    @Override
    public int getItemCount() {
        return mContactList.size();
    }

    class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView nameItemView;
        public final TextView phoneNumItemView;
        public final ImageView imageCall;

        public ContactListAdapter mAdapter;
        public FrameLayout fragmentContainer;
        public String phoneNum;

        public ContactViewHolder(View itemView, ContactListAdapter adapter) {
            super(itemView);
            nameItemView = itemView.findViewById(R.id.name_info);
            phoneNumItemView = itemView.findViewById(R.id.phone_info);
            imageCall = itemView.findViewById(R.id.image_call);
            fragmentContainer = (FrameLayout) itemView.findViewById(R.id.contact_info_fragment_container);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
            imageCall.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // Get the position of the item that was clicked.
            int mPosition = getLayoutPosition();
            // Use that to access the affected item in mContactList.
            Person element = mContactList.get(mPosition);

            // Depending on what was pressed (call button or not)
            if (view.getId() == R.id.image_call) {
                phoneNum = mContactList.get(mPosition).getPhoneNum();
                makePhoneCall();
            }
            else {
                // Change the word in the mContactList.
                openFragment(element.getName(), element.getPhoneNum(), element.getEmail(), element.getNickname());
            }
        }

        public void openFragment(String name, String phoneNum, String email, String nickname) {
            ContactDetailsFragment fragment = ContactDetailsFragment.newInstance(name, phoneNum, email, nickname);
            FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
            transaction.addToBackStack(null);
            transaction.add(R.id.contact_info_fragment_container, fragment, "CONTACT_DETAILS_FRAGMENT").commit();
            mAdapter.notifyDataSetChanged();
        }

        private void makePhoneCall() {
            if (phoneNum.trim().length() > 0) {
                if (ContextCompat.checkSelfPermission(context,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((AppCompatActivity)context,
                            new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                } else {
                    String dial = "tel:" + phoneNum;
                    context.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
                }
            } else {
                Toast.makeText(context, "Enter Phone Number", Toast.LENGTH_SHORT).show();
            }
        }

        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            if (requestCode == REQUEST_CALL) {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    makePhoneCall();
                } else {
                    Toast.makeText(context, "Permission DENIED", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}

