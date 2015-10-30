package apidez.com.databinding;


/**
 * Created by nongdenchet on 10/24/15.
 */

import apidez.com.databinding.dependency.component.AppComponent;
import apidez.com.databinding.dependency.component.PlacesComponent;
import apidez.com.databinding.dependency.component.PurchaseComponent;
import apidez.com.databinding.dependency.module.PlacesModule;
import apidez.com.databinding.dependency.module.PurchaseModule;

/**
 * Use to build subcomponent
 */
public class ComponentBuilder {
    private AppComponent appComponent;

    public ComponentBuilder(AppComponent appComponent) {
        this.appComponent = appComponent;
    }

    public PlacesComponent placesComponent() {
        return appComponent.plus(new PlacesModule());
    }

    public PurchaseComponent purchaseComponent() {
        return appComponent.plus(new PurchaseModule());
    }
}
