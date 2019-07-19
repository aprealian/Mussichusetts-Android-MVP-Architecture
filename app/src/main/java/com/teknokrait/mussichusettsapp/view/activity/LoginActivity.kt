package com.teknokrait.mussichusettsapp.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.teknokrait.mussichusettsapp.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initLogin()
    }

    private fun initLogin() {
        tvLoginFacebook.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        })
        tvLoginGoogle.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        })
    }
}
