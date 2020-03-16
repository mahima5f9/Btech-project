package com.example.project.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.project.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.Context.USER_SERVICE;


public class Fragement_Home extends Fragment {
    TextView etPuid,Pmobile,etPName,etPEmail,etPSkills,etPphoto,etPYear,etPBacklogs,etPCGPA,etPAddress,Presume,Pdept;
    private FirebaseDatabase database;
    private DatabaseReference userRef;
    private View view;
    private static final String USER="USERS";
    String mobilenumber;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {







        view=inflater.inflate(R.layout.fragment_fragement__home,container,false);
        Intent intent=getActivity().getIntent();
        mobilenumber=intent.getStringExtra("mobilenumber");
        etPuid= view.findViewById(R.id.etPuid);
        Pmobile= view.findViewById(R.id.etPphone);
        etPName= view.findViewById(R.id.etPname);
        etPEmail= view.findViewById(R.id.etPemail);
        etPSkills= view.findViewById(R.id.Pskills);
        etPphoto= view.findViewById(R.id.etPphoto);
        etPYear= view.findViewById(R.id.Pyear);
        etPBacklogs= view.findViewById(R.id.Pbacklogs);
        etPCGPA= view.findViewById(R.id.Pcgpa);
        etPAddress= view.findViewById(R.id.Paddress);
        Presume= view.findViewById(R.id.Presume);
        Pdept= view.findViewById(R.id.Pdept);


        database=FirebaseDatabase.getInstance();
        userRef=database.getReference(USER);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    if(ds.child("mobilenumber").getValue().equals(mobilenumber)){
                        etPName.setText(ds.child("Name").getValue(String.class));
                        etPuid.setText(ds.child("uid").getValue(String.class));
                        Pmobile.setText(mobilenumber);
                        Presume.setText(ds.child("Resume").getValue(String.class));
                        etPEmail.setText(ds.child("Email").getValue(String.class));
                        etPSkills.setText(ds.child("Skills").getValue(String.class));
                        etPphoto.setText(ds.child("Photo").getValue(String.class));
                        etPYear.setText(ds.child("Year").getValue(String.class));
                        etPBacklogs.setText(ds.child("Backlogs").getValue(String.class));
                        etPCGPA.setText(ds.child("Cgps").getValue(String.class));
                        etPAddress.setText(ds.child("Address").getValue(String.class));
                        Pdept.setText(ds.child("Dept").getValue(String.class));


                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return view;
    }

}
