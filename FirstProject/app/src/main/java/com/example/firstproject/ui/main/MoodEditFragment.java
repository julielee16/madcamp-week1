package com.example.firstproject.ui.main;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.firstproject.R;
import android.graphics.Matrix;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class MoodEditFragment extends Fragment {
    private String[] feelings = {"Not Chosen", "Joy", "Sadness", "Anger", "Neutral"};
    private Spinner spinner;
    private Context context;
    private ImageView selectedImage;
    private Button edit_button;

    private static final String POS = "position";
    private int mPosition;
    private int PICK_IMAGE_REQUEST = 1;
    private LinearLayout layout;

    private final static int REQUEST_CODE_ASK_PERMISSIONS = 1;
    private static final String[] REQUIRED_SDK_PERMISSIONS = new String[] {Manifest.permission.READ_EXTERNAL_STORAGE};


    public MoodEditFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tab3.
     */
    // TODO: Rename and change types and number of parameters
    public static MoodEditFragment newInstance(int position) {
        MoodEditFragment fragment = new MoodEditFragment();
        Bundle args = new Bundle();
        args.putInt(POS, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPosition = getArguments().getInt(POS);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mood_edit, container, false);
        context = getActivity();
        if(context != null) {
            spinner = (Spinner) view.findViewById(R.id.spinner);
            setSpinnerData();
            layout = (LinearLayout) view.findViewById(R.id.tab3_layout);
            spinnerChosen();
            String mood = spinner.getSelectedItem().toString().toLowerCase();
            ((MainActivity)getActivity()).setNewMood(mood);
            selectedImage = (ImageView) view.findViewById(R.id.picAdded);
            setPic();
            edit_button = (Button) view.findViewById(R.id.mood_edit_button);
            edit_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getParentFragmentManager().popBackStack();
                }
            });
        }
        return view;
    }

    private void setSpinnerData() {
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, feelings);
        spinner.setAdapter(spinnerAdapter);
    }

    public void spinnerChosen(){
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get Selected Class name from the list
//                String selectedClass = parent.getItemAtPosition(position).toString();
                switch (position) {
                    case 0:layout.setBackgroundColor(Color.WHITE); break;
                    case 1:
                        layout.setBackgroundColor(getResources().getColor(R.color.Joy)); break;
                    case 2:
                        layout.setBackgroundColor(getResources().getColor(R.color.Sadness)); break;
                    case 3:
                        layout.setBackgroundColor(getResources().getColor(R.color.Anger)); break;
                    case 4:
                        layout.setBackgroundColor(getResources().getColor(R.color.Neutral)); break;
                }
            }
            public void onNothingSelected(AdapterView<?> parentView) {
                layout.setBackgroundColor(Color.WHITE);
            }
        });
    }


    protected void setPic() {
        selectedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                checkPermissions();
                Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                try {
                    startActivityForResult(Intent.createChooser(i, "Select Picture"), PICK_IMAGE_REQUEST);
                }catch (ActivityNotFoundException ex){
                    ex.printStackTrace();
                }
            }
        });
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getActivity().getContentResolver().query(uri, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            Bitmap loadedBitmap = BitmapFactory.decodeFile(picturePath);
            cursor.close();

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
            selectedImage.setImageBitmap(loadedBitmap);
        }
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int degrees) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }
}