package com.universityprojects.v4tch.inventorymanagement;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "emailPassword";

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
        mEmailText = findViewById(R.id.signup_email_text);
        mPasswordText = findViewById(R.id.signup_passw_text);

        findViewById(R.id.signup_email_text);
        findViewById(R.id.signup_passw_text);

        findViewById(R.id.btn_login).setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
    }

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount" + email);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail: Success");
                            FirebaseUser user = mAuth.getCurrentUser();

                        } else {
                            Log.w(TAG, "createUserWithEmail:failure");
                            Toast.makeText(SignUpActivity.this, "Authentication faiuled", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        createAccount(mEmailText.getText().toString(), mPasswordText.getText().toString());
    }
}
