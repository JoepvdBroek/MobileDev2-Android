package com.android.eindopdracht;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
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

    TextView tvQuery;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvQuery = (TextView) findViewById(R.id.editText);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        String settingQuery = sharedPreferences.getString("default_query", "");
        Toast.makeText(getApplicationContext(), settingQuery, Toast.LENGTH_LONG).show();

        tvQuery.setText(settingQuery);
    }

    @Override
    protected void onResume() {
        super.onResume();

        String settingQuery = sharedPreferences.getString("default_query", "");
        Toast.makeText(getApplicationContext(), settingQuery, Toast.LENGTH_LONG).show();

        tvQuery.setText(settingQuery);
    }

    public void search(View view){
        EditText editText = (EditText)findViewById(R.id.editText);
        String query = editText.getText().toString().trim();

        Intent intent = new Intent(this, DisplayActivity.class);
        intent.putExtra("query", query);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        //inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        // handle presses on the action bar items
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(getApplicationContext(), "Settings", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
