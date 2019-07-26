package com.reyhan.chatapp;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.reyhan.chatapp.Adapter.GroupMessageAdapter;
import com.reyhan.chatapp.Model.AllMethods;
import com.reyhan.chatapp.Model.Message;
import com.reyhan.chatapp.Model.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class GroupChatActivity extends AppCompatActivity {

    private EditText messageInput;

    DatabaseReference reference;
    private FirebaseDatabase database;
    private FirebaseAuth auth;

    private FirebaseUser firebaseUser;

    GroupMessageAdapter groupMessageAdapter;
    User user;
    List<Message> messages;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);

        String groupName = getIntent().getExtras().get("groupName").toString();
        reference = FirebaseDatabase.getInstance().getReference("Groups").child(groupName);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(groupName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        initializeFields();

    }

    //binding
    private void initializeFields() {
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        user = new User();
        recyclerView = findViewById(R.id.recView);
        messageInput = findViewById(R.id.inputMessage);
        ImageView send = findViewById(R.id.sendGroupButton);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(messageInput.getText().toString())){
                    HashMap<String,Object> hashMap = new HashMap<>();
                    hashMap.put("message", messageInput.getText().toString());
                    hashMap.put("name", user.getUsername());
                    hashMap.put("timestamp", ServerValue.TIMESTAMP);
                    messageInput.setText("");
                    reference.push().setValue(hashMap);
                } else {
                    Toast.makeText(GroupChatActivity.this, "Text tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }
            }
        });

        messages = new ArrayList<>();
    }


    @Override
    protected void onStart() {
        super.onStart();

        final FirebaseUser currentuser = auth.getCurrentUser();

        user.setId(currentuser.getUid());
        user.setUsername(currentuser.getDisplayName());

        database.getReference("Users").child(currentuser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                user.setId(currentuser.getUid());
                AllMethods.name = user.getUsername();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Message message = dataSnapshot.getValue(Message.class);
                message.setKey(dataSnapshot.getKey());
                messages.add(message);
                displayMessages(messages);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Message message = dataSnapshot.getValue(Message.class);
                message.setKey(dataSnapshot.getKey());

                List<Message> newMessages = new ArrayList<Message>();
                for (Message m : messages){
                    if (m.getKey().equals(message.getKey())){
                        newMessages.add(message);
                    } else {
                        newMessages.add(m);
                    }
                }

                messages = newMessages;
                displayMessages(messages);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Message message = dataSnapshot.getValue(Message.class);
                message.setKey(dataSnapshot.getKey());

                List<Message> newMessages = new ArrayList<Message>();
                for (Message m : messages){
                    if (!m.getKey().equals(message.getKey())){
                        newMessages.add(m);
                    }
                }

                messages = newMessages;
                displayMessages(messages);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        messages = new ArrayList<>();
    }

    private void displayMessages(List<Message> messages) {
        recyclerView.setLayoutManager(new LinearLayoutManager(GroupChatActivity.this));
        groupMessageAdapter = new GroupMessageAdapter(GroupChatActivity.this, messages, reference);
        recyclerView.setAdapter(groupMessageAdapter);

    }
}
