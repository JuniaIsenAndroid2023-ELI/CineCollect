package com.example.cinecollect.executor;

import android.os.Handler;
import android.os.Looper;

import com.example.cinecollect.helpers.UserHelper;
import com.example.cinecollect.listeners.AddUserFilmListener;
import com.example.cinecollect.listeners.DeleteUserFilmListener;
import com.example.cinecollect.pojo.Film;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DeleteUserFilmExecutor {
    private final ExecutorService mExecutorService;
    private final Handler mHandler;
    private final DeleteUserFilmListener mListener;

    public DeleteUserFilmExecutor(DeleteUserFilmListener listener) {
        mExecutorService = Executors.newSingleThreadExecutor();
        mHandler = new Handler(Looper.getMainLooper());
        mListener = listener;
    }

    public void deleteUserFilm(String userId, String filmId) {
        mExecutorService.execute(() -> {
            UserHelper.getInstance().deleteUserFilm(userId, filmId);
            mHandler.post(mListener::onDeleteUserFilm);
        });
    }
}
