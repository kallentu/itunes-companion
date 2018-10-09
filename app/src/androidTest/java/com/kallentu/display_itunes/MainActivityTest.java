package com.kallentu.display_itunes;

import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.filters.SmallTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.KeyEvent;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

/**
 * Tests for {@link MainActivity} searching + querying.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    private ViewInteraction searchBar;
    private ViewInteraction title;
    private String testString;

    @Rule
    public ActivityTestRule<MainActivity> mainActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() {
        title = onView(withId(R.id.itunes_companion_title));
        searchBar = onView(withId(R.id.userQueryEditText));
        testString = "test";
    }

    @SmallTest
    public void views_notNull() {
        assertNotNull(title);
        assertNotNull(searchBar);
    }

    @SmallTest
    public void searchBar_initialEmpty() {
        searchBar.check(matches(withText("")));
    }

    @Test
    public void searchBar_changeText() {
        searchBar.perform(typeText(testString));
        ViewActions.pressKey(KeyEvent.KEYCODE_ENTER);

        searchBar.check(matches(withText(testString)));
    }
}
