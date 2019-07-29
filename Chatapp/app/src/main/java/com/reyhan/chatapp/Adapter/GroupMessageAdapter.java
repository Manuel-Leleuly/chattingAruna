package com.reyhan.chatapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.reyhan.chatapp.Model.AllMethods;
import com.reyhan.chatapp.Model.Message;
import com.reyhan.chatapp.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class GroupMessageAdapter extends RecyclerView.Adapter<GroupMessageAdapter.GroupMessageAdapterViewHolder>{

    private static final int LEFT_MSG = 0;
    private static final int RIGHT_MSG = 1;

    Context context;
    List<Message> messages;
    DatabaseReference reference;

    FirebaseUser firebaseUser;

    public GroupMessageAdapter(Context context, List<Message> messages, DatabaseReference reference) {
        this.context = context;
        this.messages = messages;
        this.reference = reference;
    }

    @NonNull
    @Override
    public GroupMessageAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == RIGHT_MSG) {
            View view = LayoutInflater.from(context).inflate(R.layout.chat_group_right, viewGroup, false);
            return new GroupMessageAdapterViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.chat_group, viewGroup, false);
            return new GroupMessageAdapterViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull GroupMessageAdapterViewHolder groupMessageAdapterViewHolder, int i) {
        Message message = messages.get(i);
        groupMessageAdapterViewHolder.showMessage.setText(message.getMessage());
        if(message.getName().equals(AllMethods.name)){
            groupMessageAdapterViewHolder.showUser.setText("Anda");
            groupMessageAdapterViewHolder.showMessage.setGravity(Gravity.START);
        } else {
            groupMessageAdapterViewHolder.showUser.setText(message.getName());
            groupMessageAdapterViewHolder.showMessage.setText(message.getMessage());
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy hh:mm a");
        Date date = new Date(message.getTimestamp());
        groupMessageAdapterViewHolder.timestamp.setText(sdf.format(date));
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class GroupMessageAdapterViewHolder extends RecyclerView.ViewHolder {

        TextView showMessage;
        TextView showUser;
        TextView timestamp;

        public GroupMessageAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            showUser = itemView.findViewById(R.id.show_username);
            showMessage = itemView.findViewById(R.id.show_message);
            timestamp = itemView.findViewById(R.id.timestamp);
        }
    }

    @Override
    public int getItemViewType(int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (messages.get(position).getName().equals(AllMethods.name)){
            return RIGHT_MSG;
        } else {
            return LEFT_MSG;
        }
    }
}
