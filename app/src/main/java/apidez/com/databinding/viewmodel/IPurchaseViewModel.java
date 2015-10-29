package apidez.com.databinding.viewmodel;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;

/**
 * Created by nongdenchet on 10/29/15.
 */
public interface IPurchaseViewModel {
    /**
     * Observable validation of card
     */
    ObservableInt creditCardError();

    /**
     * Observable validation of email
     */
    ObservableInt emailError();

    /**
     * Observable validation of submit
     */
    ObservableBoolean canSubmit();

    /**
     * set new credit card
     */
    void setCreditCard(String newCreditCard);

    /**
     * Set new email
     */
    void setEmail(String newEmail);

    /**
     * Command submit
     */
    rx.Observable<Boolean> submit();
}
