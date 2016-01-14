package apidez.com.databinding.utils;

import android.test.suitebuilder.annotation.SmallTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

/**
 * Created by nongdenchet on 1/14/16.
 */
@SmallTest
@RunWith(JUnit4.class)
public class NumericUtilsTest {

    @Test
    public void testCreate() throws Exception {
        new NumericUtils();
    }

    @Test
    public void testIsNumeric() throws Exception {
        assertTrue(NumericUtils.isNumeric("1000"));
        assertFalse(NumericUtils.isNumeric("hello"));
    }

    @Test
    public void testIsNumericCharacter() throws Exception {
        CharSequence sequence = "10000";
        assertTrue(NumericUtils.isNumeric(sequence));
        sequence = "hello";
        assertFalse(NumericUtils.isNumeric(sequence));
    }
}