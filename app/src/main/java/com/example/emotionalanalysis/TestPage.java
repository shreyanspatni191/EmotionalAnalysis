package com.example.emotionalanalysis;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import retrofit2.Retrofit;

public class TestPage extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    Retrofit retrofit;
    private EditText editTextTestPlaceDescription, editTextTestAmbience, editTextWatchNo;
    private Spinner editTextTestPlace;
    private Button buttonStartTest;
    FirebaseUser user;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_page);
        user = FirebaseAuth.getInstance().getCurrentUser();

        final String[] paths = {"School/College","Home","Cafe","Other"};

        Typeface custom_font = Typeface.createFromAsset(getAssets(),"fonts/Lato-Light.ttf");

        editTextTestPlace = (Spinner) findViewById(R.id.editTextTestPlace);
//        editTextTestPlace.setTypeface(custom_font);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(TestPage.this,
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editTextTestPlace.setAdapter(adapter);
        editTextTestPlace.setOnItemSelectedListener(this);

        editTextTestPlaceDescription = (EditText)findViewById(R.id.editTextTestPlaceDescription);
        editTextTestPlaceDescription.setTypeface(custom_font);

        editTextTestAmbience = (EditText)findViewById(R.id.editTextTestAmbience);
        editTextTestAmbience.setTypeface(custom_font);

        editTextWatchNo = (EditText)findViewById(R.id.editTextWatchNo);
        editTextWatchNo.setTypeface(custom_font);

        buttonStartTest = (Button)findViewById(R.id.buttonNext);
        progressDialog = new ProgressDialog(this);

        buttonStartTest.setOnClickListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        switch (position) {
            case 0:
                // Whatever you want to happen when the first item gets selected
                break;
            case 1:
                // Whatever you want to happen when the second item gets selected
                break;
            case 4:
                Log.d("other", "aagya");
                editTextTestPlaceDescription.setEnabled(true);
                break;

        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {
        // TODO Auto-generated method stub
    }

    private void testInfo(){
        String testplace = editTextTestPlace.getSelectedItem().toString().trim();

        if(testplace.contains("Other")){
            editTextTestPlaceDescription.setEnabled(true);
        }
        else{
            editTextTestPlaceDescription.setEnabled(false);
        }

        String testplacedescription = editTextTestPlaceDescription.getText().toString().trim();

        if(String.valueOf(testplacedescription ).equals(""))testplacedescription = "-";
        Log.d("TDP",String.valueOf(testplacedescription));

        String testambience = editTextTestAmbience.getText().toString().trim();
        String watchno = editTextWatchNo.getText().toString().trim();
        Bundle bundle = getIntent().getExtras();
        String email = bundle.getString("email");

        if(user != null){
            email = user.getEmail();
        }
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        f.setTimeZone(TimeZone.getTimeZone("UTC"));

        String starttime = f.format(new Date());

        Intent i = new Intent(this, SliderActivity.class);
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