package com.example.cinecollect.executor;

import android.os.Handler;
import android.os.Looper;

import com.example.cinecollect.helpers.FilmHelper;
import com.example.cinecollect.listeners.GetUserFilmsListener;
import com.example.cinecollect.pojo.Film;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GetUserFilmsExecutor {

    private final ExecutorService mExecutorService;
    private final Handler mHandler;
    private final GetUserFilmsListener mListener;

    public GetUserFilmsExecutor(GetUserFilmsListener listener) {
        mExecutorService = Executors.newSingleThreadExecutor();
        mHandler = new Handler(Looper.getMainLooper());
        mListener = listener;
    }

    public void getUserFilms(String userId) {
        getUserFilms(userId, false);
    }

    public void getUserFilms(String userId, Boolean withId) {
        mExecutorService.execute(() -> {
            List<Film> films = FilmHelper.getInstance().getUserFilms(userId, withId);
            if (films != null) {
                mHandler.post(() -> {
                    mListener.onGetUserFilms(films);
                });
            }
        });
    }
}
