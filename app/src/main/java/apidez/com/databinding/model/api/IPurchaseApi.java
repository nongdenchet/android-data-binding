package apidez.com.databinding.model.api;

import rx.Observable;

/**
 * Created by nongdenchet on 10/24/15.
 */
public interface IPurchaseApi {
    Observable<Boolean> submitPurchase(String creditCard, String email);
}
