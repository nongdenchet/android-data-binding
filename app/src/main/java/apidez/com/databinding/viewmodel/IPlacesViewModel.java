package apidez.com.databinding.viewmodel;

import android.databinding.ObservableArrayList;

import apidez.com.databinding.model.entity.Place;
import rx.Observable;

/**
 * Created by nongdenchet on 10/21/15.
 */
public interface IPlacesViewModel {
    /**
     * Fetch all places from google
     */
    Observable<Boolean> fetchAllPlaces();

    /**
     * Observe current places
     */
    ObservableArrayList<Place> getCurrentPlaces();

    /**
     * Filter the places
     */
    void filterPlacesByType(String type);
}
