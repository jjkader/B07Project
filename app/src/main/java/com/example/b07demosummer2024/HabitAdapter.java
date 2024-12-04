package com.example.b07demosummer2024;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
    public static String transStr = "";
    public static String foodStr = "";
    public static String consumpStr = "";
    public static String str;
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
    public void onBindViewHolder(@NonNull  HabitViewHolder holder, @SuppressLint("RecyclerView") int position) {
        DatabaseReference yearlyData = userRef.child("Yearly Data");
        yearlyData.child("carType").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    transStr = String.valueOf(task.getResult().getValue());
                    if (transStr.equals("Gasoline")){
                        transStr = " (RECOMMENDED FOR YOU)";
                    }
                }
            }
        });
        yearlyData.child("transportEmis").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    transStr = String.valueOf(task.getResult().getValue());
                    if (Float.parseFloat((transStr)) >= 7000){
                        transStr = " (RECOMMENDED FOR YOU)";
                    }
                }
            }
        });
         yearlyData.child("foodEmis").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (task.isSuccessful()) {
                        foodStr = String.valueOf(task.getResult().getValue());
                        if (Float.parseFloat((foodStr)) >= 1000){
                            foodStr = " (RECOMMENDED FOR YOU)";
                        }
                    }
                }
         });
        yearlyData.child("consumpEmis").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    consumpStr = String.valueOf(task.getResult().getValue());
                    if (Float.parseFloat((consumpStr)) >= 600){
                        consumpStr = " (RECOMMENDED FOR YOU)";
                    }
                }
            }
        });
        if (Objects.equals(habits.get(position).type, "Transportation")){
            str = habits.get(position).type + transStr;
            holder.typeView.setText(str);
        }
        else if (Objects.equals(habits.get(position).type, "Food")){
            str = habits.get(position).type + foodStr;
            holder.typeView.setText(str);
        }
        else if (Objects.equals(habits.get(position).type, "Consumption")){
            str = habits.get(position).type + consumpStr;
            holder.typeView.setText(str);
        }
        else{
            holder.typeView.setText(habits.get(position).type);
        }
        holder.impactView.setText(habits.get(position).impact);
        holder.descriptionView.setText(habits.get(position).description);
        holder.imageView.setImageResource(habits.get(position).image);
        userHabitsRef.child(habits.get(position).id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Get the boolean value from the snapshot
                    int habitStatus = dataSnapshot.getValue(Integer.class);

                    // Set the checkbox state based on the retrieved boolean value
                    if (habitStatus == -1) {holder.activeView.setChecked(false);}
                    else {holder.activeView.setChecked(true);}
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
                        int habitStatus = dataSnapshot.getValue(Integer.class);

                        // Set the checkbox state based on the retrieved boolean value
                        userHabitsRef.child(habits.get(position).id).setValue(habitStatus*-1);
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