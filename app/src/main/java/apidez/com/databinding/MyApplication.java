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

    protected AppComponent mAppComponent;
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

        // Create app component
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule())
                .build();
    }

    public AppComponent component() {
        return mAppComponent;
    }

    public static Context context() {
        return mContext;
    }
}