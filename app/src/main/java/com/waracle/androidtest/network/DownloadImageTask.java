package com.waracle.androidtest.network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.waracle.androidtest.utils.ImageUtil;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

    private WeakReference<ImageView> imageView;

    public DownloadImageTask(ImageView imageView) {
        this.imageView = new WeakReference<>(imageView);
    }

    /**
     * @param strings
     * Executes the downloadImage() method
     * immediately on a background thread
     * @return
     */
    @Override
    protected Bitmap doInBackground(String... strings) {

        return downloadImage(strings[0]); //passing the string from the params (strings), which is thw image url to the downloadImage method
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if(bitmap != null && imageView.get() != null) {
            imageView.get().setImageBitmap(bitmap);
        }
    }

    /**
     * @param imageUrl
     * Download the image from the JSON and make it a bitmap
     * @return
     */
    private Bitmap downloadImage(String imageUrl) {

        Bitmap bitmap = null;
        HttpURLConnection conn = null;

        try {
            final URL url = new URL(imageUrl);
            conn = (HttpURLConnection) url.openConnection(); //creating a connection to the JSON data (the given url)
            final int respCode = conn.getResponseCode(); //

            if(respCode == HttpURLConnection.HTTP_OK) { // check if we got a response code '200'

                bitmap = streamToBitmap(conn.getInputStream());

            } else if(respCode == HttpURLConnection.HTTP_MOVED_TEMP || respCode == HttpURLConnection.HTTP_MOVED_PERM) {

                final String newHeader = conn.getHeaderField("CakeData");
                final URL newUrl = new URL(newHeader);

                conn = (HttpURLConnection) newUrl.openConnection();
                bitmap = streamToBitmap(conn.getInputStream());
            }

        } catch (IOException ex) {
            ex.printStackTrace();

        } finally {
            if(conn != null) conn.disconnect();
        }

        if(bitmap != null) {
            ImageUtil.cacheImage(imageUrl, bitmap);
        }

        return bitmap;
    }

    /**
     * @param is
     * decode InputStream to Bitmap
     * @return
     */
    private Bitmap streamToBitmap(InputStream is) {
        if(is != null)
            return BitmapFactory.decodeStream(is);
        return null;
    }
}
