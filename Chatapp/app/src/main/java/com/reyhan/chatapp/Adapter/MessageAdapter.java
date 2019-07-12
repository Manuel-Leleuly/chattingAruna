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
import com.reyhan.chatapp.Model.User;
import com.reyhan.chatapp.R;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>{

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;

    private Context context;
    private List<Chat> chat;
    private String imageurl;

    FirebaseUser firebaseUser;

    public MessageAdapter(Context context, List<Chat> chat, String imageurl) {
        this.context = context;
        this.chat = chat;
        this.imageurl = imageurl;
    }

    //tampilan akan berdasar pada layout user yang dibuat (binding)
    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(i == MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_right, viewGroup, false);
            return new MessageAdapter.ViewHolder(view);
        }
        else{
            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_left, viewGroup,false);
            return new MessageAdapter.ViewHolder(view);
        }
    }

    //tarik data dari db dan set dilayout
    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder viewHolder, int i) {
        Chat pesan = chat.get(i);
        viewHolder.show_message.setText(pesan.getMessage());
        if(imageurl.equals("default")){
            viewHolder.profile.setImageResource(R.drawable.user);
        }
        else{
            Glide.with(context).load(imageurl).into(viewHolder.profile);
        }
    }

    //memunculkan user sebanyak data didatabase
    @Override
    public int getItemCount() {
        return chat.size();
    }

    //binding data di db dan di layout
    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView show_message;
        private ImageView profile;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);

            show_message = itemView.findViewById(R.id.show_message);
            profile = itemView.findViewById(R.id.userimage);

        }
    }

    @Override
    public int getItemViewType(int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(chat.get(position).getSender().equals(firebaseUser.getUid())){
            return MSG_TYPE_RIGHT;
        }
        else{
            return MSG_TYPE_LEFT;
        }
    }
}
