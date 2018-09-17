package com.waracle.androidtest.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.waracle.androidtest.R;
import com.waracle.androidtest.model.Cake;
import com.waracle.androidtest.utils.ImageUtil;

import java.util.ArrayList;
import java.util.List;

public class CakeAdapter extends BaseAdapter {

    private final List<Cake> cakeList;

    public CakeAdapter() {
        cakeList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return cakeList.size();
    }

    @Override
    public Object getItem(int position) {
        return cakeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder;

        if(view == null) {

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_layout, parent, false);
            viewHolder = new ViewHolder();
            view.setTag(viewHolder);

            viewHolder.title = view.findViewById(R.id.title);
            viewHolder.desc = view.findViewById(R.id.desc);
            viewHolder.image = view.findViewById(R.id.image);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        final Cake cake = (Cake) getItem(position);
        viewHolder.title.setText(cake.getTitle());
        viewHolder.desc.setText(cake.getDesc());
        viewHolder.image.setImageResource(R.drawable.nocake);

        ImageUtil.load(cake.getImageUrl(), viewHolder.image);

        return view;
    }

    public void setItems(List<Cake> cake) {
        cakeList.clear();
        cakeList.addAll(cake);

        notifyDataSetChanged();
    }

    static class ViewHolder {
        TextView title;
        TextView desc;
        ImageView image;
    }
}
