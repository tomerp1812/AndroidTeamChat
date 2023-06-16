package com.example.teamchat.adapters;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamchat.entities.Message;

import java.util.List;

public class ChatListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactViewHolder> {
    private List<Message> messages;
    private Context context;

    ChatListAdapter(List<Message> messages, Context context) {
        setContext(context);
        setMessages(messages);
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ContactListAdapter.ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactListAdapter.ContactViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
}
