package com.example.project.Activities;
import org.json.JSONObject;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.project.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {
    String UID,PHONE;
    private String URL= "https://us-central1-organic-duality-224412.cloudfunctions.net/api/profile";
   private RequestQueue requestQueue;
    TextView etPuid,Pmobile,etPName,etPEmail,etPSkills,etPphoto,etPYear,etPBacklogs,etPCGPA,etPAddress,Presume,Pdept;
    String rollno,phone,name,email,skills,photo,year,backlogs,cgpa,address,resume,dept;
    private FirebaseDatabase database;
    private DatabaseReference userRef;
    private View view;
   FirebaseAuth fauth;
   FirebaseFirestore fstore;
    String mobilenumber;
    String userid;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Intent intent=getIntent();
        mobilenumber=intent.getStringExtra("mobilenumber");
        etPuid= findViewById(R.id.etPuid);
        Pmobile= findViewById(R.id.etPphone);
        etPName= findViewById(R.id.etPname);
        etPEmail= findViewById(R.id.etPemail);
        etPSkills= findViewById(R.id.Pskills);
        etPphoto= findViewById(R.id.etPphoto);
        etPYear= findViewById(R.id.Pyear);
        etPBacklogs= findViewById(R.id.Pbacklogs);
        etPCGPA=findViewById(R.id.Pcgpa);
        etPAddress=findViewById(R.id.Paddress);
        Presume= findViewById(R.id.Presume);
        Pdept= findViewById(R.id.Pdept);

        profileUser();


    }
    private void profileUser(){


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(ProfileActivity.this,response,Toast.LENGTH_LONG).show();
                        Log.e("APP","ji");
                        Log.e ( "APP", "" + response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            phone=jsonObject.optString("phoneNumber");
                            name=jsonObject.optString("Name");
                            email=jsonObject.optString("Email");
                            rollno=jsonObject.optString("registernum");
                            skills=jsonObject.optString("Skills");
                            year=jsonObject.optString("Year");
                            backlogs=jsonObject.optString("Backlogs");
                            cgpa=jsonObject.optString("Cgps");
                            address=jsonObject.optString("Address");
                            dept=jsonObject.optString("Dept");


                        } catch (JSONException e) {
                          e.printStackTrace();
                            Log.e("APP","Error"+e.getMessage());

                        }
                        Pmobile.setText(phone);
                        etPName.setText(name);etPEmail.setText(email);etPSkills.setText(skills);etPYear.setText(year);etPBacklogs.setText(backlogs);etPCGPA.setText(cgpa);
                        etPAddress.setText(address);Pdept.setText(dept);etPuid.setText(rollno);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ProfileActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                userid=FirebaseAuth.getInstance().getCurrentUser().getUid();
                Log.e("APP",userid);
                params.put("uid",userid);
                params.put("phoneNumber",mobilenumber);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}
