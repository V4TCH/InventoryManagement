package com.universityprojects.v4tch.inventorymanagement;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    private void firebaseAuth() {
        EditText email = findViewById(R.id.email_text);
        EditText passw = findViewById(R.id.passw_text);

    }
}
