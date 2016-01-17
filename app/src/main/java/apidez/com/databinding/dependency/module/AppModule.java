package apidez.com.databinding.dependency.module;

import com.google.gson.Gson;

import javax.inject.Singleton;

import apidez.com.databinding.utils.RxUtils;
import dagger.Module;
import dagger.Provides;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by nongdenchet on 10/3/15.
 */
@Module
public class AppModule {
    @Singleton
    @Provides
    public Gson provideGson() {
        return new Gson();
    }

    @Provides
    public RxUtils.SchedulerHolder provideSchedulerHolder() {
        return new RxUtils.SchedulerHolder(AndroidSchedulers.mainThread(), Schedulers.io());
    }
}
