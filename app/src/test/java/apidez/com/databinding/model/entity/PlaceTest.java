package apidez.com.databinding.model.entity;

import android.test.suitebuilder.annotation.SmallTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by nongdenchet on 1/14/16.
 */
@SmallTest
@RunWith(JUnit4.class)
public class PlaceTest {

    @Test
    public void testGetIcon() throws Exception {
        Place place = new Place.Builder().icon("url").build();
        assertEquals("url", place.getIcon());
    }

    @Test
    public void testGetId() throws Exception {
        Place place = new Place.Builder().build();
        assertNotNull(place.getId());
    }

    @Test
    public void testGetName() throws Exception {
        Place place = new Place.Builder().name("name").build();
        assertEquals("name", place.getName());
    }

    @Test
    public void testGetTypes() throws Exception {
        Place place = new Place.Builder().types(Arrays.asList("123", "234")).build();
        assertEquals(2, place.getTypes().size());
    }
}