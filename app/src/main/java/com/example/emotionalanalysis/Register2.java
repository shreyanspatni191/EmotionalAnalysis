package com.example.emotionalanalysis;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
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

public class Register2 extends AppCompatActivity implements View.OnClickListener{

    Retrofit retrofit;
    AutoCompleteTextView textView;
    private EditText editTextAge, editTextGender, editTextWeight, editTextHeight, editTextCountry;
    private Button buttonRegister;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

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

    private void registerUser(){
        String age = editTextAge.getText().toString().trim();
        String gender = editTextGender.getText().toString().trim();
        String weight = editTextWeight.getText().toString().trim();
        String height = editTextHeight.getText().toString().trim();
        String country = editTextCountry.getText().toString().trim();
        Bundle bundle = getIntent().getExtras();
//      UserInfo userInfo = new UserInfo();
        String username = bundle.getString("username");
        String email = bundle.getString("email");
        String password = bundle.getString("password");
        String firstname = bundle.getString("firstname");
        String middlename = bundle.getString("middlename");
        String lastname = bundle.getString("lastname");

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