package com.maximbravo.bakeit;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RecipesActivityTestPhone {

    @Rule
    public ActivityTestRule<RecipesActivity> mActivityTestRule = new ActivityTestRule<>(RecipesActivity.class);

    @Test
    public void recipesActivityTest6() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.recipes_list), isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(1, click()));

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.steps_list_view), isDisplayed()));
        recyclerView2.perform(actionOnItemAtPosition(2, click()));

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.next_fab), isDisplayed()));
        floatingActionButton.perform(click());


        ViewInteraction floatingActionButton2 = onView(
                allOf(withId(R.id.prev_fab), isDisplayed()));
        floatingActionButton2.perform(click());

        ViewInteraction floatingActionButton3 = onView(
                allOf(withId(R.id.next_fab), isDisplayed()));
        floatingActionButton3.perform(click());

        ViewInteraction floatingActionButton4 = onView(
                allOf(withId(R.id.next_fab), isDisplayed()));
        floatingActionButton4.perform(click());


        ViewInteraction floatingActionButton5 = onView(
                allOf(withId(R.id.next_fab), isDisplayed()));
        floatingActionButton5.perform(click());


        ViewInteraction textView = onView(
                allOf(withId(R.id.playerView)));
        textView.check(matches(isDisplayed()));

    }

}
