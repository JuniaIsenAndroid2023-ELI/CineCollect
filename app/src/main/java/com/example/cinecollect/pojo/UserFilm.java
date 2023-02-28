package com.example.cinecollect.pojo;

public class UserFilm {
    public String filmId;
    public String review;
    public Boolean liked;

    public UserFilm() {}

    public UserFilm(String filmId, String review, Boolean liked) {
        this.filmId = filmId;
        this.review = review;
        this.liked = liked;
    }

    public void setFilmId(String filmId) {
        this.filmId = filmId;
    }

    public String getFilmId() {
        return filmId;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getReview() {
        return review;
    }

    public void setLiked(Boolean liked) {
        this.liked = liked;
    }

    public Boolean getLiked() {
        return liked;
    }

    public WithoutId getWithoutId() {
        return new WithoutId(this);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class WithoutId {
        public String review;
        public Boolean liked;

        private WithoutId(UserFilm userFilm) {
            this.review = userFilm.getReview();
            this.liked = userFilm.getLiked();
        }
    }

    public static class Builder {
        private final UserFilm userFilm;

        private Builder() {
            this.userFilm = new UserFilm();
        }

        public Builder setFilmId(String filmId) {
            userFilm.setFilmId(filmId);
            return this;
        }

        public Builder setReview(String review) {
            userFilm.setReview(review);
            return this;
        }

        public Builder setLiked(Boolean liked) {
            userFilm.setLiked(liked);
            return this;
        }

        public UserFilm build() {
            return userFilm;
        }
    }
}
