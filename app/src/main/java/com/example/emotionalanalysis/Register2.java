package com.example.emotionalanalysis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class Register2 extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private static final String TAG = "Register2";
    Retrofit retrofit;
    AutoCompleteTextView textView;
    private EditText editTextAge, editTextWeight, editTextHeight;
    private Button buttonRegister;
    private Spinner editTextGender,editTextCountry;
    private ProgressDialog progressDialog;

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        Typeface custom_font = Typeface.createFromAsset(getAssets(),"fonts/Lato-Light.ttf");

        final String[] paths = {"Male","Female","Other"};
        final String[] paths1 = {"India","USA","Norway","Other"};
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        editTextAge = (EditText)findViewById(R.id.editTextAge);
        editTextAge.setTypeface(custom_font);
//        editTextGender = (EditText)findViewById(R.id.editTextGender);

        editTextGender = (Spinner) findViewById(R.id.editTextGender);
//        editTextTestPlace.setTypeface(custom_font);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Register2.this,
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editTextGender.setAdapter(adapter);
        editTextGender.setOnItemSelectedListener(this);

        editTextWeight = (EditText)findViewById(R.id.editTextWeight);
        editTextWeight.setTypeface(custom_font);

        editTextHeight = (EditText)findViewById(R.id.editTextHeight);
        editTextHeight.setTypeface(custom_font);

        editTextCountry = (Spinner)findViewById(R.id.editTextCountry);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(Register2.this,
                android.R.layout.simple_spinner_item,paths1);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editTextCountry.setAdapter(adapter1);
        editTextCountry.setOnItemSelectedListener(this);
//        textView = (AutoCompleteTextView) findViewById(R.id.editTextCountry);

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
                SystemClock.sleep(3000);
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
        String gender = editTextGender.getSelectedItem().toString().trim();
        if(gender.contains("Male"))gender = "1";
        else if(gender.contains("Female"))gender = "2";
        else gender = "3";
        String weight = editTextWeight.getText().toString().trim();
        String height = editTextHeight.getText().toString().trim();
        String country = editTextCountry.getSelectedItem().toString().trim();
        if(country.contains("India"))country = "1";
        else if(country.contains("USA"))country = "2";
        else if(country.contains("Norway"))country = "3";
        else country = "4";
//      UserInfo userInfo = new UserInfo();
        String username = intent.getStringExtra("username");
        String firstname = intent.getStringExtra("firstname");
        String middlename = intent.getStringExtra("middlename");
        String lastname = intent.getStringExtra("lastname");


//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line, COUNTRIES);
//        textView.setAdapter(adapter);


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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}