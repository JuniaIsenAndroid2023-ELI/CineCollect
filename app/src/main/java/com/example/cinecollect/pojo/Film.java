package com.example.cinecollect.pojo;

public class Film {
    public String title;
    public String releaseDate;
    public String genre;
    public String director;

    private Film() {
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setDirector(String director) {
        this.director = director;
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

        public Builder setReleaseDate(String releaseDate) {
            film.setReleaseDate(releaseDate);
            return this;
        }

        public Builder setGenre(String genre) {
            film.setGenre(genre);
            return this;
        }

        public Builder setDirector(String director) {
            film.setDirector(director);
            return this;
        }

        public Film build() {
            return film;
        }
    }
}
