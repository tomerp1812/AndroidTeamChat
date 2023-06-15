package com.example.teamchat.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamchat.R;
import com.example.teamchat.entities.Contact;

import java.util.List;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactViewHolder> {
     private List<Contact> contacts;
     private Context context;

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public ContactListAdapter(List<Contact> contacts, Context context) {
        this.contacts = contacts;
        this.context = context;
    }

    @NonNull
    @Override
    public ContactListAdapter.ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item,parent,false);
        return new ContactListAdapter.ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactListAdapter.ContactViewHolder holder, int position) {
        Contact contact = contacts.get(position);
        holder.username.setText(contact.getUsername());
        holder.lastMessage.setText(contact.getLastMessage());
        holder.profilePic.setImageResource(contact.getProfilePic());
        holder.date.setText(contact.getDate());


    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }


    class ContactViewHolder extends  RecyclerView.ViewHolder{
        ImageView profilePic;
        TextView username;
        TextView lastMessage;
        TextView date;



        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            profilePic = itemView.findViewById(R.id.ivProfile);
            username = itemView.findViewById(R.id.tvUsername);
            lastMessage = itemView.findViewById(R.id.tvLastMsg);
            date = itemView.findViewById(R.id.tvTime);
        }
    }
}
