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
import com.example.teamchat.entities.messages.Message;

import java.util.ArrayList;
import java.util.List;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.MyViewHolder> {
    class MyViewHolder extends RecyclerView.ViewHolder{
        private final TextView msg;
        private final ImageView profilePic;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            msg = itemView.findViewById(R.id.text_send_message);
            profilePic = itemView.findViewById(R.id.image_send_message);
        }
    }
    private static final int VIEW_TYPE_SENDER = 0;
    private static final int VIEW_TYPE_RECEIVER = 1;
    private final LayoutInflater mInflater;
    private List<Message> messages;
    public ChatListAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        messages = new ArrayList<>();
    }
    @NonNull
    @Override
    public ChatListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        //NEED TO CHANGE SENDER/RECEIVER
        View itemView = inflater.inflate(R.layout.item_send_message, parent, false);
        return new MyViewHolder(itemView);    }

    @Override
    public void onBindViewHolder(@NonNull ChatListAdapter.MyViewHolder holder, int position) {
       if(messages != null){
           final Message message = messages.get(position);
           if(message.getContent() != null){
               holder.msg.setText(message.getContent());
           }else{
               holder.msg.setText("");
           }
       }

//
//        int viewType = getItemViewType(position);
//        if (viewType == VIEW_TYPE_SENDER) {
//            holder.msg.setBackgroundResource(R.drawable.bg_send_message);
//        } else if (viewType == VIEW_TYPE_RECEIVER) {
//            holder.msg.setBackgroundResource(R.drawable.bg_receive_message);
//        }
    }


    @Override
    public int getItemCount() {
        if (messages != null) {
            return messages.size();
        } else {
            return 0;
        }    }
    public void setMessages(List<Message> s) {
        messages = s;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        Message message = messages.get(position);
//        if (message.isSender()) {
            return VIEW_TYPE_SENDER;
//        } else {
//            return VIEW_TYPE_RECEIVER;
//        }
    }

}