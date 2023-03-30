package com.globant.imdb.navigation

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.MediumTest
import com.globant.imdb.R
import com.globant.imdb.view.login.LoginFragment
import org.hamcrest.Matchers
import com.google.common.truth.Truth.assertThat
import org.junit.Test

@MediumTest
class LoginToSearchTest {

    @Test
    fun loginButton_clicked_navigateToSearchFragment() {
        val scenario = launchFragmentInContainer<LoginFragment>(Bundle(), R.style.Theme_IMDb)

        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())

        scenario.onFragment {
            navController.setGraph(R.navigation.nav_graph)
            navController.setCurrentDestination(R.id.loginFragment)
            Navigation.setViewNavController(it.requireView(), navController)
        }

        onView(
            Matchers.allOf(
                isDescendantOfA(withId(R.id.email_frame_input)),
                withClassName(Matchers.endsWith("EditText"))
            )
        ).perform(typeText("testemail@email.com"))

        onView(
            Matchers.allOf(
                isDescendantOfA(withId(R.id.password_frame_input)),
                withClassName(Matchers.endsWith("EditText"))
            )
        ).perform(typeText("Mypassword123@"))

        onView(withId(R.id.login_button)).check(matches(isEnabled())).perform(
            closeSoftKeyboard(),
            scrollTo(),
            click()
        )
        assertThat(navController.currentDestination?.id).isEqualTo(R.id.bottomNavFragment)
        scenario.close()
    }
}