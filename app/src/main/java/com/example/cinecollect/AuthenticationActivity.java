package com.example.cinecollect;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cinecollect.helpers.UserHelper;
import com.example.cinecollect.pojo.CineCollectUser;
import com.example.cinecollect.util.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class AuthenticationActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private Button mSignInButton;
    private Button mSignUpButton;
    private Button mPasswordResetButton;

    private static final String TAG = "EmailPassword";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authentication);

        mAuth = FirebaseAuth.getInstance();
        mEmailEditText = findViewById(R.id.userEmailText);
        mPasswordEditText = findViewById(R.id.userPasswordText);
        mSignInButton = findViewById(R.id.signInButton);
        mSignUpButton = findViewById(R.id.signUpButton);
        mPasswordResetButton = findViewById(R.id.passwordResetButton);
    }

    @Override
    protected void onStart() {
        super.onStart();

        mSignInButton.setOnClickListener(view -> {
            if (TextUtils.isEmpty(mEmailEditText.getText())) {
                Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(mPasswordEditText.getText())) {
                Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
                return;
            }
            signInUser(mEmailEditText.getText().toString(), mPasswordEditText.getText().toString());
        });

        mSignUpButton.setOnClickListener(view -> {
            if (TextUtils.isEmpty(mEmailEditText.getText())) {
                Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(mPasswordEditText.getText())) {
                Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
                return;
            }
            signUpUser(mEmailEditText.getText().toString(), mPasswordEditText.getText().toString());
        });

        mPasswordResetButton.setOnClickListener(view -> {
            if (TextUtils.isEmpty(mEmailEditText.getText())) {
                Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
                return;
            }
            sendPasswordResetEmail(mEmailEditText.getText().toString());
        });
    }

    private Intent getIntent(String username, String uid) {
        final Intent intent = new Intent(this, UserFilmListActivity.class);
        final Bundle extras = new Bundle();
        extras.putString(Constants.Login.EXTRA_USERNAME, username);
        extras.putString(Constants.Login.EXTRA_UID, uid);
        intent.putExtras(extras);
        return intent;
    }
    
    private void signInUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (!user.isEmailVerified()) {
                            mAuth.signOut();
                            Toast.makeText(AuthenticationActivity.this, "Please verify your email", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            UserHelper.getInstance().addUserFilm(user.getUid(), "randomFilmId");
                            UserHelper.getInstance().updateUser(user.getUid(), CineCollectUser.newBuilder().setEmailVerified(true).build());
                            Toast.makeText(AuthenticationActivity.this, "Welcome back!", Toast.LENGTH_SHORT).show();
                            startActivity(getIntent(user.getEmail(), user.getUid()));
                        }
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        Toast.makeText(AuthenticationActivity.this, "Authentication failed",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void signUpUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign up success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success");

                        FirebaseUser user = mAuth.getCurrentUser();
                        sendVerificationEmail(user);

                        UserHelper
                                .getInstance()
                                .createUser(
                                        user.getUid(),
                                        CineCollectUser
                                                .newBuilder()
                                                .setEmail(email)
                                                .setUsername(email.substring(0, email.indexOf("@")))
                                                .build()
                        );

                        UserHelper.getInstance().addUserFilm(user.getUid(), "randomFilmId");
                    } else {
                        // If sign up fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        if (task.getException().getClass().equals(FirebaseAuthUserCollisionException.class)) {
                            Toast.makeText(AuthenticationActivity.this, "This email has already sent sign up request",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(AuthenticationActivity.this, "Authentication failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void sendVerificationEmail(FirebaseUser user) {
        user.sendEmailVerification()
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    // email sent
                    // after email is sent just logout the user and finish this activity
                    FirebaseAuth.getInstance().signOut();
                    Toast.makeText(AuthenticationActivity.this, "An email sent to '" + user.getEmail() + "' for verification", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                {
                    // email not sent, so display message and restart the activity or do whatever you wish to do

                    //restart this activity
                    overridePendingTransition(0, 0);
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(getIntent());

                }
            });
    }

    private void sendPasswordResetEmail(String email) {
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Email sent.");
                        Toast.makeText(AuthenticationActivity.this, "A password reset email is sent", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
