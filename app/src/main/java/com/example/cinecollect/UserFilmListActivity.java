package com.example.cinecollect;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cinecollect.fragments.FilmFragment;
import com.example.cinecollect.util.Constants;

public class UserFilmListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_film_list);

        final Intent intent = getIntent();

        if (intent != null) {
            final Bundle extras = intent.getExtras();
            if (extras != null) {
                final String username = extras.getString(Constants.Login.EXTRA_USERNAME);
                final String uid = extras.getString(Constants.Login.EXTRA_UID);
                getSupportActionBar().setSubtitle(username);
                getSupportFragmentManager().beginTransaction().add(R.id.container, FilmFragment.newInstance(uid)).commit();
            }
        }
    }
}
