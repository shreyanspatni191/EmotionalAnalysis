package com.example.emotionalanalysis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Result extends AppCompatActivity implements View.OnClickListener {

    private Button buttonLogout, buttonRetest;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        user = FirebaseAuth.getInstance().getCurrentUser();
        buttonLogout = (Button)findViewById(R.id.buttonLogout);
        buttonRetest = (Button)findViewById(R.id.buttonRetest);

        buttonLogout.setOnClickListener(this);
        buttonRetest.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == buttonLogout){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(Result.this, MainActivity.class));
            finish();
        }
        else if(view == buttonRetest){
            String email = user.getEmail();
            Intent i = new Intent(Result.this, TestPage.class);
            Bundle bundle = new Bundle();
            bundle.putString("email",email);
            i.putExtras(bundle);
            startActivity(i);
            finish();
        }
    }
}
