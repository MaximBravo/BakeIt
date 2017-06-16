package com.maximbravo.bakeit;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Kids on 6/14/2017.
 */
@RunWith(AndroidJUnit4.class)
public class RecipeActivityBasicTest {
    @Rule
    public ActivityTestRule<RecipesActivity> mActivityTestRule =
            new ActivityTestRule<>(RecipesActivity.class);

    @Test
    public void clickRecipeAndCheckToMakeSureItWorked() {

//        // Check the initial quantity variable is zero
//        onView((withId(R.id.quantity_text_view))).check(matches(withText("0")));
//
//        // Click on decrement button
//        onView((withId(R.id.decrement_button)))
//                .perform(click());
//
//        // Verify that the decrement button decreases the quantity by 1
//        onView(withId(R.id.quantity_text_view)).check(matches(withText("0")));
//
//        // Verify that the increment button also increases the total cost to $5.00
//        onView(withId(R.id.cost_text_view)).check(matches(withText("$0.00")));

    }
}
