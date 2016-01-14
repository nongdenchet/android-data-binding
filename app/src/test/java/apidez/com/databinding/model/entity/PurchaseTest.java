package apidez.com.databinding.model.entity;

import android.test.suitebuilder.annotation.SmallTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

/**
 * Created by nongdenchet on 1/14/16.
 */
@SmallTest
@RunWith(JUnit4.class)
public class PurchaseTest {

    private Purchase purchase;

    @Before
    public void setUp() throws Exception {
        purchase = new Purchase("credit", "number");
    }

    @Test
    public void testGetCreditCard() throws Exception {
        assertEquals("credit", purchase.getCreditCard());
        purchase = new Purchase();
        assertNull(purchase.getCreditCard());
    }

    @Test
    public void testSetCreditCard() throws Exception {
        purchase.setCreditCard("aaa");
        assertEquals("aaa", purchase.getCreditCard());
    }

    @Test
    public void testGetEmail() throws Exception {
        assertEquals("number", purchase.getEmail());
        purchase = new Purchase();
        assertNull(purchase.getEmail());
    }

    @Test
    public void testSetEmail() throws Exception {
        purchase.setEmail("bbb");
        assertEquals("bbb", purchase.getEmail());
    }
}