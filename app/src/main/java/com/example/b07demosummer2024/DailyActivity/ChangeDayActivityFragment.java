package com.example.b07demosummer2024.DailyActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.b07demosummer2024.R;
import com.example.b07demosummer2024.User;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChangeDayActivityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */;
public class ChangeDayActivityFragment extends Fragment {

    private static final String ARG_YEAR = "year";
    private static final String ARG_MONTH = "month";
    private static final String ARG_DAY = "day";

    private int year;
    private int month;
    private int day;
    TextView Date;
    EditText personaDist, publicTime, walkDist, numFlights, numClothes, numElectronics, numOther,
            otherType, beefMeals, porkMeals, chickenMeals, fishMeals, plantMeals;
    RadioButton shortHaul, longHaul;

    Switch miles;
    Button submitChanges;

    public ChangeDayActivityFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param year Selected year.
     * @param month Selected month.
     * @param day Selected day.
     * @return A new instance of fragment ChangeActivity.
     */
    public static ChangeDayActivityFragment newInstance(int year, int month, int day) {
        ChangeDayActivityFragment fragment = new ChangeDayActivityFragment();
        Bundle args = new Bundle();
        args.putInt("year", year);
        args.putInt("month", month);
        args.putInt("day", day);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            year = getArguments().getInt("year");
            month = getArguments().getInt("month");
            day = getArguments().getInt("day");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_day_activity, container, false);
        // TODO: Access database to find activities done on this day

        Date = (TextView) view.findViewById(R.id.textDate);
        String formatted_date = year + "-" + (month+1) + "-" + day;
        Date.setText(formatted_date);

        submitChanges = (Button) view.findViewById(R.id.submitChanges);

        miles = (Switch) view.findViewById(R.id.milesSwitch);
        personaDist = (EditText) view.findViewById(R.id.editPersonalDist);
        publicTime = (EditText) view.findViewById(R.id.editPublicTime);
        walkDist = (EditText) view.findViewById(R.id.editWalkDist);
        shortHaul = (RadioButton) view.findViewById(R.id.radioShortHaul);
        longHaul = (RadioButton) view.findViewById(R.id.radioLongHaul);
        numFlights = (EditText) view.findViewById(R.id.editNumFlights);
        beefMeals = (EditText) view.findViewById(R.id.editBeef);
        porkMeals = (EditText) view.findViewById(R.id.editPork);
        chickenMeals = (EditText) view.findViewById(R.id.editChicken);
        fishMeals = (EditText) view.findViewById(R.id.editFish);
        plantMeals = (EditText) view.findViewById(R.id.editPlant);
        numClothes = (EditText) view.findViewById(R.id.editNumClothes);
        numElectronics = (EditText) view.findViewById(R.id.editNumElectronics);
        otherType = (EditText) view.findViewById(R.id.editOtherType);
        numOther = (EditText) view.findViewById(R.id.editNumOther);

        submitChanges.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                DatabaseReference userRef = DailyTrackingActivity.userRef;

                Map<String, Map<String, Object>> updatedActivity = new HashMap<>();
                String haul_type;
                double FACTOR = 1;
                if (miles.isChecked()) {
                    FACTOR = 1.609344;
                }

                double personal_dist;
                double public_time;
                double walk_dist;
                int num_flights;
                int num_beef;
                int num_pork;
                int num_chicken;
                int num_fish;
                int num_plant;
                int num_clothes;
                int num_electronics;
                String other_type = "Other";
                int num_other;
                if (personaDist.getText().length() == 0) {
                    personal_dist = 0;
                } else {
                    personal_dist = FACTOR * Double.parseDouble(personaDist.getText().toString());
                }
                if (publicTime.getText().length() == 0) {
                    public_time= 0;
                } else {
                    public_time = Double.parseDouble(publicTime.getText().toString());
                }
                if (walkDist.getText().length() == 0) {
                    walk_dist = 0;
                } else {
                    walk_dist = FACTOR * Double.parseDouble(walkDist.getText().toString());
                }
                if (numFlights.getText().length() == 0) {
                    haul_type = "Short";
                    num_flights = 0;
                } else {
                    if (longHaul.isChecked()) {
                        haul_type = "Long";
                    } else {
                        haul_type = "Short";
                    }
                    num_flights = Integer.parseInt(numFlights.getText().toString());
                }
                if (beefMeals.getText().length() == 0) {
                    num_beef = 0;
                } else {
                    num_beef = Integer.parseInt(beefMeals.getText().toString());
                }
                if (porkMeals.getText().length() == 0) {
                    num_pork = 0;
                } else {
                    num_pork = Integer.parseInt(porkMeals.getText().toString());
                }
                if (chickenMeals.getText().length() == 0) {
                    num_chicken = 0;
                } else {
                    num_chicken = Integer.parseInt(chickenMeals.getText().toString());
                }
                if (fishMeals.getText().length() == 0) {
                    num_fish = 0;
                } else {
                    num_fish = Integer.parseInt(fishMeals.getText().toString());
                }
                if (plantMeals.getText().length() == 0) {
                    num_plant = 0;
                } else {
                    num_plant = Integer.parseInt(plantMeals.getText().toString());
                }
                if (numClothes.getText().length() == 0) {
                    num_clothes = 0;
                } else {
                    num_clothes = Integer.parseInt(numClothes.getText().toString());
                }
                if (numElectronics.getText().length() == 0) {
                    num_electronics = 0;
                } else {
                    num_electronics = Integer.parseInt(numElectronics.getText().toString());
                }
                if (numOther.getText().length() == 0) {
                    num_other = 0;
                } else {
                    num_other = Integer.parseInt(numOther.getText().toString());
                    other_type = otherType.getText().toString();
                    if (other_type.isEmpty()) {
                        // TODO: proper error handling, but I'm too lazy rn
                    }
                }

                // TODO: use factor to change miles to kilometers
                User.addDailyActivity(updatedActivity, formatted_date, Double.toString(personal_dist),
                        Double.toString(public_time), Double.toString(walk_dist), Integer.toString(num_flights), haul_type,
                        Integer.toString(num_beef), Integer.toString(num_pork), Integer.toString(num_chicken),
                        Integer.toString(num_fish), Integer.toString(num_plant), Integer.toString(num_clothes),
                        Integer.toString(num_electronics), other_type, Integer.toString(num_other));

                Map<String, Object> updatedDateActivity = updatedActivity.get(formatted_date);

                userRef.child(formatted_date).updateChildren(updatedDateActivity);

                getParentFragmentManager().popBackStackImmediate();
            }
        });

        return view;
    }
}