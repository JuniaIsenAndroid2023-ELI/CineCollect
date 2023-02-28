package com.example.cinecollect.helpers;

import android.util.Log;

import com.example.cinecollect.interfaces.FirebaseDatabaseService;
import com.example.cinecollect.pojo.Film;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FilmHelper {

    private static String TAG = "FilmHelper";

    private static FirebaseUser currentUser;
    private static FilmHelper filmHelper;

    private final String userToken;
    private final String baseUrl = "https://cinecollect-d0d0c-default-rtdb.europe-west1.firebasedatabase.app/";
    private final Retrofit retrofit;
    private final FirebaseDatabaseService service;

    private FilmHelper(String userToken) {
        this.userToken = userToken;
        this.retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        this.service = retrofit.create(FirebaseDatabaseService.class);
    }

    // TODO:
    // Uhh not the prettiest func
    public static FilmHelper getInstance() throws FirebaseAuthInvalidUserException {
        FirebaseUser _currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (_currentUser == null) {
            // TODO:
            throw new FirebaseAuthInvalidUserException("FIXHERE", "FIXHERE");
        }

        if (currentUser != _currentUser || filmHelper == null) {
            currentUser = _currentUser;
            filmHelper = new FilmHelper(currentUser.getIdToken(false).getResult().getToken());
        }

        return filmHelper;
    }

    public List<Film> getUserFilms(String uid) {
        // TODO:
        // Fix here
            service.getUserFilms(uid, userToken).enqueue(new Callback<Map<String, Boolean>>() {
                @Override
                public void onResponse(Call<Map<String, Boolean>> call, Response<Map<String, Boolean>> response) {

                }

                @Override
                public void onFailure(Call<Map<String, Boolean>> call, Throwable t) {

                }
            });
//            if (response.isSuccessful()) {
//                return response
//                        .body()
//                        .keySet()
//                        .stream()
//                        .map(
//                                filmId ->
//                                Film
//                                        .newBuilder()
//                                        .setTitle(filmId)
//                                        .build())
//                        .collect(Collectors.toList());
        return new ArrayList<>();
    }
}
