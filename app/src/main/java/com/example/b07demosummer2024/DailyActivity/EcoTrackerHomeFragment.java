package com.example.b07demosummer2024.DailyActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.b07demosummer2024.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Map;
import java.text.DecimalFormat;

public class EcoTrackerHomeFragment extends Fragment {
    private static final String ARG_PARAM1 = "year";
    private static final String ARG_PARAM2 = "month";
    private static final String ARG_PARAM3 = "day";
    private static final DecimalFormat df = new DecimalFormat("0.00");
    int year, month, day;
    Button openCalendar, editTodaysActivity;
    TextView date, personalDist, publicTime, walkDist, flight, beef, pork, chicken, fish, plant,
            numServings, numClothes, numElectronics, numOther;
    TextView personalCO2, publicCO2, walkCO2, flightCO2, beefCO2, porkCO2, chickenCO2, fishCO2,
            plantCO2, clothesCO2, electronicsCO2, otherCO2, totalCO2, houseCO2;
    Switch miles;
    String personal_dist_km, walk_dist_km, personal_dist_mi, walk_dist_mi;

    public EcoTrackerHomeFragment() {
        // set to current date if no date provided
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param year Current year.
     * @param month Current month.
     * @param day Current day.
     * @return A new instance of fragment CalendarFragment.
     */
    public static EcoTrackerHomeFragment newInstance(int year, int month, int day) {
        EcoTrackerHomeFragment fragment = new EcoTrackerHomeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, year);
        args.putInt(ARG_PARAM2, month);
        args.putInt(ARG_PARAM3, day);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            year = getArguments().getInt(ARG_PARAM1);
            month = getArguments().getInt(ARG_PARAM2);
            day = getArguments().getInt(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_eco_tracker_home, container, false);

        openCalendar = (Button) view.findViewById(R.id.openCalendar);
        editTodaysActivity = (Button) view.findViewById(R.id.editTodayActivity);

        date = (TextView) view.findViewById(R.id.textDate);
        personalDist = (TextView) view.findViewById(R.id.textPersonalDist);
        publicTime = (TextView) view.findViewById(R.id.textPublicDist);
        walkDist = (TextView) view.findViewById(R.id.textWalkDist);
        flight = (TextView) view.findViewById(R.id.textFlight);
        beef = (TextView) view.findViewById(R.id.textBeef);
        pork = (TextView) view.findViewById(R.id.textPork);
        chicken = (TextView) view.findViewById(R.id.textChicken);
        fish = (TextView) view.findViewById(R.id.textFish);
        plant = (TextView) view.findViewById(R.id.textPlant);
        numServings = (TextView) view.findViewById(R.id.textFish);
        numClothes = (TextView) view.findViewById(R.id.textNumClothes);
        numElectronics = (TextView) view.findViewById(R.id.textNumElectronics);
        numOther = (TextView) view.findViewById(R.id.textNumOther);

        personalCO2 = (TextView) view.findViewById(R.id.textPersonalCO2);
        publicCO2 = (TextView) view.findViewById(R.id.textPublicCO2);
        walkCO2 = (TextView) view.findViewById(R.id.textWalkCO2);
        flightCO2 = (TextView) view.findViewById(R.id.textFlightCO2);
        beefCO2 = (TextView) view.findViewById(R.id.textBeefCO2);
        porkCO2 = (TextView) view.findViewById(R.id.textPorkCO2);
        chickenCO2 = (TextView) view.findViewById(R.id.textChickenCO2);
        fishCO2 = (TextView) view.findViewById(R.id.textFishCO2);
        plantCO2 = (TextView) view.findViewById(R.id.textPlantCO2);
        clothesCO2 = (TextView) view.findViewById(R.id.textClothesCO2);
        electronicsCO2 = (TextView) view.findViewById(R.id.textElectronicsCO2);
        otherCO2 = (TextView) view.findViewById(R.id.textOtherCO2);
        totalCO2 = (TextView) view.findViewById(R.id.textTotalCO2);
        houseCO2 = (TextView) view.findViewById(R.id.textHousingCO2);

        miles = (Switch) view.findViewById(R.id.milesSwitch);

        String Date = year + "-" + (month+1) + "-" + day;
        date.setText(Date);

        DatabaseReference userRef = DailyTrackingActivity.userRef;

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    DataSnapshot todayData = snapshot.child(Date);
                    if (todayData.exists()) {
                        // TODO: Calculate and set carbon footprint data
                        Map<String, Map<String, String>> activityData = (Map<String, Map<String, String>>) todayData.getValue();
                        Map<String, String> transData = (Map<String, String>) activityData.get("transportation");
                        Map<String, String> foodData = (Map<String, String>) activityData.get("food");
                        Map<String, String> consumData = (Map<String, String>) activityData.get("consumption");

                        double FACTOR = 0.6213712;
                        Double personal_dist = Double.parseDouble(transData.get("personal"));
                        String public_time = transData.get("public");
                        Double walk_dist = Double.parseDouble(transData.get("walk"));
                        String num_flight = transData.get("flight_num");
                        String haul = transData.get("flight_type");
                        String num_beef = foodData.get("beef");
                        String num_pork = foodData.get("pork");
                        String num_chicken = foodData.get("chicken");
                        String num_fish = foodData.get("fish");
                        String num_plant = foodData.get("plant");
                        String num_clothes = consumData.get("clothes");
                        String num_electronics = consumData.get("electronics");
                        String other_type = consumData.get("other_type");
                        String num_other = consumData.get("other");

                        personal_dist_mi = df.format(personal_dist * FACTOR);
                        personal_dist_km = df.format(personal_dist);
                        walk_dist_km = df.format(walk_dist);
                        walk_dist_mi = df.format(walk_dist * FACTOR);

                        String personal_dist_str_km = "Personal: " + personal_dist_km + " km";
                        public_time = "Public: " + public_time + " h";
                        String walk_dist_str_km = "Walking/Cycling: " + walk_dist_km + " km";
                        String flights = haul + "-haul Flights: " + num_flight;
                        personalDist.setText(personal_dist_str_km);
                        publicTime.setText(public_time);
                        walkDist.setText(walk_dist_str_km);
                        flight.setText(flights);

                        String beef_str = "Beef: " + num_beef;
                        String pork_str = "Pork: " + num_pork;
                        String chicken_str = "Chicken: " + num_chicken;
                        String fish_str = "Fish: " + num_fish;
                        String plant_str = "Plant: " + num_plant;
                        beef.setText(beef_str);
                        pork.setText(pork_str);
                        chicken.setText(chicken_str);
                        fish.setText(fish_str);
                        plant.setText(plant_str);

                        String clothes_str = "Clothing: " + num_clothes;
                        String electronics_str = "Electronics: " + num_electronics;
                        String other_str = other_type.substring(0,1).toUpperCase() + other_type.substring(1).toLowerCase() + ": " + num_other;
                        numClothes.setText(clothes_str);
                        numElectronics.setText(electronics_str);
                        numOther.setText(other_str);
                    } else {
                        // send user to answer questions about daily activity
                        Fragment fragment = ChangeDayActivityFragment.newInstance(year, month, day);
                        loadFragment(fragment);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // send user to answer questions about daily activity
                Fragment fragment = ChangeDayActivityFragment.newInstance(year, month, day);
                loadFragment(fragment);
            }
        });

        openCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = CalendarFragment.newInstance(year, month, day);
                loadFragment(fragment);
            }
        });

        editTodaysActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = ChangeDayActivityFragment.newInstance(year, month, day);
                loadFragment(fragment);
            }
        });

        miles.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String pers = "Personal: ";
                String cycle = "Walking/Cycling: ";

                if (isChecked) {
                    // set to miles
                    pers +=  personal_dist_mi + " mi.";
                    cycle += walk_dist_mi + " mi.";
                } else {
                    // set to km
                    pers += personal_dist_km + " km";
                    cycle += walk_dist_km + " km";
                }
                personalDist.setText(pers);
                walkDist.setText(cycle);
            }
        });

        return view;
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}