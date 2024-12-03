package com.example.b07demosummer2024;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.auth.AuthResult;
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

        habits.add(new Habit("1","Transportation","large","Did you run walk or bike to work?","Walking and biking to work daily.", true, R.drawable.transport_habit));
        habits.add(new Habit("2","Transportation","medium","Did you take the bus to work?","Taking the bus to work daily.", false, R.drawable.transport_habit));
        habits.add(new Habit("3","Transportation","small","Did you run carpool to work?","Carpooling with colleagues to work daily.", true, R.drawable.transport_habit));
        habits.add(new Habit("4","Food","small","Did you eat BLAH BLAH today?","Eating BLAH BLAH daily.", false,R.drawable.transport_habit));
        habits.add(new Habit("5","Food","medium","Did you eat BLAH today?","Eating BLAH daily.", false, R.drawable.transport_habit));
        habits.add(new Habit("6","Transportation","large","Did you run walk or bike to work?","Walking and biking to work daily.", false, R.drawable.transport_habit));
        habits.add(new Habit("7","Transportation","medium","Did you take the bus to work?","Taking the bus to work daily.", false, R.drawable.transport_habit));
        habits.add(new Habit("8","Transportation","small","Did you run carpool to work?","Carpooling with colleagues to work daily.", false, R.drawable.transport_habit));
        habits.add(new Habit("9","Food","small","Did you eat BLAH BLAH today?","Eating BLAH BLAH daily.", false,R.drawable.transport_habit));
        habits.add(new Habit("10","Food","medium","Did you eat BLAH today?","Eating BLAH daily.", false, R.drawable.transport_habit));


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
        if (electricity.isChecked()){type.add("Electricity");}
        if (small.isChecked()){impact.add("small");}
        if (medium.isChecked()){impact.add("medium");}
        if (large.isChecked()){impact.add("large");}

        for (Habit habit : habits){
            if (type.contains(habit.type) || impact.contains(habit.impact)){
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
            if (habit.description.toLowerCase().contains(newText.toLowerCase())){
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
