package com.example.emotionalanalysis;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Register1 extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextUsername, editTextPassword, editTextFirstname, editTextLastname, editTextMiddlename, editTextEmail;
    private Button buttonNext;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);

        Typeface custom_font = Typeface.createFromAsset(getAssets(),"fonts/Lato-Light.ttf");


        editTextUsername = (EditText)findViewById(R.id.editTextUsername);
        editTextUsername.setTypeface(custom_font);

        editTextPassword = (EditText)findViewById(R.id.editTextPassword);
        editTextPassword.setTypeface(custom_font);

        editTextFirstname = (EditText)findViewById(R.id.editTextFirstname);
        editTextFirstname.setTypeface(custom_font);

        editTextMiddlename = (EditText)findViewById(R.id.editTextMiddlename);
        editTextMiddlename.setTypeface(custom_font);

        editTextLastname = (EditText)findViewById(R.id.editTextLastname);
        editTextLastname.setTypeface(custom_font);

        editTextEmail = (EditText)findViewById(R.id.editTextEmail);
        editTextEmail.setTypeface(custom_font);

        buttonNext = (Button)findViewById(R.id.buttonNext);
        buttonNext.setTypeface(custom_font);

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
        if(String.valueOf(middlename ).equals(""))middlename = "-";

        UserInfo userInfo = new UserInfo(username, email, password, firstname, middlename, lastname);
//        Log.d("HHHHH",userInfo.getUsername().toString().trim());

        Intent i = new Intent(this, Register2.class);
//        Bundle bundle = new Bundle();
        i.putExtra("username", username);
        i.putExtra("email",email);
        i.putExtra("password",password);
        i.putExtra("firstname",firstname);
        i.putExtra("middlename",middlename);
        i.putExtra("lastname",lastname);

//        Add the bundle to the intent
//        i.putExtras(bundle);

//        Fire that second activity
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