package com.example.cinecollect.pojo;

public class Film {
    public String id;

    public String title;
    public String description;
    public Boolean liked;

    public Film() {}

    public Film(Film film) {
        id = film.id;
        title = film.title;
        description = film.description;
        liked = film.liked;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Film withoutId() {
        Film film = new Film(this);
        film.id = null;
        return film;
    }

    public static class Builder {
        private final Film film;

        private Builder() {
            this.film = new Film();
        }

        public Builder setTitle(String title) {
            film.setTitle(title);
            return this;
        }

        public Film build() {
            return film;
        }
    }
}
