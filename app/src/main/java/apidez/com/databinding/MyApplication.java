package apidez.com.databinding;

import android.app.Application;
import android.content.Context;

import apidez.com.databinding.dependency.component.AppComponent;
import apidez.com.databinding.dependency.component.DaggerAppComponent;
import apidez.com.databinding.dependency.module.AppModule;


/**
 * Created by nongdenchet on 10/2/15.
 */
public class MyApplication extends Application {
    private static Context mContext;
    protected AppComponent mAppComponent;
    protected ComponentBuilder mComponentBuilder;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

        // Create app component
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule())
                .build();

        // Create component builder
        mComponentBuilder = new ComponentBuilder(mAppComponent);
    }

    public AppComponent component() {
        return mAppComponent;
    }

    public ComponentBuilder builder() {
        return mComponentBuilder;
    }

    public static Context context() {
        return mContext;
    }
}