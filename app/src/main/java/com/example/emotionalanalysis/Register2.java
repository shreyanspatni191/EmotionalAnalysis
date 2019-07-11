package com.example.emotionalanalysis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
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
    private static final String TAG = "Register2";
    Retrofit retrofit;
    AutoCompleteTextView textView;
    private EditText editTextAge, editTextGender, editTextWeight, editTextHeight, editTextCountry;
    private Button buttonRegister;

    private ProgressDialog progressDialog;

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        editTextAge = (EditText)findViewById(R.id.editTextAge);
        editTextGender = (EditText)findViewById(R.id.editTextGender);
        editTextWeight = (EditText)findViewById(R.id.editTextWeight);
        editTextHeight = (EditText)findViewById(R.id.editTextHeight);

        editTextCountry = (AutoCompleteTextView)findViewById(R.id.editTextCountry);
        textView = (AutoCompleteTextView) findViewById(R.id.editTextCountry);

        intent = getIntent();

        buttonRegister = (Button)findViewById(R.id.buttonRegister);
        progressDialog =  new ProgressDialog(this);
        buttonRegister.setOnClickListener(this);
    }
    private static final String[] COUNTRIES = new String[] {"India","Norway","USA"};

    private void sendEmailVerification(){
        FirebaseUser user = mAuth.getCurrentUser();
        if(user!=null){
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(Task<Void> task) {
//                    if(task.isSuccessful()){
                        Log.d("Boom", "Boom");
//                        FirebaseAuth.getInstance().signOut();

//                    }
                    Toast.makeText(Register2.this, "Verification link sent", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void registerUser(){

        final String email = intent.getStringExtra("email");
        final String password = intent.getStringExtra("password");

        //verification
        Log.d(TAG, "registerUser: with " + email + " and " + password);
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG, "onComplete: Registered");
                if (task.isSuccessful()) {
                    sendEmailVerification();
                    storeDetails(email, password);
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                }
            }
        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Register2.this, "Email address already registered", Toast.LENGTH_SHORT).show();
                SystemClock.sleep(1000);
                startActivity(new Intent(Register2.this, MainActivity.class));
                finish();
            }
        });


        //done


//        progressDialog.setMessage("Registering User...");
//        progressDialog.show();

    }

    private void storeDetails(String email, String password) {
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
//      UserInfo userInfo = new UserInfo();
        String username = intent.getStringExtra("username");
        String firstname = intent.getStringExtra("firstname");
        String middlename = intent.getStringExtra("middlename");
        String lastname = intent.getStringExtra("lastname");


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