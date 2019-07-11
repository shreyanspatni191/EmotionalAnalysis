package com.example.emotionalanalysis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.SyncStateContract;
//import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Retrofit retrofit;
    private EditText editTextEmail, editTextPassword;
    private TextView textViewRegister;
    private Button buttonLogin;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
// ...
// Initialize Firebase Auth

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mAuth = FirebaseAuth.getInstance();


        Typeface custom_font = Typeface.createFromAsset(getAssets(),"fonts/Lato-Light.ttf");

        editTextEmail = (EditText)findViewById(R.id.editTextEmail);
        editTextEmail.setTypeface(custom_font);

        editTextPassword = (EditText)findViewById(R.id.editTextPassword);
        editTextPassword.setTypeface(custom_font);

        textViewRegister = (TextView)findViewById(R.id.textViewRegister);
        textViewRegister.setTypeface(custom_font);

        buttonLogin = (Button)findViewById(R.id.buttonLogin);
        buttonLogin.setTypeface(custom_font);

        progressDialog = new ProgressDialog(this);

        buttonLogin.setOnClickListener(this);
        textViewRegister.setOnClickListener(this);


    }

    private void userLogin(){

        final String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        //firebase
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = mAuth.getCurrentUser();
                    if (user != null) {
                        if (user.isEmailVerified()) {
                            Toast.makeText(MainActivity.this,"Logged in successfully", Toast.LENGTH_SHORT).show();
    //                        finish();
    //                        startActivity(new Intent(getApplicationContext(), TestPage.class));
                            Intent i = new Intent(MainActivity.this, TestPage.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("email",email);
                            i.putExtras(bundle);
                            startActivity(i);
                            finish();
                        } else {
                            Toast.makeText(MainActivity.this,"Please verify your email first", Toast.LENGTH_SHORT).show();

                        }
                    }
                } else {
                    Toast.makeText(MainActivity.this,"Invalid Login Credentials", Toast.LENGTH_SHORT).show();

                }
            }
        });
        //firebase end


        /*if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.ROOT_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build();
            retrofit.create(LoginApi.class).login(email,password).enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    progressDialog.hide();
                    Toast.makeText(MainActivity.this,String.valueOf(response.body().message), Toast.LENGTH_SHORT).show();
                    if(String.valueOf(response.body().error) == "false"){

                        Intent i = new Intent(MainActivity.this, TestPage.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("email",email);
                        i.putExtras(bundle);
                        finish();
                        startActivity(i);
                    }
                    else{
//                        Log.d("haga", "onResponse:");
                        Toast.makeText(MainActivity.this,"Invalid Login Credentials", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    progressDialog.hide();
                    Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });


        }*/
    }
    @Override
    public void onClick(View view) {
        if(view == buttonLogin){
            userLogin();
        }
        else if(view == textViewRegister){
            startActivity(new Intent(getApplicationContext(),Register1.class));
        }
    }
}