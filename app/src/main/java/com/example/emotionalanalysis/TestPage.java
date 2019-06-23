package com.example.emotionalanalysis;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import retrofit2.Retrofit;

public class TestPage extends AppCompatActivity implements View.OnClickListener{

    Retrofit retrofit;
    private EditText editTextTestPlace, editTextTestPlaceDescription, editTextTestAmbience, editTextWatchNo;
    private Button buttonStartTest;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_page);

        editTextTestPlace = (EditText)findViewById(R.id.editTextTestPlace);
        editTextTestPlaceDescription = (EditText)findViewById(R.id.editTextTestPlaceDescription);
        editTextTestAmbience = (EditText)findViewById(R.id.editTextTestAmbience);
        editTextWatchNo = (EditText)findViewById(R.id.editTextWatchNo);

        buttonStartTest = (Button)findViewById(R.id.buttonStartTest);
        progressDialog = new ProgressDialog(this);

        buttonStartTest.setOnClickListener(this);
    }

    private void testInfo(){
        String testplace = editTextTestPlace.getText().toString().trim();
        String testplacedescription = editTextTestPlaceDescription.getText().toString().trim();
        String testambience = editTextTestAmbience.getText().toString().trim();
        String watchno = editTextWatchNo.getText().toString().trim();
        Bundle bundle = getIntent().getExtras();
        String email = bundle.getString("email");

        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        f.setTimeZone(TimeZone.getTimeZone("UTC"));

        String starttime = f.format(new Date());

        Intent i = new Intent(this, Session.class);
        Bundle goBundle = new Bundle();

        goBundle.putString("email",email);
        goBundle.putString("testplace",testplace);
        goBundle.putString("testplacedescription",testplacedescription);
        goBundle.putString("testambience",testambience);
        goBundle.putString("watchno",watchno);
        goBundle.putString("starttime",starttime);

        i.putExtras(goBundle);
        finish();
        startActivity(i);

    }
    @Override
    public void onClick(View view) {
        if(view == buttonStartTest){
            testInfo();
        }
    }
}
