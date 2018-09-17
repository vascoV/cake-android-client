package com.waracle.androidtest.network;

import android.os.AsyncTask;

import com.waracle.androidtest.listeners.OnItemsLoadedListener;
import com.waracle.androidtest.model.Cake;
import com.waracle.androidtest.utils.Configs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TheCakeTask extends AsyncTask<Void, Void, List<Cake>> {

    private static final String TAG = TheCakeTask.class.getSimpleName();

    private OnItemsLoadedListener itemsLoadedListener;

    public TheCakeTask(OnItemsLoadedListener itemsLoadedListener) {
        this.itemsLoadedListener = itemsLoadedListener;
    }

    /**
     * @param voids
     * invokes on the background thread immediately after the onPreExecute() finishes
     * reading the JSON_URL
     * getting the JSON object from the JSON array
     * @return
     */
    @Override
    protected List<Cake> doInBackground(Void... voids) {

        final List<Cake> cakeList = new ArrayList<>();
        JSONArray jsonArray;
        HttpURLConnection urlConn = null;

        try {
            URL url = new URL(Configs.JSON_URL);
            urlConn = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConn.getInputStream());

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            StringBuilder json = new StringBuilder();

            String line;
            while((line = bufferedReader.readLine()) != null) {
                json.append(line).append('\n');
            }

            jsonArray = new JSONArray(json.toString());
            JSONObject jsonObject;
            for(int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);

                final String title = jsonObject.getString(Configs.TITLE_NAME);
                final String desc = jsonObject.getString(Configs.DESC_NAME);
                final String imageUrl = jsonObject.getString(Configs.IMAGE_URL);

                cakeList.add(new Cake(title, desc, imageUrl));
            }

        } catch (IOException | JSONException ex) {
            ex.printStackTrace();

        } finally {
            if(urlConn != null) urlConn.disconnect();
        }
        return cakeList;
    }

    /**
     * @param addCake
     * executes when the doInBackground(Params...) finishes
     * loads the cake list on the UI
     */
    @Override
    protected void onPostExecute(List<Cake> addCake) {
        super.onPostExecute(addCake);
        itemsLoadedListener.onItemsLoaded(addCake);
    }
}
