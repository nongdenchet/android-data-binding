package apidez.com.databinding.view.fragment;

import android.view.MenuItem;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.fakes.RoboMenuItem;

import apidez.com.databinding.ComponentBuilder;
import apidez.com.databinding.CustomBuildConfig;
import apidez.com.databinding.R;
import apidez.com.databinding.UnitTestApplication;
import apidez.com.databinding.dependency.component.PlacesComponent;
import apidez.com.databinding.dependency.module.PlacesModule;
import apidez.com.databinding.model.api.IPlacesApi;
import apidez.com.databinding.view.activity.TestActivity;
import apidez.com.databinding.viewmodel.IPlacesViewModel;
import rx.Observable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by nongdenchet on 10/30/15.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = CustomBuildConfig.class, sdk = 21,
        manifest = "src/main/AndroidManifest.xml",
        application = UnitTestApplication.class)
public class PlacesFragmentTest {
    private TestActivity activity;
    private PlacesFragment fragment;
    private UnitTestApplication application;
    private IPlacesViewModel mockViewModel;

    @Before
    public void setUp() throws Exception {
        // Mocking
        mockViewModel = Mockito.mock(IPlacesViewModel.class);
        when(mockViewModel.fetchAllPlaces()).thenReturn(Observable.<Boolean>empty());
        PlacesModule mockModule = new PlacesModule() {
            @Override
            public IPlacesViewModel providePlacesViewModel(IPlacesApi placesApi) {
                return mockViewModel;
            }
        };

        // Setup application
        application = (UnitTestApplication) RuntimeEnvironment.application;
        application.setComponentBuilder(new ComponentBuilder(application.component()) {
            @Override
            public PlacesComponent placesComponent() {
                return application.component().plus(mockModule);
            }
        });

        // Setup activity
        fragment = PlacesFragment.newInstance();
        activity = Robolectric.buildActivity(TestActivity.class)
                .create()
                .start()
                .resume()
                .get();
        activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, fragment)
                .commit();
    }

    @Test
    public void toolBarShouldExist() throws Exception {
        assertNotNull(activity.getSupportActionBar());
        assertEquals(activity.getSupportActionBar().getTitle(), "Places");
    }

    @Test
    public void menuItemsExist() throws Exception {
        fragment.onOptionsItemSelected(new RoboMenuItem(R.id.action_sort));
        MenuItem cafeItem = new RoboMenuItem(R.id.action_cafe);
        cafeItem.setTitle("Cafe");
        fragment.onOptionsItemSelected(cafeItem);
        verify(mockViewModel).filterPlacesByType("Cafe");
    }
}