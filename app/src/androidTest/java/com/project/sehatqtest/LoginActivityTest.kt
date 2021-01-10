package com.project.sehatqtest

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.project.sehatqtest.view.login.LoginActivity
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

/*
    Bila ingin menjalankan ui test ini lebih baik aplikasi dalam kondisi logout / belum login
*/
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4ClassRunner::class)
class LoginActivityTest {

    @get:Rule
    var activityRule = ActivityTestRule(LoginActivity::class.java)

    @Test
    fun tes1_login(){
        Espresso.onView(withId(R.id.username_edit_text))
                .perform(clearText(), typeText("lalalala"))
        Espresso.onView(withId(R.id.password_edit_text))
                .perform(clearText(), typeText("123456"))
        Espresso.onView(withId(R.id.sign_in_button)).perform(click())
        Espresso.onView(withId(R.id.navigation)).check(matches(isDisplayed()))
    }

    @Test
    fun tes2_login_failed(){
        Espresso.onView(withId(R.id.username_edit_text))
                .perform(clearText(), typeText(""))
        Espresso.onView(withId(R.id.password_edit_text))
                .perform(clearText(), typeText("12346"))
        Espresso.onView(withId(R.id.sign_in_button)).perform(click())
        Espresso.onView(withId(R.id.username_edit_text))
                .check(matches(hasErrorText(getResourceString(R.string.username_is_required))))
        Espresso.onView(withId(R.id.password_edit_text))
                .check(matches(hasErrorText(getResourceString(R.string.password_is_required))))
    }

    private fun getResourceString(id: Int): String {
        return activityRule.activity.resources.getString(id)
    }

    @Test
    fun tes3_login_remember(){
        Espresso.onView(withId(R.id.username_edit_text))
            .perform(clearText(), typeText("lalalala"))
        Espresso.onView(withId(R.id.password_edit_text))
            .perform(clearText(), typeText("123456"))
        Espresso.onView(withId(R.id.remember_check_box)).perform(click())
        Espresso.onView(withId(R.id.sign_in_button)).perform(click())
        Espresso.onView(withId(R.id.navigation)).check(matches(isDisplayed()))
    }
}