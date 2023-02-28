package com.example.cinecollect.pojo;

public class CineCollectUser {
    public String username;
    public String email;
    public Boolean emailVerified;
    public Boolean hasFilms;

    public CineCollectUser() {}

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public Boolean getEmailVerified() {
        return emailVerified;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setHasFilms(Boolean hasFilms) {
        this.hasFilms = hasFilms;
    }

    public Boolean getHasFilms() {
        return hasFilms;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private final CineCollectUser user;

        private Builder() {
            this.user = new CineCollectUser();
        }

        public Builder setUsername(String username) {
            user.setUsername(username);
            return this;
        }

        public Builder setEmailVerified(Boolean emailVerified) {
            user.setEmailVerified(emailVerified);
            return this;
        }

        public Builder setEmail(String email) {
            user.setEmail(email);
            return this;
        }

        public Builder setHasFilms(Boolean hasFilms) {
            user.setHasFilms(hasFilms);
            return this;
        }

        public CineCollectUser build() {
            return user;
        }
    }
}
