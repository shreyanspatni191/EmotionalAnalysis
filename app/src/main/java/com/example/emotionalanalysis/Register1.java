package com.example.emotionalanalysis;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Register1 extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextUsername, editTextPassword, editTextFirstname, editTextLastname, editTextMiddlename, editTextEmail;
    private Button buttonNext;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);

        editTextUsername = (EditText)findViewById(R.id.editTextUsername);
        editTextPassword = (EditText)findViewById(R.id.editTextPassword);
        editTextFirstname = (EditText)findViewById(R.id.editTextFirstname);
        editTextMiddlename = (EditText)findViewById(R.id.editTextMiddlename);
        editTextLastname = (EditText)findViewById(R.id.editTextLastname);
        editTextEmail = (EditText)findViewById(R.id.editTextEmail);

        buttonNext = (Button)findViewById(R.id.buttonNext);
        progressDialog = new ProgressDialog(this);

        buttonNext.setOnClickListener(this);
    }

    private void registerOne(){

        String username = editTextUsername.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String firstname = editTextFirstname.getText().toString().trim();
        String middlename = editTextMiddlename.getText().toString().trim();
        String lastname = editTextLastname.getText().toString().trim();

        UserInfo userInfo = new UserInfo(username, email, password, firstname, middlename, lastname);
//        Log.d("HHHHH",userInfo.getUsername().toString().trim());

        Intent i = new Intent(this, Register2.class);
        Bundle bundle = new Bundle();

        bundle.putString("username", username);
        bundle.putString("email",email);
        bundle.putString("password",password);
        bundle.putString("firstname",firstname);
        bundle.putString("middlename",middlename);
        bundle.putString("lastname",lastname);

//        Add the bundle to the intent
        i.putExtras(bundle);

//        Fire that second activity
        finish();
        startActivity(i);
//        startActivity(new Intent(getApplicationContext(),Register2.class));

    }
    @Override
    public void onClick(View view) {
        if(view == buttonNext){
            registerOne();

        }
    }
}
