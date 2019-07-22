package com.teknokrait.mussichusettsapp.view.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.teknokrait.mussichusettsapp.R
import kotlinx.android.synthetic.main.activity_login.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.teknokrait.mussichusettsapp.BuildConfig
import com.teknokrait.mussichusettsapp.util.Constants
import com.teknokrait.mussichusettsapp.util.Constants.Companion.GOOGLE_REQ_CODE
import timber.log.Timber
import com.google.android.gms.tasks.Task
import com.teknokrait.mussichusettsapp.model.User
import com.teknokrait.mussichusettsapp.local.MyPreference

class LoginActivity : AppCompatActivity() {

    private var googleSignInClient:GoogleSignInClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        configureGoogleSignIn()
        checkIsLogin()
        initLogin()
        initGoogleAuth()
    }

    private fun checkIsLogin() {
        val account = GoogleSignIn.getLastSignedInAccount(this)
        if (account?.isExpired != null){
            intentToMainActivity()
        }
    }

    private fun configureGoogleSignIn() {
//        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(BuildConfig.DEFAULT_GOOGLE_WEB_CLIENT_ID)
//            .requestProfile()
//            .requestEmail()
//            .build()
//
//        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)


        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        // Build a GoogleSignInClient with the options specified by gso.
        googleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private fun initGoogleAuth() {
        btnGoogleLogin.setOnClickListener {
            val signInIntent = googleSignInClient?.signInIntent
            startActivityForResult(signInIntent, GOOGLE_REQ_CODE)
        }

    }


    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GOOGLE_REQ_CODE) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            Timber.e("handleLoginResponse success ")

            if (account != null) {

                Timber.e("handleLoginResponse account "+account.toString())
                Timber.e("handleLoginResponse url "+account.photoUrl)
                Timber.e("handleLoginResponse url string "+account.photoUrl.toString())

                val personName = account.displayName
                val personGivenName = account.givenName
                val personFamilyName = account.familyName
                val personEmail = account.email
                val personId = account.id
                val personPhoto = account.photoUrl

                val user = User(personId, personName, personEmail, personPhoto.toString(), "Google")
                MyPreference.getInstance(this).addUserSession(user)
                intentToMainActivity()
            }

            //updateUI(account)
        } catch (e: ApiException) {
            //Log.w(this.TAG, "signInResult:failed code=" + e.statusCode)
            //updateUI(null)
            Timber.e("handleLoginResponse code not found "+e.statusCode)
        }
    }

    private fun initLogin() {
        tvLoginFacebook.setOnClickListener {
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        }
        tvLoginGoogle.setOnClickListener {
            val signInIntent = googleSignInClient?.signInIntent
            startActivityForResult(signInIntent, GOOGLE_REQ_CODE)
        }
    }

    fun intentToMainActivity(){
        val newIntent = Intent(this@LoginActivity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(newIntent)
        //finish()
    }
}
