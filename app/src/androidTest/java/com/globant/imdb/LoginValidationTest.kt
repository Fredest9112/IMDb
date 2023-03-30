package com.globant.imdb

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.MediumTest
import com.globant.imdb.view.login.LoginFragment
import org.hamcrest.Matchers
import org.hamcrest.Matchers.allOf
import org.junit.Test

@MediumTest
class LoginValidationTest {

    @Test
    fun loginButton_inputsAreValid() {
        val scenario = launchFragmentInContainer<LoginFragment>(Bundle(), R.style.Theme_IMDb)

        scenario.onFragment{}

        onView(
            allOf(
                isDescendantOfA(withId(R.id.email_frame_input)),
                withClassName(Matchers.endsWith("EditText"))
            )
        ).perform(typeText("testemail@email.com"))

        onView(
            allOf(
                isDescendantOfA(withId(R.id.password_frame_input)),
                withClassName(Matchers.endsWith("EditText"))
            )
        ).perform(typeText("Mypassword123@"))

        onView(withId(R.id.login_button)).check(matches(isEnabled()))

        scenario.close()
    }
}