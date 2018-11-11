package com.universityprojects.v4tch.inventorymanagement;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "emailPassword";

    private TextView mStatusText;
    private TextView mDetailsText;
    private EditText mEmailText;
    private EditText mPasswordText;
    private FirebaseAuth mAuth;

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount" + email);
    }

    @Override
    public void onClick(View v) {
        createAccount(mEmailText.getText().toString(), mPasswordText.getText().toString());
    }
}
