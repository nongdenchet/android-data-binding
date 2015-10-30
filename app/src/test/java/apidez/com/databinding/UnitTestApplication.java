package apidez.com.databinding;

import apidez.com.databinding.dependency.component.AppComponent;

/**
 * Created by nongdenchet on 10/30/15.
 */
public class UnitTestApplication extends MyApplication {
    public void setComponent(AppComponent appComponent) {
        this.mAppComponent = appComponent;
    }

    public void setComponentBuilder(ComponentBuilder componentBuilder) {
        this.mComponentBuilder = componentBuilder;
    }
}
