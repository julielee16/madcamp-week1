package com.example.firstproject.ui.main;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class GalleryAdapter extends BaseAdapter {

    private Context context;
    private int[] images;

    public GalleryAdapter(Context c, int[] images) {
        context = c;
        this.images = images;
    }

    // returns the number of images
    public int getCount() {
        return images.length;
    }

    // returns the ID of an item
    public Object getItem(int position) {
        return position;
    }

    // returns the ID of an item
    public long getItemId(int position) {
        return position;
    }

    // returns an ImageView view
    public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 400));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(images[position]);
            return imageView;
    }
}


