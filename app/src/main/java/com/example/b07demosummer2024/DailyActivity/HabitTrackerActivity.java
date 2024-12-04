package com.example.b07demosummer2024.DailyActivity;

import static android.view.View.GONE;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.b07demosummer2024.HabitSearchActivity;
import com.example.b07demosummer2024.R;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Map;

public class HabitTrackerActivity extends AppCompatActivity {

    Map<String, Long> data;

    private ProgressBar pb;
    private TextView percentage;
    int total = 10;
    int comp = 0;
    int completed;
    long check = (long)-1;
    String p;

    CheckBox goal1, goal2, goal3, goal4, goal5, goal6, goal7, goal8, goal9, goal10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_habit_tracker);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.habitTracker), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        findViewById(R.id.homeButton).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.habitTracker, new EcoTrackerHomeFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        findViewById(R.id.goalButton).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Intent myIntent = new Intent(HabitTrackerActivity.this, HabitSearchActivity.class);
                HabitTrackerActivity.this.startActivity(myIntent);
            }
        });
        DatabaseReference db = FirebaseDatabase.getInstance(
                "https://b07project-725cc-default-rtdb.firebaseio.com/").getReference();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = currentUser.getUid();
        DatabaseReference habitData = db.child("users").child(uid);
        Task<DataSnapshot> task = getDataFromFirebase(habitData);
        Task<List<Object>> taskComplete = Tasks.whenAllSuccess(task);
        taskComplete.addOnSuccessListener(aVoid -> {

            if (task.getResult() == null) {
                Toast.makeText(HabitTrackerActivity.this, "There was an error retrieving the data", Toast.LENGTH_SHORT).show();
                getSupportFragmentManager().popBackStack();
            }
            data = (Map<String, Long>)(task.getResult().child("Habits").getValue());
            if (data.get("habit1") == check){
                findViewById(R.id.goal1).setVisibility(GONE);
                total--;
            }
            if (data.get("habit2") == check){
                findViewById(R.id.goal2).setVisibility(GONE);
                total--;
            }
            if (data.get("habit3") == check){
                findViewById(R.id.goal3).setVisibility(GONE);
                total--;
            }
            if (data.get("habit4") == check){
                findViewById(R.id.goal4).setVisibility(GONE);
                total--;
            }
            if (data.get("habit5") == check){
                findViewById(R.id.goal5).setVisibility(GONE);
                total--;
            }
            if (data.get("habit6") == check){
                findViewById(R.id.goal6).setVisibility(GONE);
                total--;
            }
            if (data.get("habit7") == check){
                findViewById(R.id.goal7).setVisibility(GONE);
                total--;
            }
            if (data.get("habit8") == check){
                findViewById(R.id.goal8).setVisibility(GONE);
                total--;
            }
            if (data.get("habit9") == check){
                findViewById(R.id.goal9).setVisibility(GONE);
                total--;
            }
            if (data.get("habit10") == check){
                findViewById(R.id.goal10).setVisibility(GONE);
                total--;
            }
            percentage = findViewById(R.id.percentage);
            pb = findViewById(R.id.progressBar);
            if (total != 0){
                completed = comp/total;
                pb.setProgress(completed);
                p = completed + "%";
                percentage.setText(p);
            }
        });
    }
        private Task<DataSnapshot> getDataFromFirebase(DatabaseReference ref) {
            final TaskCompletionSource<DataSnapshot> taskCompletionSource = new TaskCompletionSource<>();
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    taskCompletionSource.setResult(snapshot);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    taskCompletionSource.setException(error.toException());
                }
            });
            return taskCompletionSource.getTask();
        }
    public void onCheckBoxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        if (checked) {
            comp++;
        }
        else{
            comp--;
        }
        completed = 100*comp/total;
        pb.setProgress(completed);
        p = completed + "%";
        percentage.setText(p);
        int id = view.getId();
        if (id == R.id.goal1) {
            goal1 = (CheckBox) findViewById(R.id.goal1);
            boolean checkStatus = PreferenceManager.getDefaultSharedPreferences(this)
                    .getBoolean("goal1", checked);
            goal1.setChecked(checkStatus);
        }
        if (id == R.id.goal2) {
            goal2 = (CheckBox) findViewById(R.id.goal2);
            boolean checkStatus = PreferenceManager.getDefaultSharedPreferences(this)
                    .getBoolean("goal2", checked);
            goal2.setChecked(checkStatus);
        }
        if (id == R.id.goal3) {
            goal3 = (CheckBox) findViewById(R.id.goal3);
            boolean checkStatus = PreferenceManager.getDefaultSharedPreferences(this)
                    .getBoolean("goal3", checked);
            goal3.setChecked(checkStatus);
        }
        if (id == R.id.goal4) {
            goal4 = (CheckBox) findViewById(R.id.goal4);
            boolean checkStatus = PreferenceManager.getDefaultSharedPreferences(this)
                    .getBoolean("goal4", checked);
            goal4.setChecked(checkStatus);
        }
        if (id == R.id.goal5) {
            goal5 = (CheckBox) findViewById(R.id.goal5);
            boolean checkStatus = PreferenceManager.getDefaultSharedPreferences(this)
                    .getBoolean("goal5", checked);
            goal5.setChecked(checkStatus);
        }
        if (id == R.id.goal6) {
            goal6 = (CheckBox) findViewById(R.id.goal6);
            boolean checkStatus = PreferenceManager.getDefaultSharedPreferences(this)
                    .getBoolean("goal6", checked);
            goal6.setChecked(checkStatus);
        }
        if (id == R.id.goal7) {
            goal7 = (CheckBox) findViewById(R.id.goal7);
            boolean checkStatus = PreferenceManager.getDefaultSharedPreferences(this)
                    .getBoolean("goal7", checked);
            goal7.setChecked(checkStatus);
        }
        if (id == R.id.goal8) {
            goal8 = (CheckBox) findViewById(R.id.goal8);
            boolean checkStatus = PreferenceManager.getDefaultSharedPreferences(this)
                    .getBoolean("goal8", checked);
            goal8.setChecked(checkStatus);
        }
        if (id == R.id.goal9) {
            goal9 = (CheckBox) findViewById(R.id.goal9);
            boolean checkStatus = PreferenceManager.getDefaultSharedPreferences(this)
                    .getBoolean("goal9", checked);
            goal9.setChecked(checkStatus);
        }
        if (id == R.id.goal10) {
            goal10 = (CheckBox) findViewById(R.id.goal10);
            boolean checkStatus = PreferenceManager.getDefaultSharedPreferences(this)
                    .getBoolean("goal10", checked);
            goal10.setChecked(checkStatus);
        }
    }
}