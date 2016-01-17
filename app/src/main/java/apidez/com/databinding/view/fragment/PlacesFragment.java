package apidez.com.databinding.view.fragment;

import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import javax.inject.Inject;

import apidez.com.databinding.MyApplication;
import apidez.com.databinding.R;
import apidez.com.databinding.databinding.FragmentPlacesBinding;
import apidez.com.databinding.view.adapter.PlacesAdapter;
import apidez.com.databinding.viewmodel.IPlacesViewModel;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class PlacesFragment extends BaseFragment {
    private FragmentPlacesBinding binding;
    private ProgressDialog mProgressDialog;

    @Inject
    IPlacesViewModel mViewModel;

    public static PlacesFragment newInstance() {
        PlacesFragment fragment = new PlacesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public PlacesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        // Setup DI
        ((MyApplication) getActivity().getApplication())
                .builder()
                .placesComponent()
                .inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_places, container, false);
        binding.setViewModel(mViewModel);
        setupView();
        return binding.getRoot();
    }

    private void setupView() {
        // Toolbar
        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Setup recycler view
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(new PlacesAdapter(getContext()));

        // Swipe to refresh
        binding.swipeRefresh.setOnRefreshListener(this::downloadData);

        // Progress dialog
        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setMessage(getString(R.string.loading));
        mProgressDialog.setCancelable(false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel.progress()
                .observeOn(AndroidSchedulers.mainThread())
                .takeUntil(preDestroy())
                .subscribe(done -> {
                    if (done) {
                        mProgressDialog.hide();
                    } else {
                        mProgressDialog.show();
                    }
                });
        downloadData();
    }

    private void downloadData() {
        // fetch all places
        mViewModel.fetchAllPlaces()
                .takeUntil(preDestroy())
                .subscribe(success -> {
                            binding.swipeRefresh.setRefreshing(false);
                        },
                        throwable -> {
                            Toast.makeText(getContext(), "Problem occurs", Toast.LENGTH_SHORT);
                            binding.swipeRefresh.setRefreshing(false);
                        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_places, menu);
    }

    @Override
    public void onDestroyView() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
        super.onDestroyView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
            case R.id.action_cafe:
            case R.id.action_food:
            case R.id.action_store:
            case R.id.action_theater:
            case R.id.action_restaurant:
            case R.id.action_all:
                // Filter the items
                mViewModel.filterPlacesByType(item.getTitle().toString());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
