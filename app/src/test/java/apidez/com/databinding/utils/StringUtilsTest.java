package apidez.com.databinding.utils;

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
public class StringUtilsTest {

    @Test
    public void testCreate() throws Exception {
        new StringUtils();
    }

    @Test
    public void testGenerateString() throws Exception {
        assertEquals(StringUtils.generateString("aaa", 23).length(), 23);
        assertEquals(StringUtils.generateString("ccc", 10).length(), 10);
    }

    @Test
    public void testIsEmpty() throws Exception {
        assertTrue(StringUtils.isEmpty(""));
        assertTrue(StringUtils.isEmpty(null));
    }

    @Test
    public void testIsNotEmpty() throws Exception {
        assertFalse(StringUtils.isEmpty("hello"));
        assertFalse(StringUtils.isEmpty("world"));
    }
}