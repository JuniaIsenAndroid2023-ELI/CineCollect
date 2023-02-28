package com.example.cinecollect.executor;

import android.os.Handler;
import android.os.Looper;

import com.example.cinecollect.helpers.FilmHelper;
import com.example.cinecollect.listeners.GetFilmListener;
import com.example.cinecollect.pojo.Film;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GetFilmExecutor {
    private final ExecutorService mExecutorService;
    private final Handler mHandler;
    private final GetFilmListener mListener;

    public GetFilmExecutor(GetFilmListener listener) {
        mExecutorService = Executors.newSingleThreadExecutor();
        mHandler = new Handler(Looper.getMainLooper());
        mListener = listener;
    }

    public void getFilm(String filmId) {
        // TODO:
        // Handle exception correctly
        mExecutorService.execute(() -> {
            Film film = FilmHelper.getInstance().getFilm(filmId);
            if (film != null) {
                mHandler.post(() -> {
                    mListener.onGetFilm(film);
                });
            }
        });
    }
}
