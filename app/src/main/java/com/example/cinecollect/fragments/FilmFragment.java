package com.example.cinecollect.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cinecollect.R;
import com.example.cinecollect.executor.RetrieveFilmsExecutor;
import com.example.cinecollect.helpers.UserHelper;
import com.example.cinecollect.interfaces.FilmChangeListener;
import com.example.cinecollect.pojo.Film;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class FilmFragment extends Fragment implements FilmChangeListener {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String ARG_UID = "uid";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private String mUid;
    private RetrieveFilmsExecutor mExecutor;
    private RecyclerView mRecyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FilmFragment() {
    }

    @Override
    public void onStart() {
        super.onStart();

        if (mUid != null && UserHelper.getInstance().getUserHasFilms(mUid)) {
            mExecutor = new RetrieveFilmsExecutor(this);
            mExecutor.getUserFilms(mUid);
        }
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static FilmFragment newInstance(int columnCount) {
        FilmFragment fragment = new FilmFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static FilmFragment newInstance(String uid) {
        FilmFragment fragment = new FilmFragment();
        Bundle args = new Bundle();
        args.putString(ARG_UID, uid);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getArguments();

        if (arguments != null) {
//            mColumnCount = arguments.getInt(ARG_COLUMN_COUNT);
            mUid = arguments.getString(ARG_UID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO:
        // Inspect first parameter of inflate function, the 'list' xml may not be the correct one.
        View view = inflater.inflate(R.layout.fragment_item_list_film, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            mRecyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                mRecyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            mRecyclerView.setAdapter(new MyFilmRecyclerViewAdapter(new ArrayList<>()));
        }
        return view;
    }

    @Override
    public void onFilmRetrieved(List<Film> films) {
        mRecyclerView.setAdapter(new MyFilmRecyclerViewAdapter(films));
    }
}