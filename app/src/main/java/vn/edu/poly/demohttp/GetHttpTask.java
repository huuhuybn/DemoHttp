package vn.edu.poly.demohttp;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

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

public class GetHttpTask extends AsyncTask<String, Long, String> {


    TextView tvResult;

    List<Item> items;

    public OnLoadCompletedListener getOnLoadCompletedListener() {
        return onLoadCompletedListener;
    }

    public void setOnLoadCompletedListener(OnLoadCompletedListener onLoadCompletedListener) {
        this.onLoadCompletedListener = onLoadCompletedListener;
    }

    OnLoadCompletedListener onLoadCompletedListener;

    public GetHttpTask(TextView tvResult) {
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

            try {
                JSONObject root = new JSONObject(s);
                JSONArray hd_wallpaper = root.getJSONArray("HD_WALLPAPER");

                for (int i = 0; i < hd_wallpaper.length() - 1; i++) {

                    JSONObject item = hd_wallpaper.getJSONObject(i);
                    String id = item.getString("id");
                    String cat_id = item.getString("cat_id");
                    String wallpaper_image = item.getString("wallpaper_image");
                    String wallpaper_image_thumb = item.getString("wallpaper_image_thumb");
                    String total_views = item.getString("total_views");
                    String cid = item.getString("cid");
                    String category_name = item.getString("category_name");
                    String category_image = item.getString("category_image");
                    String category_image_thumb = item.getString("category_image_thumb");


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


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }


    }
}
