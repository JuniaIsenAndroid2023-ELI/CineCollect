package com.example.cinecollect.executor;

import android.os.Handler;
import android.os.Looper;

import com.example.cinecollect.helpers.UserHelper;
import com.example.cinecollect.listeners.GetUserHasFilmsListener;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GetUserHasFilmsExecutor {

    private final ExecutorService mExecutorService;
    private final Handler mHandler;
    private final GetUserHasFilmsListener mListener;

    public GetUserHasFilmsExecutor(GetUserHasFilmsListener listener) {
        mExecutorService = Executors.newSingleThreadExecutor();
        mHandler = new Handler(Looper.getMainLooper());
        mListener = listener;
    }

    public void getUserHasFilms(String userId) {
        // TODO:
        // Handle exception correctly
        mExecutorService.execute(() -> {
            Boolean userHasFilms = UserHelper.getInstance().getUserHasFilms(userId);
            if (userHasFilms != null) {
                mHandler.post(() -> {
                    mListener.onGetUserHasFilms(userHasFilms);
                });
            }
        });
    }
}
