package vn.edu.poly.demohttp;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GetHttpJsonTask extends AsyncTask<String, Long, String> {


    TextView tvResult;

    List<Item> items;

    public OnLoadCompletedListener getOnLoadCompletedListener() {
        return onLoadCompletedListener;
    }

    public void setOnLoadCompletedListener(OnLoadCompletedListener onLoadCompletedListener) {
        this.onLoadCompletedListener = onLoadCompletedListener;
    }

    OnLoadCompletedListener onLoadCompletedListener;

    public GetHttpJsonTask(TextView tvResult) {
        this.tvResult = tvResult;
    }

    @Override
    protected String doInBackground(String... strings) {


        try {
            URL url = new URL(strings[0]);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            InputStream inputStream = httpURLConnection.getInputStream();

            BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder total = new StringBuilder();

            for (String line; (line = r.readLine()) != null; ) {
                total.append(line).append('\n');
            }

            return total.toString();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        items = new ArrayList<>();

        Log.e("DATA", s);
        if (s != null) {


            JsonParser jsonParser = new JsonParser();

            JsonObject root = jsonParser.parse(s).getAsJsonObject();

            JsonArray hd_wallpaper = root.get("HD_WALLPAPER").getAsJsonArray();

            for (int i = 0; i < hd_wallpaper.size(); i++) {

                JsonObject item = hd_wallpaper.get(i).getAsJsonObject();

                String id = item.get("id").getAsString();
                String cat_id = item.get("cat_id").getAsString();
                String wallpaper_image = item.get("wallpaper_image").getAsString();
                String wallpaper_image_thumb = item.get("wallpaper_image_thumb").getAsString();
                String total_views = item.get("total_views").getAsString();
                String cid = item.get("cid").getAsString();
                String category_name = item.get("category_name").getAsString();
                String category_image = item.get("category_image").getAsString();
                String category_image_thumb = item.get("category_image_thumb").getAsString();

                Item item_ = new Item();
                item_.id = id;
                item_.cat_id = cat_id;
                item_.wallpaper_image = wallpaper_image;
                item_.wallpaper_image_thumb = wallpaper_image_thumb;
                item_.total_views = total_views;
                item_.cid = cid;
                item_.category_name = category_name;
                item_.category_image = category_image;
                item_.category_image_thumb = category_image_thumb;

                items.add(item_);

            }

            onLoadCompletedListener.onCompleted(items);


        }


    }
}
