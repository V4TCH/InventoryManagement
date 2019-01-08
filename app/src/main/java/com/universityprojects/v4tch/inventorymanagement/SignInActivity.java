package com.universityprojects.v4tch.inventorymanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SignInActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "EmailPassword";

    private TextView mStatusText;
    private TextView mDetailsText;
    private EditText mEmailText;
    private EditText mPasswordText;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        mStatusText = findViewById(R.id.statusText);
        mDetailsText = findViewById(R.id.detailText);
        mEmailText = findViewById(R.id.login_email_text);
        mPasswordText = findViewById(R.id.login_password_text);

        findViewById(R.id.login_email_text);
        findViewById(R.id.login_password_text);

        findViewById(R.id.btn_login).setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIN:" + email);
        if (!validateForm()) {
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            mStatusText.setText(R.string.auth_success);
                            Toast.makeText(SignInActivity.this, "Authentication Success!", Toast.LENGTH_SHORT).show();

                        } else {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(SignInActivity.this, "Authentication failed...", Toast.LENGTH_SHORT).show();

                        }
                        if (!task.isSuccessful()) {
                            mStatusText.setText(R.string.auth_failed);
                        }
                    }
                });
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = mEmailText.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailText.setError("Required.");
            valid = false;
        } else {
            mEmailText.setError(null);
        }

        String password = mPasswordText.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordText.setError("Required.");
            valid = false;
        } else {
            mPasswordText.setError(null);
        }

        return valid;
    }

    @Override
    public void onClick(View v) {
        signIn(mEmailText.getText().toString(), mPasswordText.getText().toString());
            Intent inventory = new Intent(SignInActivity.this, InventoryMainActivity.class);
            startActivity(inventory);
    }
}