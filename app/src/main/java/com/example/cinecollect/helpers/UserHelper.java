package com.example.cinecollect.helpers;

import com.example.cinecollect.client.FirebaseDatabaseAuthInterceptor;
import com.example.cinecollect.interfaces.FirebaseDatabaseService;
import com.example.cinecollect.pojo.CineCollectUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserHelper {

    private static UserHelper userHelper;
    private static FirebaseUser currentUser;

    private final String userToken;
    private final String baseUrl = "https://cinecollect-d0d0c-default-rtdb.europe-west1.firebasedatabase.app/";
    private final Retrofit retrofit;
    private final FirebaseDatabaseService service;

    private UserHelper(String userToken) {
        this.userToken = userToken;
        this.retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        this.service = retrofit.create(FirebaseDatabaseService.class);
    }

    public static UserHelper getInstance() {
        FirebaseUser _currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (_currentUser == null) {
            throw new NoCurrentUserException();
        }

        if (currentUser != _currentUser || userHelper == null) {
            currentUser = _currentUser;
            userHelper = new UserHelper(currentUser.getIdToken(false).getResult().getToken());
        }

        return userHelper;
    }

    public void createUser(String userId, CineCollectUser user) {
        service.createUser(userId, userToken, user).enqueue(new Callback<CineCollectUser>() {
            @Override
            public void onResponse(Call<CineCollectUser> call, Response<CineCollectUser> response) {
                System.out.println("done!");
            }

            @Override
            public void onFailure(Call<CineCollectUser> call, Throwable t) {
                System.out.println("failure");
            }
        });
    }

    public void updateUser(String userId, CineCollectUser user) {
        service.updateUser(userId, userToken, user).enqueue(new Callback<CineCollectUser>() {
            @Override
            public void onResponse(Call<CineCollectUser> call, Response<CineCollectUser> response) {
                System.out.println("done!");
            }

            @Override
            public void onFailure(Call<CineCollectUser> call, Throwable t) {
                System.out.println("failure");
            }
        });
    }

    public Boolean getUserHasFilms(String userId) {
        // TODO:
        // Handle exception better, also check null return, and not successfull
        Boolean result = false;
        service.getUserHasFilms(userId, userToken).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                // TODO
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });
        return result;
    }

    public void addUserFilm(String userId, String filmId) {
        // TODO:
        // Handle exception better, also check null return, and not successfull
        Map<String, Object> h = new HashMap<>();
        h.put("a", "b");
        service.addUserFilm(userId, filmId, userToken, h).enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {

            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {

            }
        });
    }

    private static class NoCurrentUserException extends RuntimeException {

    }
}
