package com.waracle.androidtest.presenters;

import android.os.AsyncTask;

import com.waracle.androidtest.listeners.OnItemsLoadedListener;
import com.waracle.androidtest.model.Cake;
import com.waracle.androidtest.network.TheCakeTask;
import com.waracle.androidtest.view.CakeView;

import java.lang.ref.WeakReference;
import java.util.List;

public class CakePresenterImpl implements CakePresenter {

    private WeakReference<CakeView> viewRef; // so it can be garbage collected if it's not in use anymore
    private AsyncTask<Void, Void, List<Cake>> cakeTask;

    @Override
    public void loadCakes() {
        cakeTask = new TheCakeTask(new OnItemsLoadedListener() {
            @Override
            public void onItemsLoaded(List<Cake> newCake) {

                if(viewRef != null && viewRef.get() != null) {  // check if view hasn't been garbage collected
                     viewRef.get().showCakes(newCake);
                }
            }
        }).execute();
    }

    @Override
    public void attach(CakeView view) {
        this.viewRef = new WeakReference<>(view);
    }

    @Override
    public void detach() {
        cakeTask.cancel(true);
        viewRef =  null;
    }
}
