package apidez.com.databinding.utils;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by nongdenchet on 10/2/15.
 */
public class UiUtils {
    public static void showDialog(String text, Context context) {
        new AlertDialog.Builder(context)
                .setMessage(text)
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                })
                .show();
    }

    public static void closeKeyboard(Activity context) {
        // Check if no view has focus:
        View view = context.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
