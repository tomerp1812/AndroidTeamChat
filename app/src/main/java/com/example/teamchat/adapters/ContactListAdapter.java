package com.example.teamchat.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamchat.R;
import com.example.teamchat.entities.contacts.Contact;

import java.util.ArrayList;
import java.util.List;
public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactViewHolder> {

    class ContactViewHolder extends RecyclerView.ViewHolder {
        private final ImageView profilePic;
        private final TextView username;
        private final TextView lastMessage;
        private final TextView date;


        private ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            profilePic = itemView.findViewById(R.id.ivProfile);
            username = itemView.findViewById(R.id.tvUsername);
            lastMessage = itemView.findViewById(R.id.tvLastMsg);
            date = itemView.findViewById(R.id.tvTime);
        }
    }

    private final LayoutInflater mInflater;
    private List<Contact> contacts;

    private List<Contact> filteredContactList;

    private List<Contact> tempContacts;

    public ContactListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        filteredContactList = new ArrayList<>();
        contacts = new ArrayList<>();
        tempContacts = new ArrayList<>();
    }


    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.contact_item, parent, false);
        return new ContactViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        if (contacts != null) {
            final Contact contact = contacts.get(position);
            if(contact.getUser() != null){
                holder.username.setText(contact.getUser().getUsername());
                byte[] imageInBytes = Base64.decode(contact.getUser().getProfilePic(), Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageInBytes,0, imageInBytes.length);
                holder.profilePic.setImageBitmap(bitmap);
            }else{
                holder.username.setText("");
                holder.profilePic.setImageBitmap(null);
            }

            if(contact.getLastMessage() == null){
                holder.lastMessage.setText("");
                holder.date.setText("");
            }else{
                holder.lastMessage.setText(contact.getLastMessage().getContent());
                String date=contact.getLastMessage().getCreated();
                String time = date.substring(11, 16);
                holder.date.setText(time);

            }
        }
    }

    public void setContacts(List<Contact> s) {
        contacts = s;
        tempContacts.clear();
        tempContacts.addAll(s);
//        filterContacts("");
        notifyDataSetChanged();
    }

    public void filterContacts(String searchText) {
        filteredContactList.clear();
        if (TextUtils.isEmpty(searchText)) {
            contacts.clear();
            contacts.addAll(tempContacts);
            filteredContactList.addAll(tempContacts);
        } else {
            for (Contact contact : tempContacts) {
                // Customize the search logic based on your Contact model
                if (contact.getUser().getUsername().toLowerCase().contains(searchText.toLowerCase())) {
                    filteredContactList.add(contact);
                }
            }
        }
        contacts.clear();
        contacts.addAll(filteredContactList);
        notifyDataSetChanged();
        //contacts = tempContacts;
    }

    @Override
    public int getItemCount() {
        if (contacts != null) {
            return contacts.size();
        } else {
            return 0;
        }
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public Contact getContactByPosition(int position){
        return tempContacts.get(position);
    }

}
