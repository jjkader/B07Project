package com.example.b07demosummer2024;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.b07demosummer2024.AnnualCarbon.IntroToCalcFragment;
import com.example.b07demosummer2024.DailyActivity.HabitTrackerActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class HabitSearchActivity extends AppCompatActivity {
    public static DatabaseReference db = FirebaseDatabase.getInstance("https://b07project-725cc-default-rtdb.firebaseio.com/").getReference();
    public static FirebaseAuth auth = FirebaseAuth.getInstance();

    public static FirebaseUser user = auth.getCurrentUser();
    public static String uid = user.getUid();
    public static DatabaseReference userRef = db.child("users").child(uid);
    public static DatabaseReference userHabitsRef = userRef.child("Habits");

    private HabitAdapter habitadaptor;
    private RecyclerView recyclerView;
    private ArrayList<Habit> habits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_search);
        if (savedInstanceState != null) {
            Intent myIntent = new Intent(HabitSearchActivity.this, HabitTrackerActivity.class);
            HabitSearchActivity.this.startActivity(myIntent);
        }
        CheckBox transport = findViewById(R.id.transportFilter);
        CheckBox food = findViewById(R.id.foodFilter);
        CheckBox electricity = findViewById(R.id.electricityFilter);
        CheckBox small = findViewById(R.id.smallFilter);
        CheckBox medium = findViewById(R.id.mediumFilter);
        CheckBox large = findViewById(R.id.largeFilter);
        Button filter = findViewById(R.id.filterButton);

        recyclerView = findViewById(R.id.habitrecyclerview);
        SearchView searchview = findViewById(R.id.habitsearchView);
        searchview.clearFocus();

        habits = new ArrayList<>();

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (transport.isChecked() || food.isChecked() || electricity.isChecked() || small.isChecked() || medium.isChecked() || large.isChecked()) {
                    filterList(transport, food, electricity, small, medium, large);
                } else {
                    habitadaptor.setFilteredList(habits);
                    recyclerView.setAdapter(habitadaptor);
                }
            }
        });

        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchList(newText);
                return true;
            }
        });

        habits.add(new Habit("habit1","Transportation","large","Walked or biked to work:","I will walk or bike to work daily", R.drawable.transport_habit));
        habits.add(new Habit("habit2","Transportation","medium","Took the public transport to work:","I will take the public transport to work daily", R.drawable.transport_habit));
        habits.add(new Habit("habit3","Transportation","small","Carpooled to work:","I will carpooling with colleagues to work daily", R.drawable.transport_habit));
        habits.add(new Habit("habit4","Food","small","Did not eat chicken today:","I will stop eating chicken", R.drawable.food_habit));
        habits.add(new Habit("habit5","Food","medium","Did not eat pork today:","I will stop eating pork", R.drawable.food_habit));
        habits.add(new Habit("habit6","Food","small","Did not eat fish today:","I will stop eating fish", R.drawable.food_habit));
        habits.add(new Habit("habit7","Food","medium","Did not eat beef today:","I will stop eating beef", R.drawable.food_habit));
        habits.add(new Habit("habit8","Food","large","Did not waste any food today:","I will not waste/throw away any food", R.drawable.food_habit));
        habits.add(new Habit("habit9","Consumption","medium","Recycled today:","I will recycle something daily", R.drawable.consumption_habit));
        habits.add(new Habit("habit10","Consumption","large","Used eco-friendly products today?","I will use eco-friendly products daily", R.drawable.consumption_habit));


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        habitadaptor = new HabitAdapter(getApplicationContext(), habits);
        recyclerView.setAdapter(habitadaptor);

    }

    private void filterList(CheckBox transport, CheckBox food, CheckBox electricity, CheckBox small, CheckBox medium, CheckBox large) {
        ArrayList<String> type = new ArrayList<>();
        ArrayList<String> impact = new ArrayList<>();
        ArrayList<Habit> filtered = new ArrayList<>();

        if (transport.isChecked()){type.add("Transportation");}
        if (food.isChecked()){type.add("Food");}
        if (electricity.isChecked()){type.add("Consumption");}
        if (small.isChecked()){impact.add("small");}
        if (medium.isChecked()){impact.add("medium");}
        if (large.isChecked()){impact.add("large");}

        for (Habit habit : habits){
            if ((type.isEmpty() && impact.contains(habit.impact)) || (type.contains(habit.type) && impact.isEmpty()) || (type.contains(habit.type) && impact.contains(habit.impact))){
                filtered.add(habit);
            }
        }

        if (filtered.isEmpty()) {
            Toast.makeText(this, "Not Results Found", Toast.LENGTH_SHORT).show();
        } else {
            habitadaptor.setFilteredList(filtered);
            recyclerView.setAdapter(habitadaptor);
        }
    }

    private void searchList(String newText) {
        ArrayList<Habit> searched = new ArrayList<>();
        for (Habit habit : habits){
            if (habit.description.toLowerCase().contains(newText.toLowerCase()) || habit.impact.toLowerCase().contains(newText.toLowerCase()) || habit.type.toLowerCase().contains(newText.toLowerCase())){
                searched.add(habit);
            }
        }

        if (searched.isEmpty()) {
            Toast.makeText(this, "Not Results Found", Toast.LENGTH_SHORT).show();
        } else {
            habitadaptor.setFilteredList(searched);
            recyclerView.setAdapter(habitadaptor);
        }
    }
}