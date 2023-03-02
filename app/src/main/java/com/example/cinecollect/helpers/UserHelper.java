package com.example.cinecollect.helpers;

import com.example.cinecollect.pojo.Film;
import com.example.cinecollect.services.FirebaseDatabaseService;
import com.example.cinecollect.pojo.CineCollectUser;
import com.example.cinecollect.pojo.UserFilm;
import com.example.cinecollect.util.HelperExceptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
            throw new HelperExceptions.NoCurrentUserException();
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

    public Set<Map.Entry<String, CineCollectUser>> getUser(String username) {
        try {
            Response<Map<String, CineCollectUser>> response = service.getUser("\"username\"", "\"" + username + "\"", userToken).execute();
            if (response.isSuccessful()) {
                return response.body().entrySet();
            }
            else {
                // TODO
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void updateUser(String userId, CineCollectUser user) {
        service.updateUser(userId, userToken, user).enqueue(new Callback<CineCollectUser>() {
            @Override
            public void onResponse(Call<CineCollectUser> call, Response<CineCollectUser> response) {
            }

            @Override
            public void onFailure(Call<CineCollectUser> call, Throwable t) {
            }
        });
    }

    public void setUserEmailVerified(String userId) {
        updateUser(userId, CineCollectUser.newBuilder().setEmailVerified(true).build());
    }

    public Boolean getUserHasFilms(String userId) {
        // TODO:
        // Handle exception better, also check null return, and not successfull
        try {
            Response<Boolean> response = service.getUserHasFilms(userId, userToken).execute();
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

    public void addUserFilm(String userId, String filmId, Film film) {
        // TODO:
        // Handle exception better, also check null return, and not successfull
        try {
            Response<Film> response = service.addUserFilm(userId, filmId, userToken, film).execute();
            if (response.isSuccessful()) {
                service.updateUser(userId, userToken, CineCollectUser.newBuilder().setHasFilms(true).build()).execute();
            }
            else {
                // TODO
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateUserFilm(String userId, String filmId, Film film) {
        // TODO:
        // Handle exception better, also check null return, and not successfull
        try {
            Response<Film> response = service.updateUserFilm(userId, filmId, userToken, film).execute();
            if (response.isSuccessful()) {
                service.updateUser(userId, userToken, CineCollectUser.newBuilder().setHasFilms(true).build()).execute();
            }
            else {
                // TODO
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteUserFilm(String userId, String filmId) {
        try {
            service.deleteUserFilm(userId, filmId, userToken).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
