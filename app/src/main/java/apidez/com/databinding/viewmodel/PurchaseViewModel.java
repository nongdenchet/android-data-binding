package apidez.com.databinding.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;

import java.util.concurrent.TimeUnit;

import apidez.com.databinding.R;
import apidez.com.databinding.model.api.IPurchaseApi;
import apidez.com.databinding.utils.NumericUtils;
import apidez.com.databinding.utils.StringUtils;

/**
 * Created by nongdenchet on 10/28/15.
 */
public class PurchaseViewModel extends BaseObservable implements IPurchaseViewModel {
    private final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private final int TIME_OUT = 5;
    private final int RETRY = 3;

    // Normal property
    private String mCreditCard;
    private String mEmail;
    private boolean mCreditCardValid;
    private boolean mEmailValid;

    // Observable property
    private ObservableBoolean mCanSubmit = new ObservableBoolean(false);
    private ObservableInt mEmailError = new ObservableInt(R.string.empty);
    private ObservableInt mCreditCardError = new ObservableInt(R.string.empty);

    // Dependency
    private IPurchaseApi mPurchaseApi;

    public PurchaseViewModel(@NonNull IPurchaseApi purchaseApi) {
        mPurchaseApi = purchaseApi;
    }

    @Override
    public ObservableInt creditCardError() {
        return mCreditCardError;
    }

    @Override
    public ObservableInt emailError() {
        return mEmailError;
    }

    @Override
    public ObservableBoolean canSubmit() {
        return mCanSubmit;
    }

    @Override
    public void setCreditCard(String newCreditCard) {
        if (StringUtils.isEmpty(newCreditCard)) return;
        mCreditCard = newCreditCard;
        mCreditCardValid = newCreditCard.length() == 12 && NumericUtils.isNumeric(newCreditCard);
        mCreditCardError.set(mCreditCardValid ? R.string.empty : R.string.error_credit_card);
        updateCanSubmit();
    }

    @Override
    public void setEmail(String newEmail) {
        if (StringUtils.isEmpty(newEmail)) return;
        mEmail = newEmail;
        mEmailValid = newEmail.matches(EMAIL_REGEX);
        mEmailError.set(mEmailValid ? R.string.empty : R.string.error_email);
        updateCanSubmit();
    }

    @Override
    public rx.Observable<Boolean> submit() {
        return mPurchaseApi.submitPurchase(mCreditCard, mEmail)
                .timeout(TIME_OUT, TimeUnit.SECONDS)
                .retry(RETRY);
    }

    private void updateCanSubmit() {
        mCanSubmit.set(mCreditCardValid && mEmailValid);
    }
}
