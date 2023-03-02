package com.example.cinecollect;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cinecollect.executor.GetUserExecutor;
import com.example.cinecollect.listeners.GetUserListener;
import com.example.cinecollect.pojo.CineCollectUser;
import com.example.cinecollect.util.Constants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class UserSearchActivity extends AppCompatActivity implements GetUserListener {

    private TextView username;
    private Button search;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_search);

        username = findViewById(R.id.userSearchUsername);
        search = findViewById(R.id.userSearchButton);
    }

    @Override
    protected void onStart() {
        super.onStart();

        search.setOnClickListener(view -> {
            if (TextUtils.isEmpty(username.getText())) {
                Toast.makeText(this, "Please enter username", Toast.LENGTH_SHORT).show();
            }
            else {
                GetUserExecutor executor = new GetUserExecutor(this);
                executor.getUser(username.getText().toString());
            }
        });
    }

    @Override
    public void onGetUser(Set<Map.Entry<String, CineCollectUser>> users) {
        Map.Entry<String, CineCollectUser> entry = new ArrayList<>(users).get(0);
        startActivity(getIntent(entry.getValue().username, entry.getKey()));
    }

    private Intent getIntent(String username, String userId) {
        final Intent intent = new Intent(this, UserFilmListActivity.class);
        final Bundle extras = new Bundle();
        extras.putString(Constants.Login.EXTRA_USERNAME, username);
        extras.putString(Constants.Login.EXTRA_UID, userId);
        intent.putExtras(extras);
        return intent;
    }
}
