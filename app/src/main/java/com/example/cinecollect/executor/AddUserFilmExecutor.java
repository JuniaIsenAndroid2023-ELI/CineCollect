package com.example.cinecollect.executor;

import android.os.Handler;
import android.os.Looper;

import com.example.cinecollect.helpers.FilmHelper;
import com.example.cinecollect.helpers.UserHelper;
import com.example.cinecollect.listeners.AddUserFilmListener;
import com.example.cinecollect.listeners.GetFilmListener;
import com.example.cinecollect.pojo.Film;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AddUserFilmExecutor {
    private final ExecutorService mExecutorService;
    private final Handler mHandler;
    private final AddUserFilmListener mListener;

    public AddUserFilmExecutor(AddUserFilmListener listener) {
        mExecutorService = Executors.newSingleThreadExecutor();
        mHandler = new Handler(Looper.getMainLooper());
        mListener = listener;
    }

    public void addUserFilm(String userId, String filmId, Film film) {
        film.id = null;
        mExecutorService.execute(() -> {
            UserHelper.getInstance().addUserFilm(userId, filmId, film);
            mHandler.post(mListener::onAddUserFilm);
        });
    }
}
