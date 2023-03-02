package com.example.cinecollect.services;

import com.example.cinecollect.pojo.CineCollectUser;
import com.example.cinecollect.pojo.Film;
import com.example.cinecollect.pojo.UserFilm;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FirebaseDatabaseService {

    @GET("film/{filmId}.json")
    Call<Film> getFilm(
            @Path("filmId") String filmId,
            @Query("auth") String token
    );

    @PUT("user/{userId}.json")
    Call<CineCollectUser> createUser(
            @Path("userId") String userId,
            @Query("auth") String token,
            @Body CineCollectUser user
    );

    @GET("user.json")
    Call<Map<String, CineCollectUser>> getUser(
            @Query("orderBy") String orderBy,
            @Query("equalTo") String equalTo,
            @Query("auth") String token
    );

    @PATCH("user/{userId}.json")
    Call<CineCollectUser> updateUser(
            @Path("userId") String userId,
            @Query("auth") String token,
            @Body CineCollectUser user
    );

    @GET("user/{userId}/films.json")
    Call<Map<String, Film>> getUserFilms(
            @Path("userId") String userId,
            @Query("auth") String token
    );

    @GET("user/{userId}/hasFilms.json")
    Call<Boolean> getUserHasFilms(
            @Path("userId") String userId,
            @Query("auth") String token
    );

    @PUT("user/{userId}/films/{filmId}.json")
    Call<Film> addUserFilm(
            @Path("userId") String userId,
            @Path("filmId") String filmId,
            @Query("auth") String token,
            @Body Film userFilm
    );

    @PATCH("user/{userId}/films/{filmId}.json")
    Call<Film> updateUserFilm(
            @Path("userId") String userId,
            @Path("filmId") String filmId,
            @Query("auth") String token,
            @Body Film userFilm
    );

    @DELETE("user/{userId}/films/{filmId}.json")
    Call<Void> deleteUserFilm(
            @Path("userId") String userId,
            @Path("filmId") String filmId,
            @Query("auth") String token
    );
}
