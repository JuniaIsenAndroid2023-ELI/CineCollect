package com.example.cinecollect.interfaces;

import com.example.cinecollect.pojo.Film;

import java.util.List;

public interface FilmChangeListener {
    void onFilmRetrieved(List<Film> films);
}
