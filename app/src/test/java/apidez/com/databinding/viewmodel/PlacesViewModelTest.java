package apidez.com.databinding.viewmodel;

import android.test.suitebuilder.annotation.SmallTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import apidez.com.databinding.model.api.IPlacesApi;
import apidez.com.databinding.model.entity.GoogleSearchResult;
import apidez.com.databinding.model.entity.Place;
import apidez.com.databinding.utils.RxUtils;
import apidez.com.databinding.utils.TestDataUtils;
import rx.Observable;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Created by nongdenchet on 10/30/15.
 */
@SmallTest
@RunWith(JUnit4.class)
public class PlacesViewModelTest {
    private PlacesViewModel placesViewModel;
    private IPlacesApi placesApi;
    private TestSubscriber<Boolean> testSubscriber;

    @Before
    public void setUpViewModel() {
        placesApi = Mockito.mock(IPlacesApi.class);
        placesViewModel = new PlacesViewModel(placesApi,
                new RxUtils.SchedulerHolder(Schedulers.immediate(), Schedulers.immediate()));
        testSubscriber = TestSubscriber.create();
        when(placesApi.placesResult()).thenReturn(testDataObservable());
    }

    @Test
    public void fetchFirstTime() {
        TestSubscriber tester = TestSubscriber.create();
        placesViewModel.progress().subscribe(tester);
        placesViewModel.fetchAllPlaces().subscribe(testSubscriber);
        placesViewModel.fetchAllPlaces().subscribe(testSubscriber);
        placesViewModel.fetchAllPlaces().subscribe(testSubscriber);
        tester.assertReceivedOnNext(Arrays.asList(false, true));
    }

    @Test
    public void fetchAllPlacesSuccess() {
        placesViewModel.fetchAllPlaces().subscribe(testSubscriber);
        testSubscriber.assertNoErrors();
        testSubscriber.assertCompleted();
        testSubscriber.assertReceivedOnNext(Collections.singletonList(true));
    }

    @Test
    public void fetchAllPlaces() {
        placesViewModel.fetchAllPlaces().subscribe();
        assertEquals(placesViewModel.getCurrentPlaces().size(), 10);
    }

    @Test
    public void filterAll() {
        assertEquals(getAndFilterWith("all").size(), 10);
        getAndFilterWith("cafe");
        assertEquals(getAndFilterWith("all").size(), 10);
    }

    @Test
    public void filterFood() {
        assertEquals(getAndFilterWith("food").size(), 4);
        getAndFilterWith("cafe");
        assertEquals(getAndFilterWith("food").size(), 4);
    }

    @Test
    public void filterCafe() {
        assertEquals(getAndFilterWith("cafe").size(), 5);
    }

    @Test
    public void filterStore() {
        assertEquals(getAndFilterWith("store").size(), 4);
    }

    @Test
    public void filterRestaurant() {
        assertEquals(getAndFilterWith("restaurant").size(), 3);
        getAndFilterWith("cafe");
        assertEquals(getAndFilterWith("restaurant").size(), 3);
    }

    @Test
    public void typeNull() {
        assertEquals(getAndFilterWith(null).size(), 0);
    }

    @Test
    public void filterTheater() {
        assertEquals(getAndFilterWith("theater").size(), 3);
    }

    private List<Place> getAndFilterWith(String type) {
        placesViewModel.fetchAllPlaces().subscribe();
        placesViewModel.filterPlacesByType(type);
        return placesViewModel.getCurrentPlaces();
    }

    private Observable<GoogleSearchResult> testDataObservable() {
        return Observable.just(TestDataUtils.nearByData());
    }
}