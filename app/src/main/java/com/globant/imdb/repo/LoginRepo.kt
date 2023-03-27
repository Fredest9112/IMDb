package com.globant.imdb.repo

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginRepo @Inject constructor(private val firebaseAuth: FirebaseAuth) {
    //private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

//    fun initFirebaseInstance(){
//        firebaseAuth = FirebaseAuth.getInstance()
//    }

    suspend fun signInEmailAndPass(email: String, password: String): Boolean{
        return withContext(Dispatchers.IO) {
            val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            authResult.user != null
        }
    }

    suspend fun signUpEmailAndPass(email: String, password: String): Boolean{
         return withContext(Dispatchers.IO) {
            val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            authResult.user != null
        }
    }

    fun initAuthenticationWithGoogle(defaultWebClientId: String, activity: Activity) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(defaultWebClientId)
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(activity, gso)
    }

    fun signInGoogle(launcher: ActivityResultLauncher<Intent>) {
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    suspend fun signInResultHandler(result: ActivityResult?): Boolean {
        if (result != null && result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            if (task.isSuccessful) {
                val account = task.result
                if (account != null) {
                    return withContext(Dispatchers.IO) {
                        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                        val authResult = firebaseAuth.signInWithCredential(credential).await()
                        authResult.user != null
                    }
                }
            } else {
                Log.i("task exception", "${task.exception}")
            }
        }
        return false
    }
}