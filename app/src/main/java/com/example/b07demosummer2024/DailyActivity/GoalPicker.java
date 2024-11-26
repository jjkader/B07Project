package com.example.b07demosummer2024.DailyActivity;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;

import com.example.b07demosummer2024.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class GoalPicker extends AppCompatActivity {
    int[] goalProgress = new int[7];
    CheckBox pick1, pick2, pick3, pick4, pick5, pick6, pick7;

    private String str = "";
    public GoalPicker() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {







        Arrays.fill(goalProgress, -1);
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_goal_picker);

        Button nextButton = findViewById(R.id.nextButton);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.goalPicker), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        nextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.goalPicker, new GoalTrackerFragment().newInstance(goalProgress));
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        DatabaseReference db = FirebaseDatabase.getInstance(
                "https://b07project-725cc-default-rtdb.firebaseio.com/").getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        DatabaseReference yearlyData = db.child("users").child(uid).child("Yearly Data");
        yearlyData.child("carType").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    str = String.valueOf(task.getResult().getValue());
                    if (str.equals("Gasoline")){
                        pick1 = findViewById(R.id.pick1);
                        str = pick1.getText() + " (RECOMMENDED FOR YOU)";
                        pick1.setText(str);
                    }
                }
            }
        });

        yearlyData.child("howOftenRecycle").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    str = String.valueOf(task.getResult().getValue());
                    if (str.equals("Never")){
                        pick2 = findViewById(R.id.pick2);
                        str = pick2.getText() + " (RECOMMENDED FOR YOU)";
                        pick2.setText(str);
                    }
                }
            }
        });

        yearlyData.child("foodWaste").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    str = String.valueOf(task.getResult().getValue());
                    if (str.equals("Frequently")){
                        pick3 = findViewById(R.id.pick3);
                        str = pick3.getText() + " (RECOMMENDED FOR YOU)";
                        pick3.setText(str);
                    }
                }
            }
        });

        yearlyData.child("consumpEmis").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    str = String.valueOf(task.getResult().getValue());
                    try {
                        if (Double.parseDouble(str) >= 600) {
                            pick4 = findViewById(R.id.pick4);
                            pick5 = findViewById(R.id.pick5);
                            str = pick4.getText() + " (RECOMMENDED FOR YOU)";
                            pick4.setText(str);
                            str = pick5.getText() + " (RECOMMENDED FOR YOU)";
                            pick5.setText(str);
                        }
                    }
                    catch (Exception e){
                        Log.e("firebase", "Error getting data", task.getException());
                    }
                }
            }
        });

        yearlyData.child("houseEmis").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    str = String.valueOf(task.getResult().getValue());
                    try {
                        if (Double.parseDouble(str) >= 3000) {
                            pick6 = findViewById(R.id.pick6);
                            str = pick6.getText() + " (RECOMMENDED FOR YOU)";
                            pick6.setText(str);
                        }
                    }
                    catch (Exception e){
                        Log.e("firebase", "Error getting data", task.getException());
                    }
                }
            }
        });

        yearlyData.child("buyEcoClothes").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    str = String.valueOf(task.getResult().getValue());
                    if (str.equals("Regularly")){
                        pick7 = findViewById(R.id.pick7);
                        str = pick7.getText() + " (RECOMMENDED FOR YOU)";
                        pick7.setText(str);
                    }
                }
            }
        });

    }
    public void pickGoals(View view){
        boolean checked = ((CheckBox) view).isChecked();
        int id = view.getId();
        if (id == R.id.pick1) {
            if (checked) {
                goalProgress[0] = 0;
            } else {
                goalProgress[0] = -1;
            }
        } else if (id == R.id.pick2) {
            if (checked) {
                goalProgress[1] = 0;
            } else {
                goalProgress[1] = -1;
            }
        }
        if (id == R.id.pick3) {
            if (checked) {
                goalProgress[2] = 0;
            } else {
                goalProgress[2] = -1;
            }
        }
        if (id == R.id.pick4) {
            if (checked) {
                goalProgress[3] = 0;
            } else {
                goalProgress[3] = -1;
            }
        }
        if (id == R.id.pick5) {
            if (checked) {
                goalProgress[4] = 0;
            } else {
                goalProgress[4] = -1;
            }
        }
        if (id == R.id.pick6) {
            if (checked) {
                goalProgress[5] = 0;
            } else {
                goalProgress[5] = -1;
            }
        }
        if (id == R.id.pick7) {
            if (checked) {
                goalProgress[6] = 0;
            } else {
                goalProgress[6] = -1;
            }
        }
    }
    public void onCheckBoxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        int id = view.getId();
        if (id == R.id.goal1) {
            if (checked) {
                goalProgress[0] = 1;
            } else {
                goalProgress[0] = 0;
            }
        } else if (id == R.id.goal2) {
            if (checked) {
                goalProgress[1] = 1;
            } else {
                goalProgress[1] = 0;
            }
        }
        if (id == R.id.goal3) {
            if (checked) {
                goalProgress[2] = 1;
            } else {
                goalProgress[2] = 0;
            }
        }
        if (id == R.id.goal4) {
            if (checked) {
                goalProgress[3] = 1;
            } else {
                goalProgress[3] = 0;
            }
        }
        if (id == R.id.goal5) {
            if (checked) {
                goalProgress[4] = 1;
            } else {
                goalProgress[4] = 0;
            }
        }
        if (id == R.id.goal6) {
            if (checked) {
                goalProgress[5] = 1;
            } else {
                goalProgress[5] = 0;
            }
        }
        if (id == R.id.goal7) {
            if (checked) {
                goalProgress[6] = 1;
            } else {
                goalProgress[6] = 0;
            }
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.goalPicker, new GoalTrackerFragment().newInstance(goalProgress));
        transaction.addToBackStack(null);
        transaction.commit();
    }
}