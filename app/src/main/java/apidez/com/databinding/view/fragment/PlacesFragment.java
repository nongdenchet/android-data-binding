package apidez.com.databinding.view.fragment;

import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import apidez.com.databinding.MyApplication;
import apidez.com.databinding.R;
import apidez.com.databinding.databinding.FragmentPlacesBinding;
import apidez.com.databinding.view.adapter.PlacesAdapter;
import apidez.com.databinding.viewmodel.IPlacesViewModel;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PlacesFragment extends BaseFragment {
    private ProgressDialog mProgressDialog;
    private PlacesAdapter mPlacesAdapter;
    private FragmentPlacesBinding binding;

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

        // Progress dialog setup
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage(getString(R.string.loading));
        mProgressDialog.setCancelable(false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // fetch all places
        mViewModel.fetchAllPlaces()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .takeUntil(preDestroy())
                .doOnSubscribe(mProgressDialog::show)
                .doOnTerminate(mProgressDialog::hide)
                .subscribe(succes -> {}, throwable -> {});
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_places, menu);
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
