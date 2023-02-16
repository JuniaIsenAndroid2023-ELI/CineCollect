package com.example.cinecollect;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthenticationActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private Button mSignInButton;
    private Button mSignUpButton;

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
    }


    private void signInUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (!user.isEmailVerified()) {
                                mAuth.signOut();
                                Toast.makeText(AuthenticationActivity.this, "Please verify your email", Toast.LENGTH_SHORT).show();
                            }
                            // TODO: updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(AuthenticationActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            // TODO: updateUI(null);
                        }
                    }
                });
    }

    private void signUpUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign up success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            sendVerificationEmail(user);
                            // TODO: updateUI(user);
                        } else {
                            // If sign up fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(AuthenticationActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            // TODO: updateUI(null);
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
}
