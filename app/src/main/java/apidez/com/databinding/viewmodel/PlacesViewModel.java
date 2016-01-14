package apidez.com.databinding.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.support.annotation.NonNull;

import java.util.List;
import java.util.concurrent.TimeUnit;

import apidez.com.databinding.model.api.IPlacesApi;
import apidez.com.databinding.model.entity.Place;
import rx.Observable;

/**
 * Created by nongdenchet on 10/21/15.
 */
public class PlacesViewModel extends BaseObservable implements IPlacesViewModel {
    private IPlacesApi mPlacesApi;
    private final int TIME_OUT = 5;
    private final int RETRY = 3;
    private List<Place> allPlaces;

    // Observable property
    private ObservableArrayList<Place> mPlaces = new ObservableArrayList<>();

    public PlacesViewModel(@NonNull IPlacesApi placesApi) {
        mPlacesApi = placesApi;
    }

    /**
     * Return an Observable that emits the current places
     */
    @Bindable
    public ObservableArrayList<Place> getCurrentPlaces() {
        return mPlaces;
    }

    /**
     * Command fetching all places
     */
    @Override
    public Observable<Boolean> fetchAllPlaces() {
        return mPlacesApi.placesResult()
                .map(googleSearchResult -> {
                    allPlaces = googleSearchResult.results;
                    mPlaces.clear();
                    mPlaces.addAll(allPlaces);
                    return true;
                })
                .timeout(TIME_OUT, TimeUnit.SECONDS)
                .retry(RETRY);
    }

    /**
     * Command filtering places
     */
    @Override
    public void filterPlacesByType(String type) {
        mPlaces.clear();
        if (type == null) return;
        if (type.equalsIgnoreCase("all")) {
            mPlaces.addAll(allPlaces);
        } else {
            Observable.from(allPlaces)
                    .filter(place -> place.getTypes().contains(getApiType(type)))
                    .subscribe(mPlaces::add);
        }
    }

    /**
     * Helpers change type to api_type
     */
    private String getApiType(String type) {
        String newType = type.toLowerCase();
        return newType.equals("theater") ? "movie_theater" : newType;
    }
}
