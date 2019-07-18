package com.reyhan.chatapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.reyhan.chatapp.MessageActivity;
import com.reyhan.chatapp.Model.Chat;
import com.reyhan.chatapp.Model.User;
import com.reyhan.chatapp.R;

import org.w3c.dom.Text;

import java.util.List;

//buat adapter untuk menampilkan user
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{

    //set adapter
    private Context context;
    private List<User> users;
    private boolean isChat;

    String theLastMessage;

    int jumlah_pesan_unread;

    public UserAdapter(Context context, List<User> users, boolean isChat) {
        this.context = context;
        this.users = users;
        this.isChat = isChat;
    }

    //tampilan akan berdasar pada layout user yang dibuat (binding)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.user, viewGroup, false);
        return new UserAdapter.ViewHolder(view);
    }

    //tarik data dari db dan set dilayout
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final User user = users.get(i);
        viewHolder.username.setText(user.getUsername());
        if (user.getImageURL().equals("default")){
            viewHolder.profile.setImageResource(R.drawable.user);
        } else {
            Glide.with(context).load(user.getImageURL()).into(viewHolder.profile);
        }

        if (isChat){
            lastMessage(user.getId(), viewHolder.message, viewHolder.notif_each_user);
        } else {
            viewHolder.message.setVisibility(View.GONE);
        }

        //status online dan offline
        if (isChat){
            if (user.getStatus().equals("online")){
                viewHolder.img_on.setVisibility(View.VISIBLE);
                viewHolder.img_off.setVisibility(View.GONE);
            } else {
                viewHolder.img_on.setVisibility(View.GONE);
                viewHolder.img_off.setVisibility(View.VISIBLE);
            }
        } else {
            viewHolder.img_on.setVisibility(View.GONE);
            viewHolder.img_off.setVisibility(View.GONE);
        }

        //respon ketika kontak di klik
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent messageintent = new Intent(context, MessageActivity.class);
                messageintent.putExtra("userid", user.getId());
                context.startActivity(messageintent);
            }
        });
    }

    //memunculkan user sebanyak data didatabase
    @Override
    public int getItemCount() {
        return users.size();
    }

    //binding data di db dan di layout
    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView username;
        private ImageView profile;
        private ImageView img_on;
        private ImageView img_off;
        public TextView message;
        public TextView notif_each_user;


        private ViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.usertextview);
            profile = itemView.findViewById(R.id.userimage);
            img_on = itemView.findViewById(R.id.imageOn);
            img_off = itemView.findViewById(R.id.imageOff);
            message = itemView.findViewById(R.id.lastMessage);
            notif_each_user = itemView.findViewById(R.id.notif_each_user);

        }
    }

    private void lastMessage (final String userid, final TextView message, final TextView notif_each_user) {
        theLastMessage = "default";
        jumlah_pesan_unread = 0;
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chats");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Chat chat = snapshot.getValue(Chat.class);
                    if(firebaseUser != null) {
                        if (chat.getReceiver().equals(firebaseUser.getUid()) && chat.getSender().equals(userid)
                                || chat.getReceiver().equals(userid) && chat.getSender().equals(firebaseUser.getUid())) {
                            theLastMessage = chat.getMessage();
                            if (chat.getSender().equals(userid)) {
                                if (!chat.isIsseen()) {
                                    jumlah_pesan_unread++;
                                }
                            }
                        }
                    }
                }

                switch (theLastMessage){
                    case "default":
                        message.setText("");
                        break;

                    default:
                        message.setText(theLastMessage);
                        if(jumlah_pesan_unread > 0) {
                            notif_each_user.setText("(" + jumlah_pesan_unread + ")");
                        }
                        else if (jumlah_pesan_unread > 99){
                            notif_each_user.setText("(99+)");
                        }
                        break;

                }
                theLastMessage = "default";
                jumlah_pesan_unread = 0;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}