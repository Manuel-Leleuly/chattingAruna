package com.reyhan.chatapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.reyhan.chatapp.MessageActivity;

import com.reyhan.chatapp.Model.Chat;
import com.reyhan.chatapp.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>{

    //set adapter
    public static final int LEFT_MSG = 0;
    public static final int RIGHT_MSG = 1;

    private Context context;
    private List<Chat> chats;
    private String imageURL;

    FirebaseUser firebaseUser;

    public MessageAdapter(Context context, List<Chat> chats, String imageURL) {
        this.context = context;
        this.chats = chats;
        this.imageURL = imageURL;
    }

    //tampilan akan berdasar pada layout user yang dibuat (binding)
    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == RIGHT_MSG) {
            View view = LayoutInflater.from(context).inflate(R.layout.chat_right_view, viewGroup, false);
            return new MessageAdapter.ViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.chat_left_view, viewGroup, false);
            return new MessageAdapter.ViewHolder(view);
        }
    }

    //tarik data dari db dan set dilayout
    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder viewHolder, int i) {

        Chat chat = chats.get(i);

        viewHolder.message.setText(chat.getMessage());
        if (imageURL.equals("default")){
            viewHolder.profile.setImageResource(R.drawable.user);
        } else {
            Glide.with(context).load(imageURL).into(viewHolder.profile);
        }

        if(chat.isIsseen()){
            viewHolder.seen.setText("Dilihat");
        }
        else{
            viewHolder.seen.setText("Terkirim");
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy hh:mm a");
        Date date = new Date(chat.getTimestamp());
        viewHolder.timestamp.setText(sdf.format(date));

    }

    //memunculkan user sebanyak data didatabase
    @Override
    public int getItemCount() {
        return chats.size();
    }

    //binding data di db dan di layout
    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView message;
        private ImageView profile;

        public TextView seen;
        public TextView timestamp;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);

            message = itemView.findViewById(R.id.show_message);
            profile = itemView.findViewById(R.id.image_profile);
            seen = itemView.findViewById(R.id.text_seen);
            timestamp = itemView.findViewById(R.id.timestamp);
        }
    }

    @Override
    public int getItemViewType(int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (chats.get(position).getSender().equals(firebaseUser.getUid())){
            return RIGHT_MSG;
        } else {
            return LEFT_MSG;
        }
    }
}
