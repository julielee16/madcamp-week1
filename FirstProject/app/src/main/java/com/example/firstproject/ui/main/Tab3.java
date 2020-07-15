package com.example.firstproject.ui.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.example.firstproject.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;

import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Tab3 extends Fragment {
    private static final String YEAR = "year";
    private static final String MONTH = "month";
    private int month = 0;
    private int year = 0;
    private static LinkedList<Mood> mMoodList = new LinkedList<>();
    private static RecyclerView mRecyclerView;
    private TextView mMonthView;
    private TextView mYearView;
    private MoodListAdapter mAdapter;
    private Button mRightButton;
    private Button mLeftButton;
    private static Context context;
    String[] month_list = new String[]{"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};

    public Tab3() {
        // Required empty public constructor
    }

    public static Tab3 newInstance(int year, int month) {
        Tab3 fragment = new Tab3();
        Bundle args = new Bundle();
        args.putInt(YEAR, year);
        args.putInt(MONTH, month);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            year = getArguments().getInt(YEAR);
            month = getArguments().getInt(MONTH);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab3, container, false);
        context = getActivity();

/*
        try {
            jsonCreator();
        } catch (JSONException e) {
            Log.d("d", "went to exception1");
            e.printStackTrace();
        } catch (IOException e) {
            Log.d("d", "went to exception2");
            e.printStackTrace();
        }
*/
        switch(month) {
            case 1:
                view.setBackgroundResource(R.drawable.january_background); break;
            case 2:
                view.setBackgroundResource(R.drawable.february_background); break;
            case 3:
                view.setBackgroundResource(R.drawable.march_background); break;
            case 4:
                view.setBackgroundResource(R.drawable.april_background); break;
            case 5:
                view.setBackgroundResource(R.drawable.may_background); break;
            case 6:
                view.setBackgroundResource(R.drawable.june_background); break;
            case 7:
                view.setBackgroundResource(R.drawable.july_background); break;
            case 8:
                view.setBackgroundResource(R.drawable.august_background); break;
            case 9:
                view.setBackgroundResource(R.drawable.september_background); break;
            case 10:
                view.setBackgroundResource(R.drawable.october_background); break;
            case 11:
                view.setBackgroundResource(R.drawable.november_background); break;
            case 12:
                view.setBackgroundResource(R.drawable.december_background); break;
        }


        if (month < 1) {
            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat curFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.KOREA);
            String curr_time = curFormatter.format(c);
            year = Integer.parseInt(curr_time.substring(6, 10));
            month = Integer.parseInt(curr_time.substring(3, 5));
        }

        if(context != null) {
            // Get the list of contacts from the phone
            try {
                mMoodList = getMoodList(year, month);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // Get a handle to the RecyclerView.
            mRecyclerView = view.findViewById(R.id.recyclerview_mood);
            // Create an adapter and supply the data to be displayed.
            mAdapter = new MoodListAdapter(context, mMoodList);
            // Connect the adapter with the RecyclerView.
            mRecyclerView.setAdapter(mAdapter);
            // Give the RecyclerView a default layout manager.
            mRecyclerView.setLayoutManager(new GridLayoutManager(context, 5));
            mMonthView = view.findViewById(R.id.month_info);
            mMonthView.setText(month_list[month-1].substring(0, 3));
            mYearView = view.findViewById(R.id.year_info);
            mYearView.setText(Integer.toString(year));

            mRightButton = view.findViewById(R.id.right_arrow);
            mRightButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (month == 12) {
                        month = 1;
                        year += 1;
                    }
                    else {
                        month += 1;
                    }
                    openMonthFragment(year, month);
                }
            });

            mLeftButton = view.findViewById(R.id.left_arrow);
            mLeftButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (month == 1) {
                        month = 12;
                        year -= 1;
                    }
                    else {
                        month -= 1;
                    }
                    openMonthFragment(year, month);
                }
            });
        }
        return view;
    }

    @SuppressLint("MissingPermission")
    private LinkedList<Mood> getMoodList(int year, int month) throws IOException, JSONException {
        LinkedList<Mood> moodList = new LinkedList<Mood>();

        String json = null;
        InputStream is = context.getAssets().open("moodlist.json");
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();
        json = new String(buffer, "UTF-8");

        JSONObject jsonObject = new JSONObject(json);
        JSONArray jsonMoodList = jsonObject.getJSONArray("moodlist");

        for(int i = 0; i < jsonMoodList.length(); i++) {
            JSONObject moodObject = jsonMoodList.getJSONObject(i);
            if(Integer.parseInt(moodObject.getString("year")) == year && Integer.parseInt(moodObject.getString("month")) == month) {
                Mood aMood = new Mood(Integer.parseInt(moodObject.getString("year")),
                        Integer.parseInt(moodObject.getString("month")),
                        Integer.parseInt(moodObject.getString("date")), moodObject.get("day").toString()
                        , moodObject.get("mood").toString());
                moodList.add(aMood);
            }
        }

        return moodList;
    }

    public void openMonthFragment(int year, int month) {
        Tab3 fragment = Tab3.newInstance(year, month);
        FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
        fragmentManager.popBackStack();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
        //transaction.addToBackStack(null);
        transaction.add(R.id.mood_list_fragment_container, fragment, "MOOD_LIST_FRAGMENT").commit();
        //mAdapter.notifyDataSetChanged();
    }

    /*
    public void jsonCreator() throws JSONException, IOException {
        final int start = 2019;
        final int end = 2021;
        boolean leap = false;
        int day_counter = 0;
        int[] month_days = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        String[] month_list = new String[]{"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
        String[] day_of_the_week = new String[]{"tue", "wed", "thu", "fri", "sat", "sun", "mon"};

        File file = new File(context.getFilesDir(), "testfile.json");
        FileOutputStream fos = new FileOutputStream(file);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        bw.write("{");
        bw.write("\n");

        // Find if the year is a leap year
        for(int i = start; i < end; i++) {

            if(i % 4 != 0) {
                leap = false;
            }
            else {
                if(i % 100 != 100) {
                    leap = true;
                }
                else {
                    if(i % 400 == 0) {
                        leap = true;
                    }
                }
            }

            //
            for(int k = 0; k < 12; k++) {
                int num_of_days = month_days[k];
                if(leap && k == 1) {
                    num_of_days += 1;
                }
                for(int j = 0; j < num_of_days; j++) {
                    JSONObject obj = new JSONObject();
                    obj.put("year", Integer.toString(i));
                    obj.put("month", Integer.toString(k+1));
                    obj.put("date", Integer.toString(j+1));
                    obj.put("day", day_of_the_week[day_counter%7]);
                    obj.put("mood", "none");
                    day_counter += 1;
                    bw.write(obj.toString(3));
                    bw.write(",\n");
                }
            }
        }
        bw.write("}");
        bw.close();
        fos.close();
    }
*/
}