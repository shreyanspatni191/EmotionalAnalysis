package com.example.emotionalanalysis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Register2 extends AppCompatActivity implements View.OnClickListener{

    Retrofit retrofit;
    AutoCompleteTextView textView;
    private EditText editTextAge, editTextGender, editTextWeight, editTextHeight, editTextCountry;
    private Button buttonRegister;

    private ProgressDialog progressDialog;

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        mAuth = FirebaseAuth.getInstance();

        editTextAge = (EditText)findViewById(R.id.editTextAge);
        editTextGender = (EditText)findViewById(R.id.editTextGender);
        editTextWeight = (EditText)findViewById(R.id.editTextWeight);
        editTextHeight = (EditText)findViewById(R.id.editTextHeight);

        editTextCountry = (AutoCompleteTextView)findViewById(R.id.editTextCountry);
        textView = (AutoCompleteTextView) findViewById(R.id.editTextCountry);

        buttonRegister = (Button)findViewById(R.id.buttonRegister);
        progressDialog =  new ProgressDialog(this);
        buttonRegister.setOnClickListener(this);
    }
    private static final String[] COUNTRIES = new String[] {"India","Norway","USA"};

//    private void sendEmailVerification(){
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if(user!=null){
//            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
//                @Override
//                public void onComplete(Task<Void> task) {
//                    if(task.isSuccessful()){
////                        Snackbar.make(getWindow().getDecorView(), "Verify your email-id", Snackbar.LENGTH_LONG).show();
//                        FirebaseAuth.getInstance().signOut();
//                    }
//
//                }
//            });
//        }
//    }
//
//
//    private void verifyRegistration(String email, String password){
//        final ProgressDialog dialog = new ProgressDialog(this);
//        dialog.setMessage("Registering User");
//        dialog.show();
//        FirebaseApp.initializeApp(this);
//        mAuth = FirebaseAuth.getInstance();
//        databaseReference = FirebaseDatabase.getInstance().getReference();
//        Task<AuthResult> authResultTask = mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if(task.isSuccessful()){
//                    sendEmailVerification();
////                    Snackbar.make(getWindow().getDecorView(), "Registered Successfully", Snackbar.LENGTH_LONG).show();
//                    databaseReference = FirebaseDatabase.getInstance().getReference();
//                    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
////                    storageReference = firebaseStorage.getReference(user.getUid());
//
////                    final StorageReference reference = storageReference.child(user.getUid()).child(imageUri.getLastPathSegment());
////                    reference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
////                        @Override
////                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
////                            Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
////                            while (!urlTask.isSuccessful());
////                            Uri downloadUrl = urlTask.getResult();
////                            signUpData.setAvatarUrl(downloadUrl.toString());
////                            if(user!=null){
////                                databaseReference.child("UserInfo").child(user.getUid()).setValue(signUpData);
////                                dialog.dismiss();
////                                onBackPressed();
////                            }
////                        }
////                    }).addOnFailureListener(new OnFailureListener() {
////                        @Override
////                        public void onFailure(@NonNull Exception e) {
////                            dialog.dismiss();
////                        }
////                    });
//
//
//                }
//
//                else{
//                Toast.makeText(Register2.this,task.getException().toString(),Toast.LENGTH_SHORT).show();
//                dialog.dismiss();
//
//            }
//        }
//    });
//}

    private void registerUser(){
        String age = editTextAge.getText().toString().trim();
        String gender = editTextGender.getText().toString().trim();
        if(gender.contains("male"))gender = "1";
        else gender = "2";
        String weight = editTextWeight.getText().toString().trim();
        String height = editTextHeight.getText().toString().trim();
        String country = editTextCountry.getText().toString().trim();
        if(country.contains("India") || country.contains("india"))country = "1";
        else if(country.contains("USA") || country.contains("usa") || country.contains("America"))country = "2";
        else country = "3";
        Bundle bundle = getIntent().getExtras();
//      UserInfo userInfo = new UserInfo();
        String username = bundle.getString("username");
        String email = bundle.getString("email");
        String password = bundle.getString("password");
        String firstname = bundle.getString("firstname");
        String middlename = bundle.getString("middlename");
        String lastname = bundle.getString("lastname");

        //verification

//        verifyRegistration(email, password);


        //done


        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line, COUNTRIES);
        textView.setAdapter(adapter);


        if(retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.ROOT_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build();
            retrofit.create(Register2Api.class).register(username, email, password, firstname, middlename, lastname, age, gender,
                    height, weight, country).enqueue(new Callback<Register2Response>() {

                @Override
                public void onResponse(Call<Register2Response> call, Response<Register2Response> response) {
//                    Log.d("SUCCESS", "hahah ");
                    progressDialog.hide();
                    Log.d("SUCCESS", String.valueOf(response.body().message));
                    Toast.makeText(Register2.this, String.valueOf(response.body().message), Toast.LENGTH_LONG);
                }

                @Override
                public void onFailure(Call<Register2Response> call, Throwable t) {
                    Log.d("Faliure", "saddd");
                    progressDialog.hide();
                    Toast.makeText(Register2.this, t.getMessage(), Toast.LENGTH_LONG);
                }
            });

            finish();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
    }
    @Override
    public void onClick(View view) {
        if(view == buttonRegister){
            registerUser();
        }
    }
}