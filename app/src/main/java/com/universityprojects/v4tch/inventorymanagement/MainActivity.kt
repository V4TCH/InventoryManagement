package com.universityprojects.v4tch.inventorymanagement

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_login.setOnClickListener {
            val loginbutton = Intent(this@MainActivity, SignInActivity:: class.java)
            startActivity(loginbutton)
        }

        btn_signup.setOnClickListener {
            val signupbutton = Intent(this@MainActivity, SignUpActivity::class.java)
            startActivity(signupbutton)
        }



    }



}
