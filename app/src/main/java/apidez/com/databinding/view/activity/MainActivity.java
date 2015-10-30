package apidez.com.databinding.view.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import apidez.com.databinding.R;
import apidez.com.databinding.databinding.ActivityMainBinding;
import apidez.com.databinding.view.handler.IMainHandler;

/**
 * Created by nongdenchet on 10/30/15.
 */
public class MainActivity extends BaseActivity implements IMainHandler {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setHandler(this);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public View.OnClickListener formSample() {
        return v -> startActivity(new Intent(this, PurchaseActivity.class));
    }

    @Override
    public View.OnClickListener recyclerSample() {
        return v -> startActivity(new Intent(this, PlacesActivity.class));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
