package com.waracle.androidtest.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ListFragment;
import android.view.MenuItem;
import android.view.View;

import com.waracle.androidtest.R;
import com.waracle.androidtest.adapter.CakeAdapter;
import com.waracle.androidtest.model.Cake;
import com.waracle.androidtest.presenters.CakePresenterImpl;
import com.waracle.androidtest.presenters.CakePresenter;
import com.waracle.androidtest.view.CakeView;

import java.util.List;

public class CakeFragment extends ListFragment implements CakeView {

    private CakePresenter presenter;
    private CakeAdapter adapter;

    public static CakeFragment newInstance() {
        return new CakeFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);
        presenter = new CakePresenterImpl();
        adapter = new CakeAdapter();
        setListAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_refresh:
                presenter.loadCakes();
                return true;
            default:
                return  super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.attach(this);
        presenter.loadCakes();
    }

    @Override
    public void onStop() {
        presenter.detach();
        super.onStop();
    }

    @Override
    public void showCakes(List<Cake> cake) {
        adapter.setItems(cake);
    }
}
