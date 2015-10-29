package apidez.com.databinding.view.handler;

import android.text.TextWatcher;
import android.view.View;

/**
 * Created by nongdenchet on 10/28/15.
 */
public interface IPurchaseHandler {
    TextWatcher emailWatcher();
    TextWatcher creditCardWatcher();
    View.OnClickListener onSubmit();
}
