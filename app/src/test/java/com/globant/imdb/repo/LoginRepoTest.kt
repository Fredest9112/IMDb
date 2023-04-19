package com.globant.imdb.repo

import com.globant.imdb.utils.AuthResult
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

@RunWith(JUnit4::class)
@ExperimentalCoroutinesApi
class LoginRepoTest {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var loginRepo: LoginRepo

    @Before
    fun setUp() {
        firebaseAuth = mock(FirebaseAuth::class.java)
        loginRepo = LoginRepo(firebaseAuth)
    }

    @Test
    fun `signInEmailAndPass should return success when email and password are valid`() =
        runTest {
            // Arrange
            val email = "test@example.com"
            val password = "password"
            val authResult = mock(com.google.firebase.auth.AuthResult::class.java)
            `when`(firebaseAuth.signInWithEmailAndPassword(email, password)).thenReturn(
                Tasks.forResult(authResult)
            )

            // Act
            val result = loginRepo.signInEmailAndPass(email, password)

            // Assert
            assertEquals(result, AuthResult.Success("Sign In Successful!"))
        }

    @Test
    fun `signInEmailAndPass should return error when an unknown error occurs`() = runTest {
        // Arrange
        val email = "test@example.com"
        val password = "password"
        val exception = RuntimeException("Unknown error occurred")
        `when`(firebaseAuth.signInWithEmailAndPassword(email, password)).thenThrow(exception)

        // Act
        val result = loginRepo.signInEmailAndPass(email, password)

        // Assert
        assertEquals(result, AuthResult.Error("Unknown error occurred: ${exception.message}"))
    }
}

