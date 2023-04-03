package com.globant.imdb.repo

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import com.globant.imdb.utils.AuthResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.GoogleAuthProvider
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class LoginRepo @Inject constructor(private val firebaseAuth: FirebaseAuth) {

    private lateinit var googleSignInClient: GoogleSignInClient

    suspend fun signInEmailAndPass(email: String, password: String): AuthResult {
        return withContext(Dispatchers.IO) {
            try {
                firebaseAuth.signInWithEmailAndPassword(email, password).await()
                AuthResult.Success("Sign In Successful!")
            } catch (e: FirebaseAuthInvalidUserException) {
                AuthResult.Error("User does not exist")
            } catch (e: FirebaseAuthInvalidCredentialsException) {
                AuthResult.Error("Invalid email or password")
            } catch (e: Exception) {
                AuthResult.Error("Unknown error occurred: ${e.message}")
            }
        }
    }

    suspend fun signUpEmailAndPass(email: String, password: String): AuthResult {
        return withContext(Dispatchers.IO) {
            try {
                firebaseAuth.createUserWithEmailAndPassword(email, password).await()
                AuthResult.Success("Sign In Successful!")
            } catch (e: FirebaseAuthUserCollisionException) {
                AuthResult.Error("User already exists")
            } catch (e: Exception) {
                AuthResult.Error("Unknown error occurred: ${e.message}")
            }
        }
    }

    suspend fun signInResultHandler(result: ActivityResult?): AuthResult {
        if (result != null && result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            if (task.isSuccessful) {
                val account = task.result
                if (account != null) {
                    return withContext(Dispatchers.IO) {
                        try {
                            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                            firebaseAuth.signInWithCredential(credential).await()
                            AuthResult.Success("Sign In Successful!")
                        } catch (e: Exception) {
                            AuthResult.Error("Failed to sign in with credential: $e")
                        }
                    }
                }
            } else {
                AuthResult.Error("Failed to sign in: ${task.exception}")
            }
        }
        return AuthResult.Error("Unknown error occurred...")
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
}