package com.example.cinecollect.fragments;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cinecollect.R;
import com.example.cinecollect.UserFilmListActivity;
import com.example.cinecollect.executor.AddUserFilmExecutor;
import com.example.cinecollect.executor.UpdateUserFilmExecutor;
import com.example.cinecollect.listeners.AddUserFilmListener;
import com.example.cinecollect.listeners.UpdateUserFilmListener;
import com.example.cinecollect.pojo.Film;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Film}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyFilmRecyclerViewAdapter extends RecyclerView.Adapter<MyFilmRecyclerViewAdapter.ViewHolder> implements AddUserFilmListener, UpdateUserFilmListener {

    private static String filmRepository = "filmRepository";

    private final List<Film> mValues;

    private final String ownerUserId;
    private final String perceiverUserId;
    private final Context context;

    public MyFilmRecyclerViewAdapter(List<Film> items, String ownerUserId, String perceiverUserId, Context context) {
        this.mValues = items;
        this.ownerUserId = ownerUserId;
        this.perceiverUserId = perceiverUserId;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Film film = mValues.get(position);

        holder.mItem = film;

        holder.title.setText(film.title);
        holder.description.setText(film.description);

        if (!ownerUserId.equals(perceiverUserId)) {
            holder.deleteButton.setVisibility(View.GONE);
        }

        if (ownerUserId.equals(filmRepository)) {
            holder.likeImageView.setVisibility(View.GONE);
            holder.dislikeImageView.setVisibility(View.GONE);

            holder.likeButton.setOnClickListener(view -> addUserFilm(perceiverUserId, film, true));
            holder.dislikeButton.setOnClickListener(view -> addUserFilm(perceiverUserId, film, false));
        }
        else {
            updateLikedIcon(holder, film.liked);
            holder.likeButton.setOnClickListener(view -> updateUserFilm(holder, perceiverUserId, film, true));
            holder.dislikeButton.setOnClickListener(view -> updateUserFilm(holder, perceiverUserId, film, false));
        }
    }

    private void addUserFilm(String userId, Film film, Boolean liked) {
        film.liked = liked;
        AddUserFilmExecutor executor = new AddUserFilmExecutor(this);
        executor.addUserFilm(userId, film.id, film);
        Toast.makeText(context, "Added to your list as " + (liked ? "liked" : "disliked") + "!", Toast.LENGTH_SHORT).show();
    }

    private void updateUserFilm(ViewHolder holder, String userId, Film film, Boolean liked) {
        film.liked = liked;
        updateLikedIcon(holder, liked);
        UpdateUserFilmExecutor executor = new UpdateUserFilmExecutor(this);
        executor.updateUserFilm(userId, film.id, film);
    }

    private void updateLikedIcon(ViewHolder holder, Boolean liked) {
        if (liked == null) {
            holder.likeImageView.setVisibility(View.GONE);
            holder.dislikeImageView.setVisibility(View.GONE);
        }
        else {
            if (liked) {
                holder.likeImageView.setVisibility(View.VISIBLE);
                holder.dislikeImageView.setVisibility(View.GONE);
            }
            else {
                holder.likeImageView.setVisibility(View.GONE);
                holder.dislikeImageView.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    @Override
    public void onAddUserFilm() {}

    @Override
    public void onUpdateUserFilm() {}

    public class ViewHolder extends RecyclerView.ViewHolder {
        public Film mItem;
        public TextView title;
        public TextView description;
        public ImageButton likeButton;
        public ImageButton dislikeButton;
        public ImageButton deleteButton;
        public ImageView likeImageView;
        public ImageView dislikeImageView;

        public ViewHolder(View binding) {
            super(binding);

            title = binding.findViewById(R.id.filmTitle);
            description = binding.findViewById(R.id.filmDescription);
            likeButton = binding.findViewById(R.id.filmLikeButton);
            dislikeButton = binding.findViewById(R.id.filmDislikeButton);
            deleteButton = binding.findViewById(R.id.filmDeleteButton);
            likeImageView = binding.findViewById(R.id.filmLikedImageView);
            dislikeImageView = binding.findViewById(R.id.filmDislikedImageView);
        }

        @Override
        public String toString() {
            return super.toString() + "FIXHERE";
        }
    }
}