package apidez.com.databinding.utils;

import android.databinding.BindingConversion;

import apidez.com.databinding.MyApplication;

/**
 * Created by nongdenchet on 10/29/15.
 */
public class BindingUtils {
    @BindingConversion
    public static String convertIdToString(int stringId) {
        return MyApplication.context().getString(stringId);
    }
}
