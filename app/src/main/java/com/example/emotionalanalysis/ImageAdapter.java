package com.example.emotionalanalysis;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ImageAdapter extends PagerAdapter {

    Retrofit retrofit;

    private int count;
    private Context mContext;
    private int[] mImageIds = new int[]{R.drawable.h1, R.drawable.h2};

    FirebaseStorage storage = FirebaseStorage.getInstance();
    Random random = new Random();

    ImageAdapter(Context mContext){
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return 6;
    }  //6 views displayed

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        int id = 1 + random.nextInt(6);
        String base = "image" + id + ".jpg";
        Log.d("IMAGE:", base );
        ImageView imageView = new ImageView(mContext);
        StorageReference storageRef = storage.getReferenceFromUrl("gs://emotionalanalysis-f6698.appspot.com/").child(base);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        container.addView(imageView,0);
        GlideApp.with(mContext).load(storageRef).into(imageView);
        count++;

        String imageName = "image" + id;

        // for testImageTable : insertion of each image desc into the table;

//        if(retrofit==null) {
//            retrofit = new Retrofit.Builder()
//                    .baseUrl(Constants.ROOT_URL)
//                    .addConverterFactory(GsonConverterFactory.create()).build();
//            retrofit.create(ImageAdapterApi.class).testImage(imageName).enqueue(new Callback<ImageAdapterResponse>() {
//
//                @Override
//                public void onResponse(Call<ImageAdapterResponse> call, Response<ImageAdapterResponse> response) {
////                    Log.d("SUCCESS", "hahah ");
////                    progressDialog.hide();
//                    Log.d("SUCCESS", String.valueOf(response.body().message));
////                    Toast.makeText(ImageAdapter.this, String.valueOf(response.body().message), Toast.LENGTH_LONG);
//                }
//
//                @Override
//                public void onFailure(Call<ImageAdapterResponse> call, Throwable t) {
//                    Log.d("Faliure", "saddd");
////                    progressDialog.hide();
////                    Toast.makeText(ImageAdapter.this, t.getMessage(), Toast.LENGTH_LONG);
//                }
//            });

//            finish();
//            startActivity(new Intent(getApplicationContext(),MainActivity.class));
//        }

//        if(count == 6){
//            return -1;
//        }
        Log.d("COUNT", String.valueOf(count));
        return imageView;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ImageView)object);
    }
}
