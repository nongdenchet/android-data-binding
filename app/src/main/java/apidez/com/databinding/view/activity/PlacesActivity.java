package apidez.com.databinding.view.activity;

/**
 * Created by nongdenchet on 10/21/15.
 */

import android.os.Bundle;

import apidez.com.databinding.R;
import apidez.com.databinding.view.fragment.PlacesFragment;

/**
 * User this for testing purpose only
 */
public class PlacesActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, PlacesFragment.newInstance())
                .commit();
    }
}
