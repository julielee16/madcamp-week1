package com.example.firstproject.ui.main;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.firstproject.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Tab2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tab2 extends Fragment {

    // TODO: Rename and change types of parameters
    private Context context;
    private GridView gridview;
    private ImageView selectedImage;
    private Animator currentAnimator;
    private ArrayList<Bitmap> gallery;
    private TextView no_i;


    public Tab2() {
        // Required empty public constructor
    }

    public static Tab2 newInstance(String param1, String param2) {
        Tab2 fragment = new Tab2();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_tab2, container, false);
        context = getActivity();
        if(context != null) {
            no_i = (TextView) view.findViewById(R.id.noimg);
            gallery = fetchGalleryImages(context);
            if (gallery.size()==0){
                no_i.setVisibility(View.VISIBLE);
            } else {
                no_i.setVisibility(View.GONE);
                gridview = (GridView) view.findViewById(R.id.gridView);
                gridview.setAdapter(new GalleryAdapter(context, gallery));
                selectedImage = (ImageView) view.findViewById(R.id.selectedImageView);
                pictureWork();
            }

        }
        return view;
    }

    protected void pictureWork() {
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (currentAnimator != null) { currentAnimator.cancel(); }
                selectedImage.setImageBitmap(gallery.get(position));
                selectedImage.setVisibility(View.VISIBLE);
                gridview.setAlpha(0f);

                selectedImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (currentAnimator != null) { currentAnimator.cancel(); }
                        AnimatorSet set = new AnimatorSet();
                        set.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                gridview.setAlpha(1f);
                                selectedImage.setVisibility(View.GONE);
                                currentAnimator = null;
                            }
                            @Override
                            public void onAnimationCancel(Animator animation) {
                                gridview.setAlpha(1f);
                                selectedImage.setVisibility(View.GONE);
                                currentAnimator = null;
                            }
                        });
                        set.start();
                        currentAnimator = set;
                    }
                });
            }
        });
    }

    public  ArrayList<Bitmap> fetchGalleryImages(Context context) {
        ArrayList<Bitmap> galleryImageUrls = new ArrayList<Bitmap>();
        final String[] columns = {MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID};//get all columns of type images
        final String orderBy = MediaStore.Images.Media.DATE_TAKEN;//order data by date

        Cursor imagecursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, orderBy + " DESC");//get all data in Cursor by sorting in DESC order

        for (int i = 0; i < imagecursor.getCount(); i++) {
            imagecursor.moveToPosition(i);
            int dataColumnIndex = imagecursor.getColumnIndex(MediaStore.Images.Media.DATA);//get column index
            String picturePath = imagecursor.getString(dataColumnIndex);
            Bitmap loadedBitmap = BitmapFactory.decodeFile(picturePath);
            ExifInterface exif = null;
            try {
                File pictureFile = new File(picturePath);
                exif = new ExifInterface(pictureFile.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }

            int orientation = ExifInterface.ORIENTATION_NORMAL;

            if (exif != null)
                orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    loadedBitmap = rotateBitmap(loadedBitmap, 90);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    loadedBitmap = rotateBitmap(loadedBitmap, 180);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    loadedBitmap = rotateBitmap(loadedBitmap, 270);
                    break;
            }
            galleryImageUrls.add(loadedBitmap);//get Image from column index
        }
        return galleryImageUrls;
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int degrees) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

}