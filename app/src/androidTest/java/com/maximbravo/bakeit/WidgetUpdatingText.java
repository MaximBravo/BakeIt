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
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressMenuKey;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class WidgetUpdatingText {

    @Rule
    public ActivityTestRule<RecipesActivity> mActivityTestRule = new ActivityTestRule<>(RecipesActivity.class);

    @Test
    public void widgetUpdatingText() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.recipes_list), isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction textView = onView(
                allOf(withId(R.id.step_short_description), withText("Nutella Pie")));
        textView.check(matches(withText("Nutella Pie")));

        pressBack();

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.ingredient_list), withText("Nutella Pie\n1) 2 CUP of Graham Cracker crumbs\n2) 6 TBLSP of unsalted butter, melted\n3) 0 CUP of granulated sugar\n4) 1 TSP of salt\n5) 5 TBLSP of vanilla\n6) 1 K of Nutella or other chocolate-hazelnut spread\n7) 500 G of Mascapone Cheese(room temperature)\n8) 1 CUP of heavy cream(cold)\n9) 4 OZ of cream cheese(softened)\n"),
                        childAtPosition(
                                childAtPosition(
                                        withContentDescription("BakeIt"),
                                        0),
                                0),
                        isDisplayed()));
        textView2.check(matches(isDisplayed()));

        pressMenuKey();

//        onView(withContentDescription("Overview")).perform(click());
//        onView(withContentDescription("BakeIt")).perform(click());
//        ViewInteraction recyclerView2 = onView(
//                allOf(withId(R.id.recipes_list), isDisplayed()));
//        recyclerView2.perform(actionOnItemAtPosition(1, click()));
//
//        ViewInteraction textView3 = onView(
//                allOf(withId(R.id.ingredient_list)));
//        textView3.check(matches(withText("Brownies 1) 350 G of Bittersweet chocolate (60-70% cacao) 2) 226 G of unsalted butter 3) 300 G of granulated sugar 4) 100 G of light brown sugar 5) 5 UNIT of large eggs 6) 1 TBLSP of vanilla extract 7) 140 G of all purpose flour 8) 40 G of cocoa powder 9) 1 TSP of salt 10) 350 G of semisweet chocolate chips ")));

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
