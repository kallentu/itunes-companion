package com.kallentu.display_itunes;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Async task for obtaining image from image URL.
 */
@SuppressLint("StaticFieldLeak")
public class ImageTask extends AsyncTask<String, Void, Bitmap> {
    private ImageView imageView;

    ImageTask(ImageView bmImage) {
        this.imageView = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String url = urls[0];
        Bitmap imageBitMap = null;
        try {
            InputStream in = new java.net.URL(url).openStream();
            imageBitMap = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return imageBitMap;
    }

    protected void onPostExecute(Bitmap result) {
        imageView.setImageBitmap(result);
    }
}