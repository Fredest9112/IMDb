package com.globant.imdb.navigation

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.globant.imdb.R
import com.globant.imdb.view.registration.RegistrationFragment
import com.google.common.truth.Truth.assertThat
import org.hamcrest.Matchers
import org.hamcrest.Matchers.allOf
import org.junit.Test

@MediumTest
class RegistrationToLoginTest {

    @Test
    fun registration_navigatesTo_Login(){

        val scenario = launchFragmentInContainer<RegistrationFragment>(Bundle(), R.style.Theme_IMDb)

        scenario.onFragment{}

        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())

        scenario.onFragment {
            navController.setGraph(R.navigation.nav_graph)
            navController.setCurrentDestination(R.id.registrationFragment)
            Navigation.setViewNavController(it.requireView(), navController)
        }

        onView(
            allOf(
                ViewMatchers.isDescendantOfA(withId(R.id.username_frame)),
                ViewMatchers.withClassName(Matchers.endsWith("EditText"))
            )
        ).perform(ViewActions.typeText("fredest"))

        onView(
            allOf(
                ViewMatchers.isDescendantOfA(withId(R.id.email_frame)),
                ViewMatchers.withClassName(Matchers.endsWith("EditText"))
            )
        ).perform(ViewActions.typeText("testemail@email.com"))

        onView(
            allOf(
                ViewMatchers.isDescendantOfA(withId(R.id.password_frame)),
                ViewMatchers.withClassName(Matchers.endsWith("EditText"))
            )
        ).perform(ViewActions.typeText("Mypassword123@"))

        onView(withId(R.id.accept_button)).check(matches(isEnabled())).perform(
            closeSoftKeyboard(),
            click()
        )
        assertThat(navController.currentDestination?.id).isEqualTo(R.id.registrationFragment)
    }

}