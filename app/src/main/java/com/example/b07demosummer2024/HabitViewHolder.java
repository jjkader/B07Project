package com.example.b07demosummer2024;

import android.media.Image;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HabitViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView typeView, impactView, descriptionView;
    CheckBox activeView;

    public HabitViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageview);
        typeView = itemView.findViewById(R.id.type);
        impactView = itemView.findViewById(R.id.impact);
        descriptionView = itemView.findViewById(R.id.description);
        activeView = itemView.findViewById(R.id.selectHabit);
    }
}