package apidez.com.databinding.model.api;

import android.support.annotation.NonNull;

import com.google.gson.Gson;

import apidez.com.databinding.model.entity.Purchase;
import rx.Observable;

/**
 * Created by nongdenchet on 10/3/15.
 */
public class PurchaseApi implements IPurchaseApi {

    private Gson mGson;

    public PurchaseApi(@NonNull Gson gson) {
        mGson = gson;
    }

    /**
     * Fake networking
     */
    public Observable<Boolean> submitPurchase(String creditCard, String email) {
        Purchase purchase = new Purchase(creditCard, email);
        return Observable.create(subscriber -> {
            try {
                String json = mGson.toJson(purchase);
                Thread.sleep((json.length() % 3) * 1000);
                if (json.length() % 3 == 2) throw new Exception("fail");
                subscriber.onNext(true);
                subscriber.onCompleted();
            } catch (Exception e) {
                subscriber.onError(e);
            }
        });
    }
}