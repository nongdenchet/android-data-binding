package apidez.com.databinding.view.fragment;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.test.suitebuilder.annotation.MediumTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import apidez.com.databinding.ComponentBuilder;
import apidez.com.databinding.R;
import apidez.com.databinding.dependency.component.AppComponent;
import apidez.com.databinding.dependency.component.PlacesComponent;
import apidez.com.databinding.dependency.module.PlacesModule;
import apidez.com.databinding.model.api.IPlacesApi;
import apidez.com.databinding.utils.ApplicationUtils;
import apidez.com.databinding.utils.TestDataUtils;
import apidez.com.databinding.view.activity.TestActivity;
import rx.Observable;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static apidez.com.databinding.utils.MatcherEx.hasItemCount;
import static org.mockito.Mockito.when;

/**
 * Created by nongdenchet on 10/22/15.
 */
@MediumTest
@RunWith(JUnit4.class)
public class PlacesFragmentIntegrateTest {

    @Rule
    public ActivityTestRule<TestActivity> activityTestRule =
            new ActivityTestRule<>(TestActivity.class, true, false);

    @Before
    public void setUp() throws Exception {
        PlacesModule mockModule = new PlacesModule() {
            @Override
            public IPlacesApi providePlacesApi() {
                IPlacesApi placesApi = Mockito.mock(IPlacesApi.class);
                when(placesApi.placesResult())
                        .thenReturn(Observable.just(TestDataUtils.nearByData()));
                return placesApi;
            }
        };

        // Setup test component
        AppComponent component = ApplicationUtils.application().component();
        ApplicationUtils.application().setComponentBuilder(new ComponentBuilder(component) {
            @Override
            public PlacesComponent placesComponent() {
                return component.plus(mockModule);
            }
        });

        activityTestRule.launchActivity(new Intent());
        activityTestRule.getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, PlacesFragment.newInstance())
                .commit();

        // TODO: fix this problem
        // Sometime espresso cannot find the view with id: "R.id.action_sort"
        // Maybe the view has not been completed rendered
        // Currently put it to sleep 1000ms
        Thread.sleep(1000);
    }

    @Test
    public void testAllPlacesOnLayout() throws Exception {
        onView(withId(R.id.recycler_view)).check(matches(hasItemCount(10)));
    }

    @Test
    public void clickFood() throws Exception {
        onView(withId(R.id.action_sort)).perform(click());
        onView(withText("Food")).perform(click());
        onView(withId(R.id.recycler_view)).check(matches(hasItemCount(4)));
    }

    @Test
    public void clickCafe() throws Exception {
        onView(withId(R.id.action_sort)).perform(click());
        onView(withText("Cafe")).perform(click());
        onView(withId(R.id.recycler_view)).check(matches(hasItemCount(5)));
    }

    @Test
    public void clickStore() throws Exception {
        onView(withId(R.id.action_sort)).perform(click());
        onView(withText("Store")).perform(click());
        onView(withId(R.id.recycler_view)).check(matches(hasItemCount(4)));
    }

    @Test
    public void clickTheater() throws Exception {
        onView(withId(R.id.action_sort)).perform(click());
        onView(withText("Theater")).perform(click());
        onView(withId(R.id.recycler_view)).check(matches(hasItemCount(3)));
    }

    @Test
    public void clickRestaurant() throws Exception {
        onView(withId(R.id.action_sort)).perform(click());
        onView(withText("Restaurant")).perform(click());
        onView(withId(R.id.recycler_view)).check(matches(hasItemCount(3)));
    }

    @Test
    public void clickAll() throws Exception {
        onView(withId(R.id.action_sort)).perform(click());
        onView(withText("All")).perform(click());
        onView(withId(R.id.recycler_view)).check(matches(hasItemCount(10)));
    }

    @Test
    public void clickRestaurantAfterCafe() throws Exception {
        onView(withId(R.id.action_sort)).perform(click());
        onView(withText("Cafe")).perform(click());
        onView(withId(R.id.action_sort)).perform(click());
        onView(withText("Restaurant")).perform(click());
        onView(withId(R.id.recycler_view)).check(matches(hasItemCount(3)));
    }

    @Test
    public void clickAllAfterCafe() throws Exception {
        onView(withId(R.id.action_sort)).perform(click());
        onView(withText("Cafe")).perform(click());
        onView(withId(R.id.action_sort)).perform(click());
        onView(withText("All")).perform(click());
        onView(withId(R.id.recycler_view)).check(matches(hasItemCount(10)));
    }
}
