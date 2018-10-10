package com.kallentu.display_itunes;

import android.support.test.espresso.Espresso;
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

import be.ceau.itunesapi.response.Result;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;

/**
 * Tests for {@link MainActivity} searching + querying.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    private ViewInteraction searchBar;
    private ViewInteraction title;
    private ViewInteraction resultsList;
    private String testString;

    @Rule
    public ActivityTestRule<MainActivity> mainActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() {
        title = onView(withId(R.id.itunes_companion_title));
        searchBar = onView(withId(R.id.userQueryEditText));
        resultsList = onView(withId(R.id.results_list));
        testString = "test";
    }

    @SmallTest
    public void views_notNull() {
        assertNotNull(title);
        assertNotNull(searchBar);
        assertNotNull(resultsList);
    }

    @SmallTest
    public void searchBar_initialEmpty() {
        searchBar.check(matches(withText("")));
    }

    @Test
    public void searchBar_changeText() {
        searchBar.perform(typeText(testString));
        Espresso.onView(withId(R.id.userQueryEditText)).perform(ViewActions.pressKey(KeyEvent.KEYCODE_ENTER));

        searchBar.check(matches(withText(testString)));
    }

    /** Ensures that results do appear in the list with query 'test'. */
    @Test
    public void resultsList_moreThanOneResult() {
        searchBar.perform(typeText(testString));
        Espresso.onView(withId(R.id.userQueryEditText)).perform(ViewActions.pressKey(KeyEvent.KEYCODE_ENTER));

        resultsList.check(matches(hasMinimumChildCount(1)));
    }

    /** Ensures that no results do appear in the list with empty query. */
    @Test
    public void resultsList_noResults() {
        searchBar.perform(typeText(""));
        Espresso.onView(withId(R.id.userQueryEditText)).perform(ViewActions.pressKey(KeyEvent.KEYCODE_ENTER));

        resultsList.check(matches(hasChildCount(0)));
    }

    /** Ensures that no results do appear in the list with an unmatched query. */
    @Test
    public void resultsList_noResults_unmatchedQuery() {
        searchBar.perform(typeText("thiswillreturnnoresults"));
        Espresso.onView(withId(R.id.userQueryEditText)).perform(ViewActions.pressKey(KeyEvent.KEYCODE_ENTER));

        resultsList.check(matches(hasChildCount(0)));
    }

    /** Ensures at least one result is displayed. */
    @Test
    public void result_displayed() {
        searchBar.perform(typeText(testString));
        Espresso.onView(withId(R.id.userQueryEditText)).perform(ViewActions.pressKey(KeyEvent.KEYCODE_ENTER));

        onData(is(instanceOf(Result.class)))
                .inAdapterView(withId(R.id.results_list))
                .atPosition(0)
                .check(matches(notNullValue()));
    }

    /** Ensures result info page is displayed for first result. */
    @Test
    public void result_click_infoActivityDisplayed() {
        searchBar.perform(typeText(testString));
        Espresso.onView(withId(R.id.userQueryEditText)).perform(ViewActions.pressKey(KeyEvent.KEYCODE_ENTER));

        onData(is(instanceOf(Result.class)))
                .inAdapterView(withId(R.id.results_list))
                .atPosition(0)
                .perform(click());

        assertNotNull(onView(withId(R.id.info_song_title)));
        assertNotNull(onView(withId(R.id.info_artist)));
        assertNotNull(onView(withId(R.id.info_album)));
        assertNotNull(onView(withId(R.id.info_price)));
        assertNotNull(onView(withId(R.id.info_release_date)));
        assertNotNull(onView(withId(R.id.info_genre)));
        assertNotNull(onView(withId(R.id.info_buy_itunes_button)));
    }
}
