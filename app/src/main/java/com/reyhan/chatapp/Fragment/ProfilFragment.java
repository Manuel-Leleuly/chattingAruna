package com.reyhan.chatapp.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.reyhan.chatapp.Help.Advise;
import com.reyhan.chatapp.Help.Question;
import com.reyhan.chatapp.R;

public class ProfilFragment extends Fragment {


    public ProfilFragment() {
        // Required empty public constructor
    }

    private LinearLayout advise, question;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profil, container, false);

        advise = view.findViewById(R.id.line1);
        question = view.findViewById(R.id.line2);

        advise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Advise.class);
                startActivity(intent);
            }
        });

        question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newintent = new Intent(getActivity(), Question.class);
                startActivity(newintent);
            }
        });

        return view;
    }

}
