package com.example.emotionalanalysis;

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
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;


public class SliderActivity extends AppCompatActivity {
    private static final String TAG = "SliderActivity";
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

    private static class MyTask implements Runnable {
        private WeakReference<SliderActivity> activityRef;

        MyTask(SliderActivity activity) {
            activityRef = new WeakReference<>(activity);
        }

        @Override
        public void run() {
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
                    break;
                case 1:
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int imageId = activity.random.nextInt(6) + 1;
                            String imageName = IMAGE_PREFIX + imageId + ".jpg";

                            final StorageReference imgRef = activity.storageRef.child(imageName);
                            GlideApp.with(activity).load(imgRef).into(activity.imageHolder);
                        }
                    });
                    Log.d(TAG, "run: " + index);
                    activity.setIndex(index.get() + 1);
                    activity.mHandler.postDelayed(this, 7000);
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
                    activity.mHandler.postDelayed(this, 7000);
                    break;
            }
        }
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
