package vn.edu.poly.demohttp;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;

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

import vn.edu.poly.demohttp.model.HDWALLPAPER;
import vn.edu.poly.demohttp.model.ItemResponse;

public class GetHttpGSONTask extends AsyncTask<String, Long, String> {


    TextView tvResult;

    List<HDWALLPAPER> items;

    public GetHttpGSONTask(TextView tvResult) {
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


            Gson gson = new Gson();

            ItemResponse itemResponse = gson.fromJson(s, ItemResponse.class);

            items = itemResponse.getHDWALLPAPER();


        }


    }
}
