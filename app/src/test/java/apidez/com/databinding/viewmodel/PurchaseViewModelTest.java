package apidez.com.databinding.viewmodel;

import android.test.suitebuilder.annotation.MediumTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;

import apidez.com.databinding.R;
import apidez.com.databinding.model.api.PurchaseApi;
import rx.Observable;
import rx.observers.TestSubscriber;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by nongdenchet on 10/29/15.
 */
@MediumTest
@RunWith(JUnit4.class)
public class PurchaseViewModelTest {
    private PurchaseViewModel purchaseViewModel;
    private PurchaseApi purchaseApi;
    private TestSubscriber<Boolean> testSubscriber;

    @Before
    public void setUpViewModel() {
        // Mock purchase api
        purchaseApi = Mockito.mock(PurchaseApi.class);
        when(purchaseApi.submitPurchase(anyString(), anyString()))
                .thenReturn(Observable.just(true));

        // Create test viewmodel
        purchaseViewModel = new PurchaseViewModel(purchaseApi);
        testSubscriber = TestSubscriber.create();
    }

    @Test
    public void inputValidCreditCard() throws Exception {
        purchaseViewModel.setCreditCard("412341234123");
        assertEquals(purchaseViewModel.creditCardError().get(), R.string.empty);
    }

    @Test
    public void inputInvalidCreditCard() throws Exception {
        purchaseViewModel.setCreditCard("abcdabcdabcd");
        assertEquals(purchaseViewModel.creditCardError().get(), R.string.error_credit_card);
    }

    @Test
    public void inputValidEmail() throws Exception {
        purchaseViewModel.setEmail("ndc@gmail.com");
        assertEquals(purchaseViewModel.emailError().get(), R.string.empty);
    }

    @Test
    public void inputInvalidEmail() throws Exception {
        purchaseViewModel.setEmail("__123$$@gm.co");
        assertEquals(purchaseViewModel.emailError().get(), R.string.error_email);
    }

    @Test
    public void canSubmit() throws Exception {
        purchaseViewModel.setCreditCard("412341234123");
        purchaseViewModel.setEmail("ndc@gmail.com");
        assertTrue(purchaseViewModel.canSubmit().get());
    }

    @Test
    public void cannotSubmitInvalidEmail() throws Exception {
        purchaseViewModel.setCreditCard("412341234123");
        purchaseViewModel.setEmail("ndc###@gmail.com");
        assertFalse(purchaseViewModel.canSubmit().get());
    }

    @Test
    public void cannotSubmitInvalidCreditCard() throws Exception {
        purchaseViewModel.setCreditCard("412123123341234123");
        purchaseViewModel.setEmail("ndc@gmail.com");
        assertFalse(purchaseViewModel.canSubmit().get());
    }

    @Test
    public void cannotSubmitEmptyEmail() throws Exception {
        purchaseViewModel.setCreditCard("412341234123");
        purchaseViewModel.setEmail("");
        assertFalse(purchaseViewModel.canSubmit().get());
    }

    @Test
    public void cannotSubmitEmptyCreditCard() throws Exception {
        purchaseViewModel.setEmail("ndc@gmail.com");
        purchaseViewModel.setCreditCard("");
        assertFalse(purchaseViewModel.canSubmit().get());
    }

    @Test
    public void submit() throws Exception {
        purchaseViewModel.setCreditCard("412341234123");
        purchaseViewModel.setEmail("ndc@gmail.com");
        purchaseViewModel.submit().subscribe(testSubscriber);
        testSubscriber.assertReceivedOnNext(Collections.singletonList(true));
        verify(purchaseApi).submitPurchase("412341234123", "ndc@gmail.com");
    }

    @Test
    public void submitTimeout() throws Exception {
        purchaseViewModel.setCreditCard("412341234123");
        purchaseViewModel.setEmail("ndc@gmail.com");
        when(purchaseApi.submitPurchase(anyString(), anyString())).thenReturn(
                Observable.create(subscriber -> {
                    try {
                        Thread.sleep(5100);
                        subscriber.onNext(true);
                        subscriber.onCompleted();
                    } catch (InterruptedException e) {
                        subscriber.onError(e);
                    }
                })
        );
        try {
            boolean success = purchaseViewModel.submit().toBlocking().first();
            if (success) fail("Should be timeout");
        } catch (Exception ignored) {
            // The test pass here, it should be timeout
        }
    }

    @Test
    public void submitOnTime() throws Exception {
        purchaseViewModel.setCreditCard("412341234123");
        purchaseViewModel.setEmail("ndc@gmail.com");
        when(purchaseApi.submitPurchase(anyString(), anyString())).thenReturn(
                Observable.create(subscriber -> {
                    try {
                        Thread.sleep(4900);
                        subscriber.onNext(true);
                        subscriber.onCompleted();
                    } catch (InterruptedException e) {
                        subscriber.onError(e);
                    }
                })
        );
        try {
            boolean success = purchaseViewModel.submit().toBlocking().first();
            assertTrue(success);
        } catch (Exception ignored) {
            // The test pass here, it should be timeout
            fail("Should be on time");
        }
    }

    @Test
    public void submitRetry() throws Exception {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        purchaseViewModel.setCreditCard("412341234123");
        purchaseViewModel.setEmail("ndc@gmail.com");
        when(purchaseApi.submitPurchase(anyString(), anyString())).thenReturn(
                Observable.create(subscriber -> {
                    if (atomicInteger.getAndIncrement() < 3) {
                        subscriber.onError(new Exception());
                    } else {
                        subscriber.onNext(true);
                        subscriber.onCompleted();
                    }
                })
        );
        try {
            boolean success = purchaseViewModel.submit().toBlocking().first();
            verify(purchaseApi).submitPurchase(anyString(), anyString());
            assertTrue(success);
        } catch (Exception ignored) {
            fail("Have to retry three times");
        }
    }

    @Test
    public void submitExceedRetry() throws Exception {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        purchaseViewModel.setCreditCard("412341234123");
        purchaseViewModel.setEmail("ndc@gmail.com");
        when(purchaseApi.submitPurchase(anyString(), anyString())).thenReturn(
                Observable.create(subscriber -> {
                    if (atomicInteger.getAndIncrement() < 4) {
                        subscriber.onError(new Exception());
                    } else {
                        subscriber.onNext(true);
                        subscriber.onCompleted();
                    }
                })
        );
        try {
            purchaseViewModel.submit().toBlocking().first();
            fail("Should be out of retry");
        } catch (Exception ignored) {
            // Test pass here
        }
    }
}