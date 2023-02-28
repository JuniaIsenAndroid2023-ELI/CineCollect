package com.example.cinecollect.interfaces;

import com.example.cinecollect.pojo.CineCollectUser;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FirebaseDatabaseService {
    @GET("film/{filmId}.json")
    Call<Map<String, Object>> getFilm(
            @Path("filmId") String filmId,
            @Query("auth") String token
    );

    @PUT("user/{userId}.json")
    Call<CineCollectUser> createUser(
            @Path("userId") String userId,
            @Query("auth") String token,
            @Body CineCollectUser user
    );

    @PATCH("user/{userId}.json")
    Call<CineCollectUser> updateUser(
            @Path("userId") String userId,
            @Query("auth") String token,
            @Body CineCollectUser user
    );

    @GET("users/{userId}/films.json")
    Call<Map<String, Boolean>> getUserFilms(
            @Path("userId") String userId,
            @Query("auth") String token
    );

    @GET("user/{userId}/hasFilms.json")
    Call<Boolean> getUserHasFilms(
            @Path("userId") String userId,
            @Query("auth") String token
    );

    @PUT("user/{userId}/films/{filmId}.json")
    Call<Map<String, Object>> addUserFilm(
            @Path("userId") String userId,
            @Path("filmId") String filmId,
            @Query("auth") String token,
            @Body Map<String, Object> temp
    );
}
