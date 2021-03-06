package com.universityprojects.v4tch.inventorymanagement;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import org.jetbrains.annotations.NotNull;


public class SignInActivity extends AppCompatActivity {
    private EditText loginEmail, loginPass;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private Button loginBtn, bypassBtn;
    private TextView signUpTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        loginBtn = (Button)findViewById(R.id.btn_login);
        loginEmail = (EditText)findViewById(R.id.login_email_text);
        loginPass = (EditText)findViewById(R.id.login_password_text);
        bypassBtn = (Button)findViewById(R.id.btn_bypass);
        signUpTxt = (TextView)findViewById(R.id.signupTxt);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SignInActivity.this, "Processing...",Toast.LENGTH_LONG).show();
                final String email = loginEmail.getText().toString().trim();
                String password = loginPass.getText().toString().trim();

                if (!TextUtils.isEmpty(email)&& !TextUtils.isEmpty(password)) {
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                checkUserExistence();
                                Toast.makeText(SignInActivity.this, "Success!", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(SignInActivity.this, "Unable to login, user does not exist!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(SignInActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        bypassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginEmail.setText("testuser@testemail.com");
                loginPass.setText("testpassword123");
            }
        });

        signUpTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(signInIntent);
            }
        });
    }

    public void checkUserExistence() {
        final String user_id = mAuth.getCurrentUser().getUid();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if ( dataSnapshot.hasChild(user_id)) {
                    startActivity(new Intent(SignInActivity.this, NavActivity.class));
                    Toast.makeText(SignInActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SignInActivity.this, "User is not registered in database!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}