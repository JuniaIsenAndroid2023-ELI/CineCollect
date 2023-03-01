package com.example.cinecollect.helpers;

import com.example.cinecollect.services.FirebaseDatabaseService;
import com.example.cinecollect.pojo.Film;
import com.example.cinecollect.util.HelperExceptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public static FilmHelper getInstance() {
        FirebaseUser _currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (_currentUser == null) {
            throw new HelperExceptions.NoCurrentUserException();
        }

        if (currentUser != _currentUser || filmHelper == null) {
            currentUser = _currentUser;
            filmHelper = new FilmHelper(currentUser.getIdToken(false).getResult().getToken());
        }

        return filmHelper;
    }

    public Film getFilm(String filmId) {
        try {
            Response<Film> response = service.getFilm(filmId, userToken).execute();
            if (response.isSuccessful()) {
                return response.body();
            }
            else {
                // TODO
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Film> getUserFilms(String userId) {
        return getUserFilms(userId, false);
    }

    public List<Film> getUserFilms(String userId, Boolean withId) {
        // TODO:
        // Fix here
        try {
            Response<Map<String, Film>> response = service.getUserFilms(userId, userToken).execute();
            if (response.isSuccessful()) {
                if (response.body() == null) {
                    return new ArrayList<>();
                }
                return response
                        .body()
                        .entrySet()
                        .stream()
                        .map(entry -> {
                            Film film = entry.getValue();
                            if (withId) {
                                film.id = entry.getKey();
                            }
                            return film;
                        })
                        .collect(Collectors.toList());
            }
            else {
                // TODO
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
