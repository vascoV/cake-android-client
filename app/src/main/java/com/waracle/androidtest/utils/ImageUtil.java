package com.waracle.androidtest.utils;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.widget.ImageView;

import com.waracle.androidtest.network.DownloadImageTask;

import java.lang.ref.SoftReference;
import java.security.InvalidParameterException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class ImageUtil {

    /**
     * The image cache. If we already downloaded an image, no need to re-download it.
     * We hold images in a map, with their URL as the key. When an image is requested,
     * we check if the cache contains its key and if it does, we return the image. If it doesn't
     * we request it from the network
     */
    private static final Map<String, SoftReference<Bitmap>> cache = new ConcurrentHashMap<>();

    public ImageUtil() { }

    public static void load(String url, ImageView imageView) {

        if(TextUtils.isEmpty(url)) {
            throw new InvalidParameterException("The url is empty");
        }

        if(cache.containsKey(url)) {
            final SoftReference<Bitmap> ref = cache.get(url);

            if(ref != null) {
                imageView.setImageBitmap(ref.get());
            }
        } else {
            new DownloadImageTask(imageView).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url);
        }
    }

    public static void cacheImage(String key, Bitmap bitmap) {
        if(!cache.containsKey(key)) {
            cache.put(key, new SoftReference<>(bitmap));
        }
    }
}
