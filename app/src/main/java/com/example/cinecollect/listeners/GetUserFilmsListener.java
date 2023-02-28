package com.example.cinecollect.listeners;

import com.example.cinecollect.pojo.Film;

import java.util.List;

public interface GetUserFilmsListener {
    void onGetUserFilms(List<Film> films);
}
