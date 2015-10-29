package apidez.com.databinding.view.activity;

import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import javax.inject.Inject;

import apidez.com.databinding.MyApplication;
import apidez.com.databinding.R;
import apidez.com.databinding.databinding.ActivityPurchaseBinding;
import apidez.com.databinding.dependency.module.PurchaseModule;
import apidez.com.databinding.utils.TextWatcherAdapter;
import apidez.com.databinding.utils.UiUtils;
import apidez.com.databinding.view.handler.IPurchaseHandler;
import apidez.com.databinding.viewmodel.IPurchaseViewModel;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by nongdenchet on 10/28/15.
 */
public class PurchaseActivity extends BaseActivity implements IPurchaseHandler {
    @Inject
    IPurchaseViewModel mViewModel;

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);

        // Setup DI
        ((MyApplication) getApplication()).component()
                .plus(new PurchaseModule())
                .inject(this);

        // bind viewmodel
        bindViewModel();

        // setup other views
        setUpView();
    }

    private void bindViewModel() {
        ActivityPurchaseBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_purchase);
        binding.setViewModel(mViewModel);
        binding.setHandler(this);
    }

    private void setUpView() {
        // Progress dialog
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(R.string.loading));
        mProgressDialog.setCancelable(false);
    }

    @Override
    public TextWatcher emailWatcher() {
        return new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                mViewModel.setEmail(s.toString());
            }
        };
    }

    @Override
    public TextWatcher creditCardWatcher() {
        return new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                mViewModel.setCreditCard(s.toString());
            }
        };
    }

    @Override
    public View.OnClickListener onSubmit() {
        return v -> mViewModel.submit()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .takeUntil(preDestroy())
                .doOnSubscribe(mProgressDialog::show)
                .doOnTerminate(mProgressDialog::hide)
                .subscribe(done -> {
                    UiUtils.showDialog(getString(R.string.success), this);
                }, throwable -> {
                    UiUtils.showDialog(getString(R.string.error), this);
                });
    }
}
