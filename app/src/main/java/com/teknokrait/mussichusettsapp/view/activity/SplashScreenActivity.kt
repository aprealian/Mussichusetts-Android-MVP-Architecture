package com.teknokrait.mussichusettsapp.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.os.Handler
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.teknokrait.mussichusettsapp.R

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        checkIsLogin()
    }

    private fun checkIsLogin() {
        val account = GoogleSignIn.getLastSignedInAccount(this)
        if (account?.isExpired != null){
            intentToMainActivity()
        } else {
            intentToLogin()
        }
    }

    fun intentToMainActivity(){
        val newIntent = Intent(this@SplashScreenActivity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(newIntent)
        finish()
    }

    fun intentToLogin(){
        Handler().postDelayed(Runnable {
            val mainIntent = Intent(this@SplashScreenActivity, LoginActivity::class.java)
            this@SplashScreenActivity.startActivity(mainIntent)
            this@SplashScreenActivity.finish()
        }, 5000)
    }
}
