package apidez.com.databinding.model.api;

import android.test.suitebuilder.annotation.SmallTest;

import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import rx.functions.Action0;
import rx.functions.Action1;
import rx.observers.TestSubscriber;

import static org.junit.Assert.*;

/**
 * Created by nongdenchet on 1/14/16.
 */
@SmallTest
@RunWith(JUnit4.class)
public class PurchaseApiTest {

    private PurchaseApi purchaseApi;

    @Before
    public void setUp() throws Exception {
        purchaseApi = new PurchaseApi(new Gson());
    }

    @Test
    public void testSubmitPurchase() throws Exception {
        boolean res = purchaseApi.submitPurchase("124", "").toBlocking().single();
        assertTrue(res);
    }

    @Test
    public void testSubmitPurchaseError() throws Exception {
        TestSubscriber testSubscriber = new TestSubscriber();
        purchaseApi.submitPurchase("1", "").subscribe(testSubscriber);
        testSubscriber.assertError(Exception.class);
    }
}