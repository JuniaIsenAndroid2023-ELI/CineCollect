package com.example.cinecollect.executor;

import android.os.Handler;
import android.os.Looper;

import com.example.cinecollect.helpers.FilmHelper;
import com.example.cinecollect.helpers.UserHelper;
import com.example.cinecollect.listeners.GetFilmListener;
import com.example.cinecollect.listeners.GetUserListener;
import com.example.cinecollect.pojo.CineCollectUser;
import com.example.cinecollect.pojo.Film;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GetUserExecutor {
    private final ExecutorService mExecutorService;
    private final Handler mHandler;
    private final GetUserListener mListener;

    public GetUserExecutor(GetUserListener listener) {
        mExecutorService = Executors.newSingleThreadExecutor();
        mHandler = new Handler(Looper.getMainLooper());
        mListener = listener;
    }

    public void getUser(String username) {
        // TODO:
        // Handle exception correctly
        mExecutorService.execute(() -> {
            Set<Map.Entry<String, CineCollectUser>> users = UserHelper.getInstance().getUser(username);
            if (users != null) {
                mHandler.post(() -> {
                    mListener.onGetUser(users);
                });
            }
        });
    }
}
