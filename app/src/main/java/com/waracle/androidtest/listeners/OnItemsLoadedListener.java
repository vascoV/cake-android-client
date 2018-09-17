package com.waracle.androidtest.listeners;

import com.waracle.androidtest.model.Cake;

import java.util.List;

public interface OnItemsLoadedListener {
    void onItemsLoaded(List<Cake> newCake);
}
