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
public class TestDataUtilsTest {

    @Test
    public void testCreate() throws Exception {
        new TestDataUtils();
    }
}