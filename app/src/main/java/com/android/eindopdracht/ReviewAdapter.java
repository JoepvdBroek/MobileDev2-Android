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
public class ReviewAdapter extends ArrayAdapter<Review> {

    ArrayList<Review> reviewsList;
    LayoutInflater vi;
    int Resource;
    ViewHolder holder;

    public ReviewAdapter(Context context, int resource, ArrayList<Review> objects) {
        super(context, resource, objects);
        vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resource = resource;
        reviewsList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // convert view = design
        View v = convertView;
        if (v == null) {
            holder = new ViewHolder();
            v = vi.inflate(Resource, null);
            holder.tvBody = (TextView) v.findViewById(R.id.tvBody);
            holder.tvAuthor = (TextView) v.findViewById(R.id.tvAuthor);
            holder.tvRating = (TextView) v.findViewById(R.id.tvRating);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        holder.tvAuthor.setText(reviewsList.get(position).getAuthor());
        holder.tvBody.setText(reviewsList.get(position).getBody());
        if(reviewsList.get(position).getRating()== 0){
            holder.tvRating.setText("Rating: -");
        } else {
            holder.tvRating.setText("Rating: "+String.valueOf(reviewsList.get(position).getRating()));
        }

        return v;

    }

    static class ViewHolder {
        public TextView tvAuthor;
        public TextView tvRating;
        public TextView tvBody;
    }
}
