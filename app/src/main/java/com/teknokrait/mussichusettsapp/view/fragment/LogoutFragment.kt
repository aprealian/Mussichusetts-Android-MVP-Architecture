package com.teknokrait.mussichusettsapp.view.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.teknokrait.mussichusettsapp.R
import com.teknokrait.mussichusettsapp.local.MyPreference
import com.teknokrait.mussichusettsapp.view.activity.MainActivity
import com.teknokrait.mussichusettsapp.view.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_logout.*
import androidx.annotation.NonNull
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task


class LogoutFragment : BaseFragment() {

    private var googleSignInClient: GoogleSignInClient? = null

    override fun getFragmentLayout(): Int {
        return R.layout.fragment_logout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        // Build a GoogleSignInClient with the options specified by gso.
        googleSignInClient = GoogleSignIn.getClient(activity as MainActivity, gso);

        initDataUser()
        initOnClick()
    }

    private fun initDataUser() {
        val user = MyPreference.getInstance(activity!!).getUserSession()
        if (user != null){
            tvName.text = user.name
            tvEmail.text = user.email
            tvAccountType.text = user.type

            if(!user.photo.isNullOrEmpty()){
                Glide.with(this)
                    .load(user.photo)
                    .placeholder(R.mipmap.ic_launcher_round)
                    .error(R.mipmap.ic_launcher_round)
                    .centerCrop()
                    .into(profile_image)
            }
        }
    }

    private fun initOnClick() {
        tvLogOut.setOnClickListener {
            val user = MyPreference.getInstance(activity!!).getUserSession()
            if (user?.type!!.equals("Google")){
                googleSignInClient?.signOut()?.addOnCompleteListener(OnCompleteListener {
                    context?.let { it1 -> MyPreference.getInstance(it1).logoutSession(activity as MainActivity) }
                })
            }
        }
    }

}
