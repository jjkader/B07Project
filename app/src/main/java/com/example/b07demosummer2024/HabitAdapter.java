package com.example.b07demosummer2024;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ValueEventListener;

public class HabitAdapter extends RecyclerView.Adapter<HabitViewHolder> {
    public static DatabaseReference db = FirebaseDatabase.getInstance("https://b07project-725cc-default-rtdb.firebaseio.com/").getReference();
    public static FirebaseAuth auth = FirebaseAuth.getInstance();

    public static FirebaseUser user = auth.getCurrentUser();
    public static String uid = user.getUid();
    public static DatabaseReference userRef = db.child("users").child(uid);
    public static DatabaseReference userHabitsRef = userRef.child("Habits");

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
        userHabitsRef.child(habits.get(position).id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Get the boolean value from the snapshot
                    boolean habitStatus = dataSnapshot.getValue(Boolean.class);

                    // Set the checkbox state based on the retrieved boolean value
                    holder.activeView.setChecked(habitStatus);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.err.println("Error: " + databaseError.getMessage());
            }
        });


        holder.activeView.setOnCheckedChangeListener((buttonView, isChecked) -> {
            String message;
            if (isChecked){message = "checked ID: " + habits.get(position).id;}
            else{message = "unchecked ID:" + habits.get(position).id;}
            Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show();

            userHabitsRef.child(habits.get(position).id).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Get the boolean value from the snapshot
                        boolean habitStatus = dataSnapshot.getValue(Boolean.class);

                        // Set the checkbox state based on the retrieved boolean value
                        userHabitsRef.setValue(!habitStatus);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.err.println("Error: " + databaseError.getMessage());
                }
            });

        });
    }

    @Override
    public int getItemCount() {
        return habits.size();
    }

    public void setFilteredList(ArrayList<Habit> searched){
        this.habits = searched;
    }

}