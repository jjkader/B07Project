package com.example.b07demosummer2024;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class HabitSearchActivity extends AppCompatActivity {

    private HabitAdapter habitadaptor;
    private RecyclerView recyclerView;
    private ArrayList<Habit> habits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_search);

        recyclerView = findViewById(R.id.habitrecyclerview);
        SearchView searchview = findViewById(R.id.habitsearchView);
        searchview.clearFocus();

        habits = new ArrayList<>();

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

        habits.add(new Habit("1","Transportation","large","Did you run walk or bike to work?","Walking and biking to work daily.", false, R.drawable.transport_habit));
        habits.add(new Habit("2","Transportation","medium","Did you take the bus to work?","Taking the bus to work daily.", false, R.drawable.transport_habit));
        habits.add(new Habit("3","Transportation","small","Did you run carpool to work?","Carpooling with colleagues to work daily.", false, R.drawable.transport_habit));
        habits.add(new Habit("4","Food","small","Did you eat BLAH BLAH today?","Eating BLAH BLAH daily.", false,R.drawable.transport_habit));
        habits.add(new Habit("5","Food","medium","Did you eat BLAH today?","Eating BLAH daily.", false, R.drawable.transport_habit));


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        habitadaptor = new HabitAdapter(getApplicationContext(), habits);
        recyclerView.setAdapter(habitadaptor);

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
            Toast.makeText(this, "Here are your results", Toast.LENGTH_SHORT).show();
            habitadaptor.setFilteredList(searched);
            recyclerView.setAdapter(habitadaptor);
        }
    }
}
