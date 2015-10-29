package apidez.com.databinding.dependency.component;

import javax.inject.Singleton;

import apidez.com.databinding.dependency.module.AppModule;
import apidez.com.databinding.dependency.module.PurchaseModule;
import dagger.Component;

/**
 * Created by nongdenchet on 10/2/15.
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    PurchaseComponent plus(PurchaseModule purchaseModule);
}
