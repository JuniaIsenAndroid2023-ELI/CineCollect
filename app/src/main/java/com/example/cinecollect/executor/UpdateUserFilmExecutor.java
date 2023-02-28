package com.example.cinecollect.executor;

import android.os.Handler;
import android.os.Looper;

import com.example.cinecollect.helpers.UserHelper;
import com.example.cinecollect.listeners.UpdateUserFilmListener;
import com.example.cinecollect.pojo.Film;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UpdateUserFilmExecutor {
    private final ExecutorService mExecutorService;
    private final Handler mHandler;
    private final UpdateUserFilmListener mListener;

    public UpdateUserFilmExecutor(UpdateUserFilmListener listener) {
        mExecutorService = Executors.newSingleThreadExecutor();
        mHandler = new Handler(Looper.getMainLooper());
        mListener = listener;
    }

    public void updateUserFilm(String userId, String filmId, Film film) {
        mExecutorService.execute(() -> {
            UserHelper.getInstance().updateUserFilm(userId, filmId, film.withoutId());
            mHandler.post(mListener::onUpdateUserFilm);
        });
    }
}
