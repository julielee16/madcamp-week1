package com.example.firstproject.ui.main;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstproject.R;

import java.util.LinkedList;

public class MoodListAdapter extends RecyclerView.Adapter<MoodListAdapter.MoodListViewHolder> {
    private final LinkedList<Mood> mMoodList;
    private LayoutInflater mInflater;
    private Context context;

    public MoodListAdapter(Context context, LinkedList<Mood> month_moods) {
        mInflater = LayoutInflater.from(context);
        this.mMoodList = month_moods;
        this.context = context;
    }

    @NonNull
    @Override
    public MoodListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.moodlist_item, parent, false);
        return new MoodListViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull MoodListViewHolder holder, int position) {
        int mDate = mMoodList.get(position).getDate();
        String mDay = mMoodList.get(position).getDay();
        holder.dateItemView.setText(Integer.toString(mDate));
        holder.dayItemView.setText(mDay.toUpperCase());
        String mood = mMoodList.get(position).getMood();
        if(mood != null) {
            if (mood.equals("joy")) {
                holder.date_holder.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.Joy));
            }
            if (mood.equals("sadness")) {
                holder.date_holder.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.Sadness));
            }
            if (mood.equals("anger")) {
                holder.date_holder.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.Anger));
            }
            if (mood.equals("neutral")) {
                holder.date_holder.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.Neutral));
            }
        }
    }

    @Override
    public int getItemCount() {
        return mMoodList.size();
    }

    class MoodListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final ConstraintLayout date_holder;
        public final TextView dateItemView;
        public final TextView dayItemView;
        public final TextView monthItemView;
        public final TextView yearItemView;
        public final String mood;
        public boolean filled = false;
        final MoodListAdapter mAdapter;
        public FrameLayout fragmentContainer;

        public MoodListViewHolder(View itemView, MoodListAdapter adapter) {
            super(itemView);
            date_holder = itemView.findViewById(R.id.date_holder);
            dateItemView = itemView.findViewById(R.id.date_info);
            dayItemView = itemView.findViewById(R.id.day_info);
            monthItemView = itemView.findViewById(R.id.month_info);
            yearItemView = itemView.findViewById(R.id.year_info);
            mood = "MOOD";
            fragmentContainer =
                    (FrameLayout) itemView.findViewById(R.id.mood_list_fragment_container);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // Get the position of the item that was clicked.
            int mPosition = getLayoutPosition();
            // Use that to access the affected item in mMoodList
            Mood element = mMoodList.get(mPosition);
            openFragment(mPosition);
            if(((MainActivity)context).getNewMood() != null && filled == false) {
                element.setMood(((MainActivity) context).getNewMood());
                mMoodList.set(mPosition, element);
                mAdapter.notifyDataSetChanged();
                ((MainActivity)context).setNewMood(null);
                filled = true;
            }
        }

        public void openFragment(int position) {
            MoodEditFragment fragment =
                    MoodEditFragment.newInstance(position);
            FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
            transaction.addToBackStack(null);
            transaction.add(R.id.mood_list_fragment_container, fragment,
                    "MOOD_LIST_FRAGMENT").commit();
        }
    }
}
