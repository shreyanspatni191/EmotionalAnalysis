package com.example.emotionalanalysis;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextEmail = (EditText)findViewById(R.id.editTextEmail);
        editTextPassword = (EditText)findViewById(R.id.editTextPassword);
        textViewRegister = (TextView)findViewById(R.id.textViewRegister);

        buttonLogin = (Button)findViewById(R.id.buttonLogin);
        progressDialog = new ProgressDialog(this);

        buttonLogin.setOnClickListener(this);
        textViewRegister.setOnClickListener(this);
    }

    private void userLogin(){
        final String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        if(retrofit == null){
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
//                    else{
//                        finish();
//                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
//                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    progressDialog.hide();
                    Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });


        }
    }
    @Override
    public void onClick(View view) {
        if(view == buttonLogin){
            userLogin();
        }
        else if(view == textViewRegister){
            finish();
            startActivity(new Intent(getApplicationContext(),Register1.class));
        }
    }
}
