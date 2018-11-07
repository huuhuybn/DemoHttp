package vn.edu.poly.demohttp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private TextView tvResult;

    private String url = "http://www.tapetee.com/api.php?latest";

    private List<Item> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvResult = findViewById(R.id.tvResult);
        itemList = new ArrayList<>();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tvResult.setText("Loading...");
                GetHttpJsonTask getHttpTask = new GetHttpJsonTask(tvResult);
                getHttpTask.execute(url);

                OnLoadCompletedListener onLoadCompletedListener = new OnLoadCompletedListener() {
                    @Override
                    public void onCompleted(List<Item> items) {
                        itemList = items;
                    }
                };
                getHttpTask.setOnLoadCompletedListener(onLoadCompletedListener);

                // PostHttpTask postHttpTask = new PostHttpTask(tvResult);
                //postHttpTask.execute(url);

            }
        });
    }


}
