package com.example.cinecollect;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cinecollect.fragments.FilmFragment;
import com.example.cinecollect.util.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserFilmListActivity extends AppCompatActivity {

    private String username;
    private String userId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_film_list);

        final Intent intent = getIntent();

        if (intent != null) {
            final Bundle extras = intent.getExtras();
            if (extras != null) {
                username = extras.getString(Constants.Login.EXTRA_USERNAME);
                userId = extras.getString(Constants.Login.EXTRA_UID);
                getSupportActionBar().setSubtitle(username);
                getSupportFragmentManager().beginTransaction().add(R.id.container, FilmFragment.newInstance(userId)).commit();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.menuMyFilms) {
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            if (!userId.equals(currentUser.getUid())) {
                startActivity(getIntent(currentUser.getEmail().substring(0, currentUser.getEmail().indexOf("@")), currentUser.getUid()));
            }
        }
        else if (itemId == R.id.menuFilms) {
            startActivity(getIntent("All Films", "filmRepository"));
        }
        else if (itemId == R.id.menuLogout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, MainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private Intent getIntent(String username, String uid) {
        final Intent intent = new Intent(this, UserFilmListActivity.class);
        final Bundle extras = new Bundle();
        extras.putString(Constants.Login.EXTRA_USERNAME, username);
        extras.putString(Constants.Login.EXTRA_UID, uid);
        intent.putExtras(extras);
        return intent;
    }
}
