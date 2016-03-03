package com.danilafe.rubberduck;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    SwipeRefreshLayout swipeRefreshLayout;
    TextView textView;
    String[] text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = new String[] { "No Data Fetched" };
        textView = (TextView) findViewById(R.id.duckText);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateText();
            }
        });

        Thread networkThread = new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL("");
                    InputStream inputStream = url.openStream();
                    String total;
                    String bufferString;
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    total = bufferedReader.readLine();
                    while ((bufferString = bufferedReader.readLine()) != null) total += "\n" + bufferString;
                    text = total.split("\n");
                }
                catch (MalformedURLException e) { e.printStackTrace();}
                catch (IOException e) { e.printStackTrace(); }
            }
        };

        networkThread.start();
    }

    private void updateText(){
        int textIndex = (int) Math.round(Math.random() * text.length) - 1;
        textView.setText(text[textIndex]);
    }
}
