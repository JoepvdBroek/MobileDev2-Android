package com.android.eindopdracht;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class DetailFragment extends Fragment {

    Venue venue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        return view;
    }

    public void setDetails(int venueId) {
        String url = "http://api.eet.nu/venues/"+venueId;
        new VenueAsyncTask().execute(url);
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

                    JSONObject object = new JSONObject(data);
                    //JSONArray jArray = jsonO.getJSONArray("results");

                    //for (int i = 0; i < jArray.length(); i++) {
                        //JSONObject object = jArray.getJSONObject(i);

                        venue = new Venue();

                        venue.setId(object.getInt("id"));
                        venue.setName(object.getString("name"));
                        venue.setCategory(object.getString("category"));
                        JSONObject address = object.getJSONObject("address");
                        venue.setStreetname(address.getString("street"));
                        venue.setZipcode(address.getString("zipcode"));
                        venue.setCity(address.getString("city"));
                    //}
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
                TextView tvName = (TextView) getView().findViewById(R.id.tvName);
                TextView tvCategory = (TextView) getView().findViewById(R.id.tvCategory);
                TextView tvStreetname = (TextView) getView().findViewById(R.id.tvStreetname);
                TextView tvZipcode = (TextView) getView().findViewById(R.id.tvZipcode);
                TextView tvCity = (TextView) getView().findViewById(R.id.tvCity);

                tvName.setText(venue.getName());
                tvCategory.setText(venue.getCategory());
                tvStreetname.setText(venue.getStreetname());
                tvZipcode.setText(venue.getZipcode());
                tvCity.setText(venue.getCity());
            }
        }
    }
}