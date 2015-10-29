package apidez.com.databinding.dependency.component;

import apidez.com.databinding.dependency.module.PurchaseModule;
import apidez.com.databinding.dependency.scope.ViewScope;
import apidez.com.databinding.view.activity.PurchaseActivity;
import dagger.Subcomponent;

/**
 * Created by nongdenchet on 10/24/15.
 */
@ViewScope
@Subcomponent(modules = {PurchaseModule.class})
public interface PurchaseComponent {
    void inject(PurchaseActivity purchaseActivity);
}
