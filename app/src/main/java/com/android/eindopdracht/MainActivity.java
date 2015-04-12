package com.android.eindopdracht;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements LocationListener {

    TextView evQuery;
    TextView evRadius;
    SharedPreferences sharedPreferences;

    private String latitute;
    private String longitude;
    private LocationManager locationManager;
    private String provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the location manager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Define the criteria how to select the location provider -> use
        // default
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);

        evQuery = (TextView) findViewById(R.id.query);
        evRadius = (TextView) findViewById(R.id.radius);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        String settingQuery = sharedPreferences.getString("default_query", "");
        Toast.makeText(getApplicationContext(), settingQuery, Toast.LENGTH_LONG).show();

        evQuery.setText(settingQuery);
    }

    public void search(View view){
        String query = evQuery.getText().toString().trim();
        String radius = evRadius.getText().toString().trim();
        Location location = locationManager.getLastKnownLocation(provider);

        if((query.length() > 0) && (radius.length() > 0)){
            if (location != null) {
                System.out.println("Provider " + provider + " has been selected.");
                onLocationChanged(location);

                Intent intent = new Intent(this, DisplayActivity.class);
                intent.putExtra("longitude", longitude);
                intent.putExtra("latitute", latitute);
                intent.putExtra("query", query);
                intent.putExtra("radius", radius);
                startActivity(intent);
            } else {
                Toast.makeText(this, "GPS is niet toegepast", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Vul de velden in", Toast.LENGTH_LONG).show();
        }

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


    /* Request updates at startup */
    @Override
    protected void onResume() {
        super.onResume();

        String settingQuery = sharedPreferences.getString("default_query", "");
        Toast.makeText(getApplicationContext(), settingQuery, Toast.LENGTH_LONG).show();

        evQuery.setText(settingQuery);

        locationManager.requestLocationUpdates(provider, 400, 1, this);
    }

    /* Remove the locationlistener updates when Activity is paused */
    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        latitute = String.valueOf(location.getLatitude());
        longitude = String.valueOf(location.getLongitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(this, "Enabled new provider " + provider, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this, "Disabled provider " + provider, Toast.LENGTH_SHORT).show();
    }
}
