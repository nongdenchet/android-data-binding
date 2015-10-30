package apidez.com.databinding.view.activity;

import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import apidez.com.databinding.ComponentBuilder;
import apidez.com.databinding.CustomBuildConfig;
import apidez.com.databinding.R;
import apidez.com.databinding.UnitTestApplication;
import apidez.com.databinding.dependency.component.PurchaseComponent;
import apidez.com.databinding.dependency.module.PurchaseModule;
import apidez.com.databinding.model.api.IPurchaseApi;
import apidez.com.databinding.viewmodel.IPurchaseViewModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by nongdenchet on 10/30/15.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = CustomBuildConfig.class, sdk = 21,
        manifest = "src/main/AndroidManifest.xml",
        application = UnitTestApplication.class)
public class PurchaseActivityTest {
    private PurchaseActivity activity;
    private UnitTestApplication application;
    private IPurchaseViewModel mockViewModel;
    
    @Before
    public void setup()  {
        // Mocking
        mockViewModel = Mockito.mock(IPurchaseViewModel.class);
        PurchaseModule mockModule = new PurchaseModule() {
            @Override
            public IPurchaseViewModel providePurchaseViewModel(IPurchaseApi PurchaseApi) {
                return mockViewModel;
            }
        };

        // Setup application
        application = (UnitTestApplication) RuntimeEnvironment.application;
        application.setComponentBuilder(new ComponentBuilder(application.component()) {
            @Override
            public PurchaseComponent purchaseComponent() {
                return application.component().plus(mockModule);
            }
        });
        activity = Robolectric.buildActivity(PurchaseActivity.class).create().get();
    }

    @Test
    public void checkActivityNotNull() throws Exception {
        assertNotNull(activity);
    }

    @Test
    public void shouldHaveCreditCard() throws Exception {
        EditText edtCredit = (EditText) activity.findViewById(R.id.creditCard);
        assertNotNull(edtCredit);
    }

    @Test
    public void shouldHaveHintCreditCard() throws Exception {
        TextInputLayout inputLayout = (TextInputLayout) activity.findViewById(R.id.layoutCreditCard);
        assertNotNull(inputLayout);
        assertEquals("Credit Card", inputLayout.getHint());
    }

    @Test
    public void shouldHaveEmail() throws Exception {
        EditText edtCredit = (EditText) activity.findViewById(R.id.email);
        assertNotNull(edtCredit);
    }

    @Test
    public void shouldHaveHintEmail() throws Exception {
        TextInputLayout inputLayout = (TextInputLayout) activity.findViewById(R.id.layoutEmail);
        assertNotNull(inputLayout);
        assertEquals("Email", inputLayout.getHint());
    }
}