package com.example.emotionalanalysis;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class SliderActivity extends AppCompatActivity {
    private static final String TAG = "SliderActivity";
    Retrofit retrofit;
    Timer timer = new Timer();
    AtomicInteger index = new AtomicInteger(0);
    StorageReference storageRef;
    Random random = new Random();
    public static final String IMAGE_PREFIX = "image";
    public static final String NEUTRAL_IMAGE_NAME = "neutral.jpg";
    ImageView imageHolder;
    Resources res;
    HandlerThread myHandlerThread = new HandlerThread(TAG);

    private static class MyHandler extends Handler {
        MyHandler(Looper looper) {
            super(looper);
        }
    }
    MyHandler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);
        imageHolder = findViewById(R.id.image_holder);
        storageRef = FirebaseStorage.getInstance().getReference();
        res = getResources();
        myHandlerThread.start();
        mHandler = new MyHandler(myHandlerThread.getLooper());
        mHandler.post(new MyTask(this));


//        timer.scheduleAtFixedRate(new MyTimerTask(),0,7000);
    }

    public int counter = 0, id = 1;
    public String starttime, endtime, imgStartTime, imgEndTime;
    private class MyTask implements Runnable {
        private WeakReference<SliderActivity> activityRef;

        MyTask(SliderActivity activity) {
            activityRef = new WeakReference<>(activity);
        }

        @Override
        public void run() {
            if(counter == 0){
                SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                f.setTimeZone(TimeZone.getTimeZone("UTC"));
                starttime = f.format(new Date());
            }
            counter ++;
            if(counter == 40){   //make 40 for 10 normal images
                SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                f.setTimeZone(TimeZone.getTimeZone("UTC"));
                endtime = f.format(new Date());

                store(starttime, endtime);
            }
            Log.d("Counter", String.valueOf(counter));
            final SliderActivity activity = activityRef.get();
            AtomicInteger index = activity.getIndex();
            switch (index.get() % 4) {
                case 0:
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            activity.imageHolder.setBackgroundColor(activity.res.getColor(R.color.gray));

                            GlideApp.with(activity).load(R.drawable.ic_gray_background).into(activity.imageHolder);
                        }
                    });
                    Log.d(TAG, "run: " + index);
                    activity.setIndex(index.get() + 1);
                    activity.mHandler.postDelayed(this, 2000);

                    /*SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    f.setTimeZone(TimeZone.getTimeZone("UTC"));
                    imgEndTime = f.format(new Date());*/

                    break;
                case 1:
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            int imageId = activity.random.nextInt(10) + 1;
                            int imageId = id;
                            id = id + 1;
                            String imageName = IMAGE_PREFIX + imageId + ".jpg"; //fetch from firebase
                            String imagename = IMAGE_PREFIX + imageId; //fetch from imageInfo
                            Log.d("imagename",imagename);
                            final StorageReference imgRef = activity.storageRef.child(imageName);
                            GlideApp.with(activity).load(imgRef).into(activity.imageHolder);


                            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            SimpleDateFormat ff = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            f.setTimeZone(TimeZone.getTimeZone("UTC"));
                            ff.setTimeZone(TimeZone.getTimeZone("UTC"));
                            imgStartTime = f.format(new Date());
                            long milis =  System.currentTimeMillis();
                            long secs = (milis / 1000);
                            secs += 6;
                            imgEndTime = ff.format(new Date(secs * 1000L));
                            Log.d("ImgTime", imgStartTime + " : " + imgEndTime);

                            Bundle bundle = getIntent().getExtras();
                            String email = bundle.getString("email");

                            retrofit = new Retrofit.Builder()
                                    .baseUrl(Constants.ROOT_URL)
                                    .addConverterFactory(GsonConverterFactory.create()).build();
                            retrofit.create(ImageAdapterApi.class).testImage(email, imgStartTime, imgEndTime, imagename).enqueue(new Callback<ImageAdapterResponse>() {

                                @Override
                                public void onResponse(Call<ImageAdapterResponse> call, Response<ImageAdapterResponse> response) {
//                                  Log.d("SUCCESS", "hahah ");
//                                  progressDialog.hide();
                                    Log.d("HAHAHA", String.valueOf(response.body().message));
//                                  Toast.makeText(ImageAdapter.this, String.valueOf(response.body().message), Toast.LENGTH_LONG);
                                }

                                @Override
                                public void onFailure(Call<ImageAdapterResponse> call, Throwable t) {
                                    Log.d("Faliure", "saddd");
//                                  progressDialog.hide();
//                                  Toast.makeText(ImageAdapter.this, t.getMessage(), Toast.LENGTH_LONG);
                                }
                            });

                        }
                    });
                    Log.d(TAG, "run: " + index);
                    activity.setIndex(index.get() + 1);
                    activity.mHandler.postDelayed(this, 6000);
                    break;
                case 2:
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            activity.imageHolder.setBackgroundColor(activity.res.getColor(R.color.gray));
                            GlideApp.with(activity).load(R.drawable.ic_gray_background).into(activity.imageHolder);
                        }
                    });
                    Log.d(TAG, "run: " + index);
                    activity.setIndex(index.get() + 1);
                    activity.mHandler.postDelayed(this, 2000);
                    break;
                case 3:
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            GlideApp.with(activity).load(R.drawable.neutral).into(activity.imageHolder);
                        }
                    });
                    Log.d(TAG, "run: " + index);
                    activity.setIndex(index.get() + 1);
                    activity.mHandler.postDelayed(this, 6000);
                    break;
            }
        }
    }

    public void store(String starttime, String endtime) {

        Log.d(TAG, "store: InStore ");
        Bundle bundle = getIntent().getExtras();
        String email = bundle.getString("email");
        String testplace = bundle.getString("testplace");
        String testplacedescription = bundle.getString("testplacedescription");
        String testambience = bundle.getString("testambience");
        String watchno = bundle.getString("watchno");

        if(String.valueOf(testplacedescription ).equals(""))testplacedescription = "-";
        Log.d("motherboard",testplace + " " + testplacedescription + " " + testambience + " " + watchno);

        //retrofit

//        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.ROOT_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build();
            retrofit.create(SessionApi.class).testInfo(email, starttime, endtime, testplace, testplacedescription,
                    testambience, watchno).enqueue(new Callback<SessionResponse>() {

                @Override
                public void onResponse(Call<SessionResponse> call, Response<SessionResponse> response) {
//                                      Log.d("SUCCESS", "hahah ");
//                                        progressDialog.hide();
                    Log.d("SUCCESS", String.valueOf(response.body().message));
                    Toast.makeText(SliderActivity.this, String.valueOf(response.body().message), Toast.LENGTH_LONG);
                }

                @Override
                public void onFailure(Call<SessionResponse> call, Throwable t) {
                    Log.d("Faliure", "saddd");
//                                        progressDialog.hide();
                    Toast.makeText(SliderActivity.this, t.getMessage(), Toast.LENGTH_LONG);
                }
            });

            Log.d(TAG, "store: OutStore");
            startActivity(new Intent(SliderActivity.this, Result.class));
            finish();
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myHandlerThread.quit();
    }

    public AtomicInteger getIndex() {
        return index;
    }

    public void setIndex(int idx) {
        index.set(idx);
    }
}
