package com.example.b07demosummer2024;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HabitAdapter extends RecyclerView.Adapter<HabitViewHolder> {


    Context context;
    ArrayList<Habit> habits;

    public HabitAdapter(Context context, ArrayList<Habit> habits) {
        this.context = context;
        this.habits = habits;
    }

    @NonNull
    @Override
    public HabitViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        return new HabitViewHolder(LayoutInflater.from(context).inflate(R.layout.habit_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull  HabitViewHolder holder, int position) {
        holder.typeView.setText(habits.get(position).type);
        holder.impactView.setText(habits.get(position).impact);
        holder.descriptionView.setText(habits.get(position).description);
        holder.imageView.setImageResource(habits.get(position).image);
    }

    @Override
    public int getItemCount() {
        return habits.size();
    }

    public void setFilteredList(ArrayList<Habit> searched){
        this.habits = searched;
    }
}