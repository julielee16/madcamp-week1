package com.example.firstproject.ui.main;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.firstproject.R;

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

    private int[] images = {R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img4, R.drawable.img5,
            R.drawable.img6, R.drawable.img7, R.drawable.img8, R.drawable.img9, R.drawable.img10, R.drawable.img11, R.drawable.img12, R.drawable.img13, R.drawable.img14,
            R.drawable.img15, R.drawable.img16, R.drawable.img17, R.drawable.img18, R.drawable.img19, R.drawable.img20, R.drawable.img21};


    public Tab2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tab2.
     */
    // TODO: Rename and change types and number of parameters
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
            gridview = (GridView) view.findViewById(R.id.gridView);
            gridview.setAdapter(new GalleryAdapter(context, images));
            selectedImage = (ImageView) view.findViewById(R.id.selectedImageView);
            pictureWork();
        }
        return view;
    }

    protected void pictureWork() {
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (currentAnimator != null) {
                    currentAnimator.cancel();
                }
                selectedImage.setImageResource(images[position]);
                selectedImage.setVisibility(View.VISIBLE);
                gridview.setAlpha(0f);

                selectedImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (currentAnimator != null) {
                            currentAnimator.cancel();
                        }
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
}