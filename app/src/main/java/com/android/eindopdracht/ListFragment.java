package com.android.eindopdracht;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import java.util.ArrayList;

public class ListFragment extends Fragment {

    ListView list;
    VenueAdapter adapter;
    ArrayList<Venue> venuesList;
    private OnItemSelectedListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        String query = getActivity().getIntent().getExtras().getString("query");
        Toast.makeText(getActivity().getApplicationContext(), query, Toast.LENGTH_LONG).show();

        list = (ListView)view.findViewById(R.id.list);
        venuesList = new ArrayList<Venue>();

        String url = "http://api.eet.nu/venues?query="+query+"&geolocation=51.520654,5.047317&max_distance=5";
        url = url.replaceAll(" ", "%20");
        // Listview on item click listener
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                int venueId = venuesList.get(position).getId();

                updateDetail(venueId);
            }
        });

        new VenueAsyncTask().execute(url);
        return view;
    }

    public interface OnItemSelectedListener {
        public void onItemSelected(int venueId);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnItemSelectedListener) {
            listener = (OnItemSelectedListener) activity;
        } else {
            throw new ClassCastException(activity.toString()
                    + " must implemenet MyListFragment.OnItemSelectedListener");
        }
    }


    // May also be triggered from the Activity
    public void updateDetail(int venueId) {
        // Send data to Activity
        Toast.makeText(getActivity().getApplicationContext(), String.valueOf(venueId), Toast.LENGTH_LONG).show();
        listener.onItemSelected(venueId);
    }

    public class VenueAsyncTask extends AsyncTask<String, Void, Boolean> {

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

                        venue.setId(object.getInt("id"));
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
                Toast toast = Toast.makeText(getActivity().getApplicationContext(), "data was not parsed", Toast.LENGTH_LONG);
                toast.show();
            } else {
                VenueAdapter adapter = new VenueAdapter(getActivity().getApplicationContext(), R.layout.row, venuesList);
                list.setAdapter(adapter);
            }


        }
    }
}
