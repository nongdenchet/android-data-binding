package apidez.com.databinding.viewmodel;

import android.test.suitebuilder.annotation.MediumTest;
import android.test.suitebuilder.annotation.SmallTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import java.util.Collections;

import apidez.com.databinding.R;
import apidez.com.databinding.model.api.PurchaseApi;
import rx.Observable;
import rx.observers.TestSubscriber;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by nongdenchet on 10/29/15.
 */
@SmallTest
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
}