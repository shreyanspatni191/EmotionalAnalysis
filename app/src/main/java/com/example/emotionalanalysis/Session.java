package com.example.emotionalanalysis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Session extends AppCompatActivity {

    Retrofit retrofit;
    ViewPager viewPager;
    Timer timer = new Timer();
    AtomicInteger index = new AtomicInteger(0);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        getActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);

        Bundle bundle = getIntent().getExtras();
        String email = bundle.getString("email");

        viewPager = findViewById(R.id.viewPager);
        ImageAdapter adapter = new ImageAdapter(this,email);
        viewPager.setAdapter(adapter);
//        viewPager.setVisibility(View.INVISIBLE);

        timer.scheduleAtFixedRate(new MyTimerTask(),7000,7000);
    }

    public class MyScheduledTask extends TimerTask {

        @Override
        public void run() {
            if (index.get() % 2 == 0) {

            }
        }
    }


    public class MyTimerTask extends TimerTask{

        @Override
        public void run() {
            Session.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    int turn = Session.this.viewPager.getCurrentItem();
                    try {
                        Log.d("SLEEP", "OhYeah");
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.d("ITEM", String.valueOf(turn));
                    switch (turn) {
//                        case -1:
//                            finish();
//                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        case 0:
                            Session.this.viewPager.setCurrentItem(1);
                            break;
                        case 1:
                            Session.this.viewPager.setCurrentItem(2);
                            break;
                        case 2:
                            Session.this.viewPager.setCurrentItem(3);
                            break;
                        case 3:
                            Session.this.viewPager.setCurrentItem(4);
                            break;
                        case 4:
                            Session.this.viewPager.setCurrentItem(5);
                            break;
                        case 5:
                            Session.this.viewPager.setCurrentItem(6);
                            break;
                        case 6:
                            Session.this.viewPager.setCurrentItem(7);
                            break;
                        case 7:
                            Session.this.viewPager.setCurrentItem(8);
                            break;
                        case 8:
                            Session.this.viewPager.setCurrentItem(9);
                            break;
                        case 9:
                            Session.this.viewPager.setCurrentItem(10);
                            break;
                        case 10:
                            timer.cancel();
                            timer.purge();

                            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            f.setTimeZone(TimeZone.getTimeZone("UTC"));
                            String endtime = f.format(new Date());

                            Bundle bundle = getIntent().getExtras();
                            String email = bundle.getString("email");
                            String testplace = bundle.getString("testplace");
                            String testplacedescription = bundle.getString("testplacedescription");
                            String testambience = bundle.getString("testambience");
                            String starttime = bundle.getString("starttime");
                            String watchno = bundle.getString("watchno");

                            Log.d("Time",starttime);

                            //retrofit

                            if(retrofit==null) {
                                retrofit = new Retrofit.Builder()
                                        .baseUrl(Constants.ROOT_URL)
                                        .addConverterFactory(GsonConverterFactory.create()).build();
                                retrofit.create(SessionApi.class).testInfo( email, starttime, endtime, testplace, testplacedescription,
                                        testambience, watchno).enqueue(new Callback<SessionResponse>() {

                                    @Override
                                    public void onResponse(Call<SessionResponse> call, Response<SessionResponse> response) {
//                                      Log.d("SUCCESS", "hahah ");
//                                        progressDialog.hide();
                                        Log.d("SUCCESS", String.valueOf(response.body().message));
                                        Toast.makeText(Session.this, String.valueOf(response.body().message), Toast.LENGTH_LONG);
                                    }

                                    @Override
                                    public void onFailure(Call<SessionResponse> call, Throwable t) {
                                        Log.d("Faliure", "saddd");
//                                        progressDialog.hide();
                                        Toast.makeText(Session.this, t.getMessage(), Toast.LENGTH_LONG);
                                    }
                                });

                                Log.d("DEAD", "MELA");
                                finish();
                                startActivity(new Intent(getApplicationContext(), Result.class));
                            }
                            break;
                        case 11:
                            Session.this.viewPager.setCurrentItem(12);
                            break;
                        default:
                            Session.this.viewPager.setCurrentItem(0);
                            break;
                    }
                }
            });

        }
    }
}
