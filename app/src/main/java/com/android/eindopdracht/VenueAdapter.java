package com.android.eindopdracht;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Joep on 2-4-2015.
 */
public class VenueAdapter extends ArrayAdapter<Venue> {

    ArrayList<Venue> venuesList;
    LayoutInflater vi;
    int Resource;
    ViewHolder holder;

    public VenueAdapter(Context context, int resource, ArrayList<Venue> objects) {
        super(context, resource, objects);
        vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resource = resource;
        venuesList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // convert view = design
        View v = convertView;
        if (v == null) {
            holder = new ViewHolder();
            v = vi.inflate(Resource, null);
            holder.tvName = (TextView) v.findViewById(R.id.tvName);
            holder.tvCategory = (TextView) v.findViewById(R.id.tvCategory);
            holder.tvStreetname = (TextView) v.findViewById(R.id.tvStreetname);
            holder.tvZipcode = (TextView) v.findViewById(R.id.tvZipcode);
            holder.tvCity = (TextView) v.findViewById(R.id.tvCity);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        holder.tvName.setText(venuesList.get(position).getName());
        holder.tvCategory.setText(venuesList.get(position).getCategory());
        holder.tvStreetname.setText(venuesList.get(position).getStreetname());
        holder.tvZipcode.setText(venuesList.get(position).getZipcode());
        holder.tvCity.setText(venuesList.get(position).getCity());
        return v;

    }

    static class ViewHolder {
        public TextView tvName;
        public TextView tvCategory;
        public TextView tvStreetname;
        public TextView tvZipcode;
        public TextView tvCity;
    }
}
