package com.android.eindopdracht;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    ListView list;
    VenueAdapter adapter;
    ArrayList<Venue> venuesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = (ListView)findViewById(R.id.list);
        venuesList = new ArrayList<Venue>();
    }

    public void search(View view){
        EditText editText = (EditText)findViewById(R.id.editText);
        String query = editText.getText().toString().trim();

        Intent intent = new Intent(this, DisplayActivity.class);
        intent.putExtra("query", query);
        startActivity(intent);
    }
}
