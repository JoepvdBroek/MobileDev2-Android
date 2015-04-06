package com.android.eindopdracht;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
import java.util.ArrayList;


public class DisplayActivity extends ActionBarActivity {

    ListView list;
    VenueAdapter adapter;
    ArrayList<Venue> venuesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        String query = getIntent().getExtras().getString("query");
        Toast.makeText(getApplicationContext(), query, Toast.LENGTH_LONG).show();

        list = (ListView)findViewById(R.id.list);
        venuesList = new ArrayList<Venue>();

        String url = "http://api.eet.nu/venues?query="+query+"&geolocation=51.520654,5.047317&max_distance=5";

        // Listview on item click listener
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // getting values from selected ListItem
                String name = ((TextView) view.findViewById(R.id.tvName))
                        .getText().toString();
                String category = ((TextView) view.findViewById(R.id.tvCategory))
                        .getText().toString();
                String streetname = ((TextView) view.findViewById(R.id.tvStreetname))
                        .getText().toString();

                Toast.makeText(getApplicationContext(), name, Toast.LENGTH_LONG).show();

                // Starting single contact activity
                /*Intent in = new Intent(getApplicationContext(),
                        SingleContactActivity.class);
                in.putExtra("name", name);
                in.putExtra("category", category);
                in.putExtra("streetname", streetname);
                startActivity(in);*/

            }
        });

        new VenueAsynTask().execute(url);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class VenueAsynTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... urls) {
            try {

                HttpClient client = new DefaultHttpClient();
                HttpPost post = new HttpPost(urls[0]);
                HttpResponse response = client.execute(post);

                // StatusLine stat = response.getStatusLine();
                int status = response.getStatusLine().getStatusCode();

                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);

                    JSONObject jsonO = new JSONObject(data);
                    JSONArray jArray = jsonO.getJSONArray("results");

                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject object = jArray.getJSONObject(i);

                        Venue venue = new Venue();

                        venue.setName(object.getString("name"));
                        venue.setCategory(object.getString("category"));
                        JSONObject address = object.getJSONObject("address");
                        venue.setStreetname(address.getString("street"));
                        venue.setZipcode(address.getString("zipcode"));
                        venue.setCity(address.getString("city"));

                        venuesList.add(venue);
                    }
                    return true;
                }

            } catch (ClientProtocolException e){
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            if(result == false){
                //show message that data was not parsed
                Toast toast = Toast.makeText(getApplicationContext(), "data was not parsed", Toast.LENGTH_LONG);
                toast.show();
            } else {
                VenueAdapter adapter = new VenueAdapter(getApplicationContext(), R.layout.row, venuesList);
                list.setAdapter(adapter);
            }


        }
    }
}
