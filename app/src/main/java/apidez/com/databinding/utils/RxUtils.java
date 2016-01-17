package apidez.com.databinding.utils;

import rx.Scheduler;

/**
 * Created by nongdenchet on 1/17/16.
 */
public class RxUtils {

    public static class SchedulerHolder {
        public Scheduler mainScheduler;
        public Scheduler ioScheduler;

        public SchedulerHolder(Scheduler mainScheduler, Scheduler ioScheduler) {
            this.mainScheduler = mainScheduler;
            this.ioScheduler = ioScheduler;
        }
    }
}
