package com.example.project.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.project.Login.User;
import com.example.project.Login.VolleySingleton;
import com.example.project.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private String URL= "https://us-central1-organic-duality-224412.cloudfunctions.net/api/register";
    private RequestQueue requestQueue;
    //EditText etUid,
    ImageView Ivimage;
            EditText etregister,etphoneNum,etName,etEmail,etSkills,etPhoto,etYear,etBacklogs,etCGPA,etAddress,etResume,etDept;
    private Button submit1;    //Context context;
    private String UID,PHONE;
    Button selectFile,upload;
    private FirebaseAuth mauth;
    FirebaseStorage storage;
    FirebaseDatabase database;
    String userid;
    int TAKE_IMAGE_CODE=10001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);

        storage= FirebaseStorage.getInstance();
        database=FirebaseDatabase.getInstance();
        //Ivimage=findViewById(R.id.imgProfile);
        etregister=findViewById(R.id.etRegd);
        etphoneNum=findViewById(R.id.etphoneNum);
        etName=findViewById(R.id.etName);
        etAddress=findViewById(R.id.edtAddress);
        etBacklogs=findViewById(R.id.etBacklogs);
        etCGPA=findViewById(R.id.etCGPA);
        etSkills=findViewById(R.id.etSkills);
        selectFile=findViewById(R.id.btnUpload);

        selectFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(RegisterActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
                {
                    selectPDF();
                }
                else {
                    ActivityCompat.requestPermissions(RegisterActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},9); }

            }
        });

        etYear=findViewById(R.id.etYear);
        etDept=findViewById(R.id.etDept);
        etEmail=findViewById(R.id.etEmail);
        submit1=findViewById(R.id.btnSub);
        submit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==9&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
            selectPDF();
        }
        else
            Toast.makeText(RegisterActivity.this,"please provide permission...",Toast.LENGTH_SHORT).show();
    }

    private void selectPDF() {
    }

    private void registerUser(){

        final String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final String rollno=etregister.getText().toString().trim();
        final String phonenumber = etphoneNum.getText().toString().trim();
        final String name = etName.getText().toString().trim();
        final String address = etAddress.getText().toString().trim();
        final String backlogs = etBacklogs.getText().toString().trim();
        final String cgpa = etCGPA.getText().toString().trim();
        final String skills = etSkills.getText().toString().trim();
        //final String resume = etResume.getText().toString().trim();
        //final String photo = etPhoto.getText().toString().trim();
        final String year = etYear.getText().toString().trim();
        final String department = etDept.getText().toString().trim();
        final String email = etEmail.getText().toString().trim();






        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("APP",response);
                        Toast.makeText(RegisterActivity.this,response,Toast.LENGTH_LONG).show();
                        parseData(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegisterActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("uid",userid);
                params.put("registernum",rollno);
                Log.e("APP",userid);
                params.put("phoneNumber",phonenumber);
                Log.e("APP",phonenumber);
                params.put("Name",name);
                //params.put("Photo",photo);
                params.put("Year",year);
                params.put("Dept",department);
                params.put("Email",email);
                params.put("Backlogs",backlogs);
                params.put("Cgps",cgpa);
                params.put("Skills",skills);
               // params.put("Resume",resume);
                params.put("Address",address);


                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void parseData(String response) {

       // try {
         //   JSONObject jsonObject = new JSONObject(response);
           // if (jsonObject.getString("status").equals("true")) {
             //   JSONArray dataArray = jsonObject.getJSONArray("data");
               // for (int i = 0; i < dataArray.length(); i++) {

                  //  JSONObject dataobj = dataArray.getJSONObject(i);
                    //UID = dataobj.getString("uid");
                    //PHONE = dataobj.getString("phoneNumber");
                //}

                Intent intent = new Intent(RegisterActivity.this,ProfileActivity.class);
                startActivity(intent);
           // }
       // } catch (JSONException e) {
         //   e.printStackTrace();
        //}

    }

    public void handleImageClick(View vie018w) {
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getPackageManager())!=null){
            startActivityForResult(intent,TAKE_IMAGE_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==TAKE_IMAGE_CODE){
            switch (resultCode){
                case RESULT_OK:
                    Bitmap bitmap=(Bitmap)data.getExtras().get("data");
                    Ivimage.setImageBitmap(bitmap);
                    handleUpload(bitmap);



            }
        }
    }

    private void handleUpload(Bitmap bitmap){
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);

        StorageReference reference=FirebaseStorage.getInstance().getReference()
                .child("profileImages")
                .child(userid+".jpeg");
        reference.putBytes(baos.toByteArray())
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("APP","onFailure:",e.getCause());
                    }
                });
    }

    private void getDownloadUrl(StorageReference reference){
        reference.getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.d("APP","OnSuccess"+uri);
                    }
                });
    }

    private void setUserProfileUrl(Uri uri){
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest request=new UserProfileChangeRequest.Builder()
                .setPhotoUri(uri)
                .build();
        user.updateProfile(request)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(RegisterActivity.this,"updated successfully",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterActivity.this,"profile image failed",Toast.LENGTH_SHORT).show();
                    }
                });
    }
}




