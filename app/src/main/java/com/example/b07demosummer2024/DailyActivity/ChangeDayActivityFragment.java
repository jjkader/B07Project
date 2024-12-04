package com.example.b07demosummer2024.DailyActivity;

import static com.example.b07demosummer2024.DailyActivity.DailyTrackingActivity.userDailyActivityRef;
import static com.example.b07demosummer2024.DailyActivity.DailyTrackingActivity.userMonthlyActivityRef;
import static com.example.b07demosummer2024.DailyActivity.DailyTrackingActivity.userRef;
import static com.example.b07demosummer2024.DailyActivity.DailyTrackingActivity.userWeeklyActivityRef;
import static com.example.b07demosummer2024.DailyActivity.DailyTrackingActivity.userYearlyActivityRef;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b07demosummer2024.R;
import com.example.b07demosummer2024.RegisterActivity;
import com.example.b07demosummer2024.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
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
    private Map<String, Map<String, Object>> prevDailyActivity, weeklyActivity, monthlyActivity;
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

        Date = (TextView) view.findViewById(R.id.textDate);
        String formattedDate = year + "-" + (month+1) + "-" + day;
        Date.setText(formattedDate);

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

        ImageButton logo = (ImageButton) view.findViewById(R.id.planetzeLogo);

        logo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://planetze.io/"));
                startActivity(browserIntent);
            }
        });

        submitChanges.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
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
                String other_type;
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
                    other_type = "Other";
                    num_other = 0;
                } else {
                    num_other = Integer.parseInt(numOther.getText().toString());
                    other_type = otherType.getText().toString();
                    if (other_type.isEmpty()) {
                        // TODO: proper error handling, but I'm too lazy rn
                    }
                }

                // get the first day of the current week (Sunday or Monday depending on Region)
                Calendar c = Calendar.getInstance();
                c.set(year, month, day);
                int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
                int firstDayOfWeek = c.getFirstDayOfWeek();
                c.add(Calendar.DAY_OF_MONTH, -(dayOfWeek - firstDayOfWeek));
                int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
                int yearOfWeek = c.get(Calendar.YEAR);
                int monthOfWeek = c.get(Calendar.MONTH) + 1;

                String startOfWeek = yearOfWeek + "-" + monthOfWeek + "-" + dayOfMonth;

                weeklyActivity = new HashMap<>();

                String monthStr = year + "-" + (month+1);

                monthlyActivity = new HashMap<>();

                prevDailyActivity = null;

                Task<DataSnapshot> weeklyTask = getDataFromFirebase(userWeeklyActivityRef);

                Task<DataSnapshot> monthlyTask = getDataFromFirebase(userMonthlyActivityRef);

                Task<DataSnapshot> dailyTask = getDataFromFirebase(userDailyActivityRef);

                Task<DataSnapshot> yearlyTask = getDataFromFirebase(userYearlyActivityRef);

                Task<List<Object>> allTasks = Tasks.whenAllSuccess(weeklyTask, monthlyTask,
                        dailyTask, yearlyTask);

                allTasks.addOnSuccessListener(aVoid -> {
                    if (weeklyTask.getResult() != null) {
                        DataSnapshot weekData = weeklyTask.getResult().child(startOfWeek);
                        if (weekData.exists()) {
                            weeklyActivity = (Map<String, Map<String, Object>>) weekData.getValue();
                        }
                    }
                    if (monthlyTask.getResult() != null) {
                        DataSnapshot monthData = monthlyTask.getResult().child(monthStr);
                        if (monthData.exists()) {
                            monthlyActivity = (Map<String, Map<String, Object>>) monthData.getValue();
                        }
                    }
                    if (dailyTask.getResult() != null) {
                        DataSnapshot dailyData = dailyTask.getResult().child(formattedDate);
                        if (dailyData.exists()) {
                            prevDailyActivity = (Map<String, Map<String, Object>>) dailyData.getValue();
                        }
                    }
                    Map<String, Object> yearlyData = null;
                    if (yearlyTask.getResult() != null) {
                        yearlyData = (Map<String, Object>) yearlyTask.getResult().getValue();
                    } else {
                        Toast.makeText(getContext(),
                                "Error No Yearly Data (Do Annual Questionnaire)", Toast.LENGTH_LONG).show();
                        getParentFragmentManager().popBackStack();
                    }

                    double prevTotalCO2 = 0;
                    if (prevDailyActivity != null) {
                        Map<String, Object> prevTemp = new HashMap<>();
                        prevTemp.put("totalCO2", prevDailyActivity.get("totalCO2"));
                        prevTotalCO2 = Double.parseDouble((String) prevTemp.get("totalCO2"));
                    }

                    User.addActivity(updatedActivity, weeklyActivity, monthlyActivity, prevDailyActivity,
                            formattedDate, Double.toString(personal_dist),
                            Double.toString(public_time), Double.toString(walk_dist),
                            Integer.toString(num_flights), haul_type, Integer.toString(num_beef),
                            Integer.toString(num_pork), Integer.toString(num_chicken),
                            Integer.toString(num_fish), Integer.toString(num_plant), Integer.toString(num_clothes),
                            Integer.toString(num_electronics), other_type, Integer.toString(num_other));

                    Map<String, Object> updatedDateActivity = updatedActivity.get(formattedDate);
                    Map<String, Object> updatedWeeklyActivity = new HashMap<>();
                    updatedWeeklyActivity.put("transportation", weeklyActivity.get("transportation"));
                    updatedWeeklyActivity.put("food", weeklyActivity.get("food"));
                    updatedWeeklyActivity.put("consumption", weeklyActivity.get("consumption"));
                    updatedWeeklyActivity.put("totalCO2", weeklyActivity.get("totalCO2"));
                    Map<String, Object> updatedMonthlyActivity = new HashMap<>();
                    updatedMonthlyActivity.put("transportation", monthlyActivity.get("transportation"));
                    updatedMonthlyActivity.put("food", monthlyActivity.get("food"));
                    updatedMonthlyActivity.put("consumption", monthlyActivity.get("consumption"));
                    updatedMonthlyActivity.put("totalCO2", monthlyActivity.get("totalCO2"));

                    userDailyActivityRef.child(formattedDate).updateChildren(updatedDateActivity);
                    userWeeklyActivityRef.child(startOfWeek).updateChildren(updatedWeeklyActivity);
                    userMonthlyActivityRef.child(monthStr).updateChildren(updatedMonthlyActivity);

                    double totalCO2 = EcoTrackerHomeFragment.calculateTotalCO2(updatedDateActivity, yearlyData);
                    double monthCO2 = 0;
                    if (updatedMonthlyActivity.get("totalCO2") != null) {
                        monthCO2 = Double.parseDouble((String) updatedMonthlyActivity.get("totalCO2"));
                    }
                    double weekCO2 = 0;
                    if (updatedWeeklyActivity.get("totalCO2") != null) {
                        weekCO2 = Double.parseDouble((String) updatedWeeklyActivity.get("totalCO2"));
                    }
                    User.updateCO2(userDailyActivityRef, userWeeklyActivityRef, userMonthlyActivityRef,
                            formattedDate, startOfWeek, monthStr, totalCO2, prevTotalCO2, weekCO2, monthCO2);

                    getParentFragmentManager().popBackStack();
                }).addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Error Updating Data", Toast.LENGTH_LONG).show();
                    getParentFragmentManager().popBackStack();
                });
            }
        });

        return view;
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
}