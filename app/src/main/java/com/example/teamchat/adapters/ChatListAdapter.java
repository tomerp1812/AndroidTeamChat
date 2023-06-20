package com.example.teamchat.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamchat.R;
import com.example.teamchat.entities.messages.Message;

import java.util.List;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.MyViewHolder> {
    private static final int VIEW_TYPE_SENDER = 0;
    private static final int VIEW_TYPE_RECEIVER = 1;
    private final LayoutInflater mInflater;
    private List<Message> messages;
    public ChatListAdapter(Context context){
        mInflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public ChatListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        //NEED TO CHANGE SENDER/RECEIVER
        View itemView = inflater.inflate(R.layout.item_receive_message, parent, false);
        return new MyViewHolder(itemView);    }

    @Override
    public void onBindViewHolder(@NonNull ChatListAdapter.MyViewHolder holder, int position) {
        Message message = messages.get(position);
        holder.msg.setText(message.getMessage());

        int viewType = getItemViewType(position);
        if (viewType == VIEW_TYPE_SENDER) {
            holder.msg.setBackgroundResource(R.drawable.bg_send_message);
        } else if (viewType == VIEW_TYPE_RECEIVER) {
            holder.msg.setBackgroundResource(R.drawable.bg_receive_message);
        }
    }


    @Override
    public int getItemCount() {
        return messages.size();
    }
    public void setMessages(List<Message> s) {
        messages = s;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        Message message = messages.get(position);
        if (message.isSender()) {
            return VIEW_TYPE_SENDER;
        } else {
            return VIEW_TYPE_RECEIVER;
        }
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView msg;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            msg = itemView.findViewById(R.id.ivProfile);
        }
    }
}