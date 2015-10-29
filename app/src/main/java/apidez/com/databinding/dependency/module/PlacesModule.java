package apidez.com.databinding.dependency.module;

import apidez.com.databinding.dependency.scope.ViewScope;
import apidez.com.databinding.model.api.IPlacesApi;
import apidez.com.databinding.utils.RetrofitUtils;
import apidez.com.databinding.viewmodel.IPlacesViewModel;
import apidez.com.databinding.viewmodel.PlacesViewModel;
import dagger.Module;
import dagger.Provides;

/**
 * Created by nongdenchet on 10/21/15.
 */
@Module
public class PlacesModule {
    @Provides
    @ViewScope
    public IPlacesApi providePlacesApi() {
        return RetrofitUtils.create(IPlacesApi.class, "https://maps.googleapis.com/maps/api/place/");
    }

    @Provides
    @ViewScope
    public IPlacesViewModel providePlacesViewModel(IPlacesApi placesApi) {
        return new PlacesViewModel(placesApi);
    }
}