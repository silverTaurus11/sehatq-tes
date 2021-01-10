package com.project.sehatqtest

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.project.sehatqtest.helper.EspressoIdlingResource
import com.project.sehatqtest.view.frontview.FrontViewActivity
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

/*
    Bila ingin menjalankan ui test ini lebih baik menjalankan login tes terlebih dahulu
*/
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4ClassRunner::class)
class FrontViewActivityTest {
    @get:Rule
    var activityRule = ActivityTestRule(FrontViewActivity::class.java)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getEspressoIdlingResource())
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getEspressoIdlingResource())
    }

    @Test
    fun test1_loadCategory(){
        Espresso.onView(withId(R.id.navigation))
            .check(matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.category_recyclerview))
            .check(matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.category_recyclerview))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
    }

    @Test
    fun tes2_loadProduct(){
        Espresso.onView(withId(R.id.navigation))
            .check(matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.product_recycler_view))
            .check(matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.product_recycler_view))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
    }

    @Test
    fun tes3_loadFavoriteProduct(){
        Espresso.onView(withId(R.id.icon_heart))
            .check(matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.icon_heart))
            .perform(click())
        Espresso.onView(withId(R.id.product_recycler_view))
            .check(matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.product_recycler_view))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
    }

    @Test
    fun tes4_loadSearchProduct(){
        Espresso.onView(withId(R.id.search_box))
            .check(matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.search_box))
            .perform(click())
        Espresso.onView(withId(R.id.search_box))
            .perform(ViewActions.clearText(), ViewActions.typeText("Wii"))
        Espresso.onView(withId(R.id.product_recycler_view))
            .check(matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.product_recycler_view))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
    }

    @Test
    fun tes5_loadDetailProduct(){
        Espresso.onView(withId(R.id.navigation))
            .check(matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.product_recycler_view))
            .check(matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.product_recycler_view))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,
                click()))
        Espresso.onView(withId(R.id.product_banner))
            .check(matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.product_image))
            .check(matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.product_name))
            .check(matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.product_description))
            .check(matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.product_price))
            .check(matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.like_icon))
            .check(matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun tes6_loadPurchaseHistory(){
        Espresso.onView(withId(R.id.navigation))
            .check(matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.product_recycler_view))
            .check(matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.product_recycler_view))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,
                click()))
        Espresso.onView(withId(R.id.buy_button))
            .perform(click())
        Espresso.onView(withId(R.id.profile_navigation))
            .perform(click())
        Espresso.onView(withId(R.id.product_recycler_view))
            .check(matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.product_recycler_view))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
    }

    @Test
    fun tes7_logout(){
            Espresso.onView(withId(R.id.navigation))
                .check(matches(ViewMatchers.isDisplayed()))
            Espresso.onView(withId(R.id.cart_navigation))
                .perform(click())
        try{
            Espresso.onView(withText(getResourceString(R.string.logout_warning)))
                .check(matches(ViewMatchers.isDisplayed()))
            Espresso.onView(withId(android.R.id.button1)).perform(click())
            Espresso.onView(withId(R.id.login_title))
                .check(matches(ViewMatchers.isDisplayed()))
        } catch (e: NoMatchingViewException){
            //Jika status belom login
        }

    }

    private fun getResourceString(id: Int): String {
        return activityRule.activity.resources.getString(id)
    }
}