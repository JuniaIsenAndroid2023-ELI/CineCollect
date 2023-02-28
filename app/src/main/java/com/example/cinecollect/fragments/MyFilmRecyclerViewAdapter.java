package com.example.cinecollect.fragments;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cinecollect.R;
import com.example.cinecollect.pojo.Film;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Film}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyFilmRecyclerViewAdapter extends RecyclerView.Adapter<MyFilmRecyclerViewAdapter.ViewHolder> {

    private final List<Film> mValues;

    public MyFilmRecyclerViewAdapter(List<Film> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        // TODO:
        // Assign corresponding fields of holder.
        holder.number.setText(holder.mItem.title);
        holder.content.setText(holder.mItem.director);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView number;
        public final TextView content;
        public Film mItem;
//        TODO:
//        Design the fragment_item.xml with these:
//        public TextView title;
//        public TextView releaseDate;
//        public TextView genre;
//        public TextView director;
//        public TextView summary;
//        public TextView review;
//        public ImageView thumbnail;
//        public ImageView score;
        public ViewHolder(View binding) {
            super(binding);

            // TODO:
            // Bind views here
            // for the time being number and content
            number = binding.findViewById(R.id.item_number);
            content =  binding.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + "FIXHERE";
        }
    }
}