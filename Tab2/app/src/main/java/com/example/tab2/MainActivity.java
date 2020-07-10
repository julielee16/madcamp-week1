package com.example.tab2;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private Animator currentAnimator;
    private int shortAnimationDuration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final View thumb1View = findViewById(R.id.img1);
        thumb1View.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                zoomImageFromThumb(thumb1View, R.id.inv_img1);
            }
        });
        final View thumb2View = findViewById(R.id.img2);
        thumb2View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zoomImageFromThumb(thumb2View, R.id.inv_img2);
            }
        });
        final View thumb3View = findViewById(R.id.img3);
        thumb3View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zoomImageFromThumb(thumb3View, R.id.inv_img3);
            }
        });
        final View thumb4View = findViewById(R.id.img4);
        thumb4View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zoomImageFromThumb(thumb4View, R.id.inv_img4);
            }
        });
        final View thumb5View = findViewById(R.id.img5);
        thumb5View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zoomImageFromThumb(thumb5View, R.id.inv_img5);
            }
        });
        final View thumb6View = findViewById(R.id.img6);
        thumb6View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zoomImageFromThumb(thumb6View, R.id.inv_img6);
            }
        });
        final View thumb7View = findViewById(R.id.img7);
        thumb7View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zoomImageFromThumb(thumb7View, R.id.inv_img7);
            }
        });
        final View thumb8View = findViewById(R.id.img8);
        thumb8View.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                zoomImageFromThumb(thumb8View,  R.id.inv_img8);
            }
        });
        final View thumb9View = findViewById(R.id.img9);
        thumb9View.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                zoomImageFromThumb(thumb9View, R.id.inv_img9);
            }
        });
        final View thumb10View = findViewById(R.id.img10);
        thumb10View.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                zoomImageFromThumb(thumb10View, R.id.inv_img10);
            }
        });
        final View thumb11View = findViewById(R.id.img11);
        thumb11View.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                zoomImageFromThumb(thumb11View, R.id.inv_img11);
            }
        });
        final View thumb12View = findViewById(R.id.img12);
        thumb12View.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                zoomImageFromThumb(thumb12View, R.id.inv_img12);
            }
        });
        final View thumb13View = findViewById(R.id.img13);
        thumb13View.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                zoomImageFromThumb(thumb13View, R.id.inv_img13);
            }
        });
        final View thumb14View = findViewById(R.id.img14);
        thumb14View.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                zoomImageFromThumb(thumb14View, R.id.inv_img14);
            }
        });
        final View thumb15View = findViewById(R.id.img15);
        thumb15View.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                zoomImageFromThumb(thumb15View, R.id.inv_img15);
            }
        });
        final View thumb16View = findViewById(R.id.img16);
        thumb16View.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                zoomImageFromThumb(thumb16View, R.id.inv_img16);
            }
        });
        final View thumb17View = findViewById(R.id.img17);
        thumb17View.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                zoomImageFromThumb(thumb17View, R.id.inv_img17);
            }
        });
        final View thumb18View = findViewById(R.id.img18);
        thumb18View.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                zoomImageFromThumb(thumb18View, R.id.inv_img18);
            }
        });
        final View thumb19View = findViewById(R.id.img19);
        thumb19View.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                zoomImageFromThumb(thumb19View, R.id.inv_img19);
            }
        });
        final View thumb20View = findViewById(R.id.img20);
        thumb20View.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                zoomImageFromThumb(thumb20View, R.id.inv_img20);
            }
        });
    }

    private void zoomImageFromThumb(final View thumbView, int invisibleImage) {
        if (currentAnimator != null) {
            currentAnimator.cancel();
        }
        final ImageView expandedImageView = (ImageView) findViewById(invisibleImage);
        expandedImageView.setVisibility(View.VISIBLE);
        thumbView.setAlpha(0f);

        expandedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentAnimator != null) { currentAnimator.cancel(); }
                AnimatorSet set = new AnimatorSet();
                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        currentAnimator = null;
                    }
                    @Override
                    public void onAnimationCancel(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        currentAnimator = null;
                    }
                });
                set.start();
                currentAnimator = set;
            }
        });
    }
}