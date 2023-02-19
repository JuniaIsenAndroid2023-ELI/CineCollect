package com.example.cinecollect.executor;

import android.os.Handler;
import android.os.Looper;

import com.example.cinecollect.helpers.FilmHelper;
import com.example.cinecollect.interfaces.FilmChangeListener;
import com.example.cinecollect.pojo.Film;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RetrieveFilmsExecutor {

    private ExecutorService mExecutorService;
    private Handler mHandler;
    private FilmChangeListener mListener;

    public RetrieveFilmsExecutor(FilmChangeListener listener) {
        mExecutorService = Executors.newSingleThreadExecutor();
        mHandler = new Handler(Looper.getMainLooper());
        mListener = listener;
    }

    public void getFilmsOfUser(String username) {
        mExecutorService.execute(() -> {
            List<Film> films = FilmHelper.getFilmsOfUser(username);
        });
        // TODO:
        // Get lists of given user, here you use http clients etc
    }
}
