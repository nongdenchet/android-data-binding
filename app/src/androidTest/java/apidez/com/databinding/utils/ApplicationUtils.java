package apidez.com.databinding.utils;

import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;

import apidez.com.databinding.TestApplication;

/**
 * Created by nongdenchet on 10/22/15.
 */
public class ApplicationUtils {
    public static TestApplication application() {
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        return (TestApplication) instrumentation.getTargetContext().getApplicationContext();
    }
}
