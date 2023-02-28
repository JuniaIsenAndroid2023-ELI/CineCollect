package com.example.cinecollect.executor;

import android.os.Handler;
import android.os.Looper;

import com.example.cinecollect.helpers.FilmHelper;
import com.example.cinecollect.interfaces.FilmChangeListener;
import com.example.cinecollect.pojo.Film;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RetrieveFilmsExecutor {

    private final ExecutorService mExecutorService;
    private final Handler mHandler;
    private final FilmChangeListener mListener;

    public RetrieveFilmsExecutor(FilmChangeListener listener) {
        mExecutorService = Executors.newSingleThreadExecutor();
        mHandler = new Handler(Looper.getMainLooper());
        mListener = listener;
    }

    public void getUserFilms(String uid) {
        // TODO:
        // Handle exception correctly
        mExecutorService.execute(() -> {
            try {
                List<Film> films = FilmHelper.getInstance().getUserFilms(uid);
                if (films != null) {
                    mHandler.post(() -> {
                        mListener.onFilmRetrieved(films);
                    });
                }
            } catch (FirebaseAuthInvalidUserException e) {
                e.printStackTrace();
            }
        });
    }
}
