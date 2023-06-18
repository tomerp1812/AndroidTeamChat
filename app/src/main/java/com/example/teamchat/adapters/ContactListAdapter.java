package com.example.teamchat.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

    public ContactListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
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
            holder.username.setText(contact.getUser().getUsername());
            if(contact.getLastMsg() == null){
                holder.lastMessage.setText("");
                holder.date.setText("");
            }else{
                holder.lastMessage.setText(contact.getLastMsg().getContent());
                holder.date.setText(contact.getLastMsg().getCreated());
            }
            byte[] imageInBytes = Base64.decode(contact.getUser().getProfilePic(), Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageInBytes,0, imageInBytes.length);
            holder.profilePic.setImageBitmap(bitmap);
//            Bitmap bitmap = BitmapFactory.decodeFile(contact.getUser().getProfilePic());
//            holder.profilePic.setImageBitmap(bitmap);
        }
    }

    public void setContacts(List<Contact> s) {
        contacts = s;
        notifyDataSetChanged();
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

}
