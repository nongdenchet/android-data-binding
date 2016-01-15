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
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;

/**
 * Created by nongdenchet on 10/21/15.
 */
public class PlacesViewModel extends BaseObservable implements IPlacesViewModel {
    private IPlacesApi mPlacesApi;
    private final int TIME_OUT = 5;
    private boolean firstTime = true;
    private List<Place> allPlaces;

    // Observable property
    private ObservableArrayList<Place> mPlaces = new ObservableArrayList<>();
    private BehaviorSubject<Boolean> progress = BehaviorSubject.create(false);

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
                .timeout(TIME_OUT, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map(googleSearchResult -> {
                    // update list
                    allPlaces = googleSearchResult.results;
                    mPlaces.clear();
                    mPlaces.addAll(allPlaces);

                    // check first time
                    if (firstTime) {
                        progress.onNext(true);
                        firstTime = false;
                    }
                    return true;
                });
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

    @Override
    public Observable<Boolean> progress() {
        return progress.asObservable();
    }

    /**
     * Helpers change type to api_type
     */
    private String getApiType(String type) {
        String newType = type.toLowerCase();
        return newType.equals("theater") ? "movie_theater" : newType;
    }
}
