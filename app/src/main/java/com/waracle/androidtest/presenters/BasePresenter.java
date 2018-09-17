package com.waracle.androidtest.presenters;

import android.view.View;

import com.waracle.androidtest.view.CakeView;


/**
 * This is the base implementation for presenters, that all presenters must implement
 *
 * @param <V> the MVP Views
 */

public interface BasePresenter<V extends View> {

    void attach(CakeView view);

    void detach();
}
