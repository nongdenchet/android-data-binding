package apidez.com.databinding.dependency.module;

import com.google.gson.Gson;

import apidez.com.databinding.dependency.scope.ViewScope;
import apidez.com.databinding.model.api.IPurchaseApi;
import apidez.com.databinding.model.api.PurchaseApi;
import apidez.com.databinding.viewmodel.IPurchaseViewModel;
import apidez.com.databinding.viewmodel.PurchaseViewModel;
import dagger.Module;
import dagger.Provides;

/**
 * Created by nongdenchet on 10/2/15.
 */
@Module
public class PurchaseModule {
    @Provides
    @ViewScope
    public IPurchaseApi providePurchaseApi(Gson gson) {
        return new PurchaseApi(gson);
    }

    @Provides
    @ViewScope
    public IPurchaseViewModel providePurchaseViewModel(IPurchaseApi purchaseApi) {
        return new PurchaseViewModel(purchaseApi);
    }
}
