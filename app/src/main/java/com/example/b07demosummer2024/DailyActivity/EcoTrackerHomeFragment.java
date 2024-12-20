package com.example.b07demosummer2024.DailyActivity;

import static com.example.b07demosummer2024.DailyActivity.DailyTrackingActivity.userDailyActivityRef;
import static com.example.b07demosummer2024.DailyActivity.DailyTrackingActivity.userMonthlyActivityRef;
import static com.example.b07demosummer2024.DailyActivity.DailyTrackingActivity.userWeeklyActivityRef;

import android.content.Intent;
import android.net.Uri;
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
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b07demosummer2024.AnnualCarbon.AnnualCarbonActivity;
import com.example.b07demosummer2024.HabitSearchActivity;
import com.example.b07demosummer2024.R;
import com.example.b07demosummer2024.User;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.text.DecimalFormat;

public class EcoTrackerHomeFragment extends Fragment {
    private static final String ARG_PARAM1 = "year";
    private static final String ARG_PARAM2 = "month";
    private static final String ARG_PARAM3 = "day";
    private static final DecimalFormat df = new DecimalFormat("0.00");
    int year, month, day;
    private String date, startOfWeek, monthStr;
    TextView dateText, textPersonalDist, textPublicTime, textWalkDist, textFlight, textBeef, textPork, textChicken, textFish,
            textPlant, textNumServings, textNumClothes, textNumElectronics, textNumOther;
    Button openCalendar, editTodaysActivity, ecoGoals;
    TextView textPersonalCO2, textPublicCO2, textWalkCO2, textFlightCO2, textBeefCO2, textPorkCO2, textChickenCO2, textFishCO2,
            textPlantCO2, textClothesCO2, textElectronicsCO2, textOtherCO2, textTotalCO2, textHouseCO2;
    Switch miles;
    Map<String, Long> habits;
    String personalDistKm, walkDistKm, personalDistMi, walkDistMi, Date;

    public EcoTrackerHomeFragment() {
        // set to current date if no date provided
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        date = year + "-" + (month+1) + "-" + day;
        monthStr = year + "-" + (month+1);
        // get the first day of the current week (Sunday or Monday depending on Region)
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        int firstDayOfWeek = c.getFirstDayOfWeek();
        c.add(Calendar.DAY_OF_MONTH, -(dayOfWeek - firstDayOfWeek));
        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        int yearOfWeek = c.get(Calendar.YEAR);
        int monthOfWeek = c.get(Calendar.MONTH) + 1;

        startOfWeek = yearOfWeek + "-" + monthOfWeek + "-" + dayOfMonth;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param year Current year.
     * @param month Current month.
     * @param day Current day.
     * @return A new instance of fragment EcoTrackerHomeFragment.
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
            date = year + "-" + (month+1) + "-" + day;
            monthStr = year + "-" + (month+1);
            // get the first day of the current week (Sunday or Monday depending on Region)
            Calendar c = Calendar.getInstance();
            c.set(year, month, day);
            int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
            int firstDayOfWeek = c.getFirstDayOfWeek();
            c.add(Calendar.DAY_OF_MONTH, -(dayOfWeek - firstDayOfWeek));
            int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
            int yearOfWeek = c.get(Calendar.YEAR);
            int monthOfWeek = c.get(Calendar.MONTH) + 1;

            startOfWeek = yearOfWeek + "-" + monthOfWeek + "-" + dayOfMonth;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_eco_tracker_home, container, false);

        openCalendar = (Button) view.findViewById(R.id.openCalendar);
        editTodaysActivity = (Button) view.findViewById(R.id.editTodayActivity);
        ecoGoals = (Button) view.findViewById(R.id.ecoGoals);

        dateText = (TextView) view.findViewById(R.id.textDate);
        textPersonalDist = (TextView) view.findViewById(R.id.textPersonalDist);
        textPublicTime = (TextView) view.findViewById(R.id.textPublicDist);
        textWalkDist = (TextView) view.findViewById(R.id.textWalkDist);
        textFlight = (TextView) view.findViewById(R.id.textFlight);
        textBeef = (TextView) view.findViewById(R.id.textBeef);
        textPork = (TextView) view.findViewById(R.id.textPork);
        textChicken = (TextView) view.findViewById(R.id.textChicken);
        textFish = (TextView) view.findViewById(R.id.textFish);
        textPlant = (TextView) view.findViewById(R.id.textPlant);
        textNumServings = (TextView) view.findViewById(R.id.textFish);
        textNumClothes = (TextView) view.findViewById(R.id.textNumClothes);
        textNumElectronics = (TextView) view.findViewById(R.id.textNumElectronics);
        textNumOther = (TextView) view.findViewById(R.id.textNumOther);

        textPersonalCO2 = (TextView) view.findViewById(R.id.textPersonalCO2);
        textPublicCO2 = (TextView) view.findViewById(R.id.textPublicCO2);
        textWalkCO2 = (TextView) view.findViewById(R.id.textWalkCO2);
        textFlightCO2 = (TextView) view.findViewById(R.id.textFlightCO2);
        textBeefCO2 = (TextView) view.findViewById(R.id.textBeefCO2);
        textPorkCO2 = (TextView) view.findViewById(R.id.textPorkCO2);
        textChickenCO2 = (TextView) view.findViewById(R.id.textChickenCO2);
        textFishCO2 = (TextView) view.findViewById(R.id.textFishCO2);
        textPlantCO2 = (TextView) view.findViewById(R.id.textPlantCO2);
        textClothesCO2 = (TextView) view.findViewById(R.id.textClothesCO2);
        textElectronicsCO2 = (TextView) view.findViewById(R.id.textElectronicsCO2);
        textOtherCO2 = (TextView) view.findViewById(R.id.textOtherCO2);
        textTotalCO2 = (TextView) view.findViewById(R.id.textTotalCO2);
        textHouseCO2 = (TextView) view.findViewById(R.id.textHousingCO2);

        miles = (Switch) view.findViewById(R.id.milesSwitch);

        ImageButton logo = (ImageButton) view.findViewById(R.id.planetzeLogo);

        logo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://planetze.io/"));
                startActivity(browserIntent);
            }
        });

        Date = year + "-" + (month+1) + "-" + day;
        dateText.setText(Date);

        DatabaseReference userRef = DailyTrackingActivity.userRef;
        DatabaseReference userDailyActivityRef = DailyTrackingActivity.userDailyActivityRef;

        setTextFields(userDailyActivityRef, userRef);

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
        ecoGoals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference db = FirebaseDatabase.getInstance(
                        "https://b07project-725cc-default-rtdb.firebaseio.com/").getReference();
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                String uid = currentUser.getUid();
                DatabaseReference habitData = db.child("users").child(uid);
                Task<DataSnapshot> task = getDataFromFirebase(habitData);
                Task<List<Object>> taskComplete = Tasks.whenAllSuccess(task);
                taskComplete.addOnSuccessListener(aVoid -> {
                    if (task.getResult() == null) {
                        Toast.makeText(getContext(), "There was an error retrieving the data", Toast.LENGTH_SHORT).show();
                        getParentFragmentManager().popBackStack();
                    }
                    habits = (Map<String, Long>)task.getResult().child("Habits").getValue();
                    Intent myIntent;
                    if (habits.containsValue((long)1)){
                        myIntent = new Intent(getContext(), HabitTrackerActivity.class);
                    }
                    else{
                        myIntent = new Intent(getContext(), HabitSearchActivity.class);
                    }
                    getContext().startActivity(myIntent);
                });
            }
        });

        miles.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String pers = "Personal: ";
                String cycle = "Walking/Cycling: ";

                if (isChecked) {
                    // set to miles
                    pers +=  personalDistMi + " mi.";
                    cycle += walkDistMi + " mi.";
                } else {
                    // set to km
                    pers += personalDistKm + " km";
                    cycle += walkDistKm + " km";
                }
                textPersonalDist.setText(pers);
                textWalkDist.setText(cycle);
            }
        });

        return view;
    }

    private void setTextFields(DatabaseReference userDailyActivityRef, DatabaseReference userRef) {
        userDailyActivityRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    DataSnapshot todayData = snapshot.child(Date);
                    if (todayData.exists()) {
                        Map<String, Map<String, String>> activityData = (Map<String, Map<String, String>>) todayData.getValue();
                        Map<String, String> transData = (Map<String, String>) activityData.get("transportation");
                        Map<String, String> foodData = (Map<String, String>) activityData.get("food");
                        Map<String, String> consumData = (Map<String, String>) activityData.get("consumption");

                        setText(userRef, transData, foodData, consumData);
                    } else {
                        // send user to answer questions about daily activity
                        Fragment fragment = ChangeDayActivityFragment.newInstance(year, month, day);
                        loadFragment(fragment);
                    }
                } else {
                    // send user to answer questions about daily activity
                    Fragment fragment = ChangeDayActivityFragment.newInstance(year, month, day);
                    loadFragment(fragment);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // send user to answer questions about daily activity
                Fragment fragment = ChangeDayActivityFragment.newInstance(year, month, day);
                loadFragment(fragment);
            }
        });
    }

    private void setText(DatabaseReference userRef, Map<String, String> transData,
                           Map<String, String> foodData, Map<String, String> consumData) {
        double FACTOR = 0.6213712;
        double personalDist = Double.parseDouble(transData.get("personal"));
        String publicTime = transData.get("public");
        double publicTimeDouble = Double.parseDouble(publicTime);
        double walkDist = Double.parseDouble(transData.get("walk"));
        String numFlight = transData.get("flightNum");
        int numFlightInt = Integer.parseInt(numFlight);
        String haul = transData.get("flightType");
        String numBeef = foodData.get("beef");
        int numBeefInt = Integer.parseInt(numBeef);
        String numPork = foodData.get("pork");
        int numPorkInt = Integer.parseInt(numPork);
        String numChicken = foodData.get("chicken");
        int numChickenInt = Integer.parseInt(numChicken);
        String numFish = foodData.get("fish");
        int numFishInt = Integer.parseInt(numFish);
        String numPlant = foodData.get("plant");
        int numPlantInt = Integer.parseInt(numPlant);
        String numClothes = consumData.get("clothes");
        int numClothesInt = Integer.parseInt(numClothes);
        String numElectronics = consumData.get("electronics");
        int numElectronicsInt = Integer.parseInt(numElectronics);
        String otherType = consumData.get("otherType");
        String numOther = consumData.get("other");
        int numOtherInt = Integer.parseInt(numOther);

        personalDistMi = df.format(personalDist * FACTOR);
        personalDistKm = df.format(personalDist);
        walkDistKm = df.format(walkDist);
        walkDistMi = df.format(walkDist * FACTOR);

        String personalDistStrKm = "Personal: " + personalDistKm + " km";
        publicTime = "Public: " + publicTime + " h";
        String walkDistStrKm = "Walking/Cycling: " + walkDistKm + " km";
        String flights = haul + "-haul Flights: " + numFlight;
        textPersonalDist.setText(personalDistStrKm);
        textPublicTime.setText(publicTime);
        textWalkDist.setText(walkDistStrKm);
        textFlight.setText(flights);

        String beef_str = "Beef: " + numBeef;
        String pork_str = "Pork: " + numPork;
        String chicken_str = "Chicken: " + numChicken;
        String fish_str = "Fish: " + numFish;
        String plant_str = "Plant: " + numPlant;
        textBeef.setText(beef_str);
        textPork.setText(pork_str);
        textChicken.setText(chicken_str);
        textFish.setText(fish_str);
        textPlant.setText(plant_str);

        String clothesStr = "Clothing: " + numClothes;
        String electronicsStr = "Electronics: " + numElectronics;
        String otherStr = otherType.substring(0,1).toUpperCase() + otherType.substring(1)
                .toLowerCase() + ": " + numOther;
        textNumClothes.setText(clothesStr);
        textNumElectronics.setText(electronicsStr);
        textNumOther.setText(otherStr);

        setCO2Fields(userRef, personalDist, publicTimeDouble, numFlightInt, haul, numBeefInt,
                numPorkInt, numChickenInt, numFishInt, numClothesInt, numElectronicsInt, numOtherInt);
    }

    private void setCO2Fields(DatabaseReference userRef, double personalDist, double publicTime,
                              int numFlight, String haul, int numBeef, int numPork, int numChicken,
                              int numFish, int numClothes, int numElectronics, int numOther) {
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    DataSnapshot data = snapshot.child("Yearly Data");
                    Map<String, Object> yearlyData = (Map<String, Object>) data.getValue();
                    String carType = (String) yearlyData.get("carType");
                    String foodWaste = (String) yearlyData.get("foodWaste");
                    // TODO: get all yearly data, then take monthly energy bill data to find value
                    double yearlyHouseEmissions = Double
                            .parseDouble((String) yearlyData.get("houseEmis"));
                    String ecoClothes = (String) yearlyData.get("buyEcoClothes");
                    String unit = " kg CO2e";

                    double personalCO2 = calculatePersonalCO2(personalDist, carType);
                    String personalCO2Str = df.format(personalCO2) + unit;
                    textPersonalCO2.setText(personalCO2Str);

                    double publicCO2 = calculatePublicCO2(publicTime);
                    String publicCO2Str = df.format(publicCO2) + unit;
                    textPublicCO2.setText(publicCO2Str);

                    double walkCO2 = calculateWalkCO2();
                    String walkCO2Str = df.format(walkCO2) + unit;
                    textWalkCO2.setText(walkCO2Str);

                    double flightCO2 = calculateFlightCO2(numFlight, haul);
                    String flightCO2Str = df.format(flightCO2) + unit;
                    textFlightCO2.setText(flightCO2Str);

                    double beefCO2 = calculateFoodCO2(numBeef, "beef", foodWaste);
                    double porkCO2 = calculateFoodCO2(numPork, "pork", foodWaste);
                    double chickenCO2 = calculateFoodCO2(numChicken, "chicken", foodWaste);
                    double fishCO2 = calculateFoodCO2(numFish, "fish", foodWaste);

                    String beefCO2Str = df.format(beefCO2) + unit;
                    String porkCO2Str = df.format(porkCO2) + unit;
                    String chickenCO2Str = df.format(chickenCO2) + unit;
                    String fishCO2Str = df.format(fishCO2) + unit;
                    textBeefCO2.setText(beefCO2Str);
                    textPorkCO2.setText(porkCO2Str);
                    textChickenCO2.setText(chickenCO2Str);
                    textFishCO2.setText(fishCO2Str);
                    String plantCO2Str = "0" + unit;
                    textPlantCO2.setText(plantCO2Str);

                    double clothesCO2 = calculateClothesCO2(numClothes, ecoClothes);
                    String clothesCO2Str = df.format(clothesCO2) + unit;
                    textClothesCO2.setText(clothesCO2Str);

                    double electronicsCO2 = calculateElectronicsCO2(numElectronics);
                    String electronicsCO2Str = df.format(electronicsCO2) + unit;
                    textElectronicsCO2.setText(electronicsCO2Str);

                    double otherCO2 = calculateOtherCO2(numOther);
                    String otherCO2Str = df.format(otherCO2) + unit;
                    textOtherCO2.setText(otherCO2Str);

                    double dailyHouseEmissions = calculateHousingCO2(yearlyHouseEmissions);
                    String dailyHouseEmissionsStr = df.format(dailyHouseEmissions) + unit;
                    textHouseCO2.setText(dailyHouseEmissionsStr);

                    double totalCO2 = personalCO2 + publicCO2 + flightCO2 + beefCO2 + porkCO2
                            + chickenCO2 + fishCO2 + clothesCO2 + electronicsCO2 + otherCO2
                            + dailyHouseEmissions;
                    String totalCO2Str = df.format(totalCO2) + unit;
                    textTotalCO2.setText(totalCO2Str);

                    User.updateCO2(snapshot, userDailyActivityRef, userWeeklyActivityRef,
                            userMonthlyActivityRef, Date, startOfWeek, monthStr, totalCO2);
                } else {
                    // FATAL ERROR: User must have data
                    throw new RuntimeException();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // send user to answer questions about daily activity
                Fragment fragment = ChangeDayActivityFragment.newInstance(year, month, day);
                loadFragment(fragment);
            }
        });
    }

    private static double calculatePersonalCO2(double personalDist, String carType) {
        double personalCO2;
        if (carType.equals("Gasoline")) {
            personalCO2 = 0.24;
        } else if (carType.equals("Diesel")) {
            personalCO2 = 0.27;
        } else if (carType.equals("Hybrid")) {
            personalCO2 = 0.16;
        } else if (carType.equals("Electric")) {
            personalCO2 = 0.05;
        } else {
            personalCO2 = 0;
        }
        return personalCO2 * personalDist;
    }

    private static double calculatePublicCO2(double publicTime) {
        // from the yearly calculations, estimate roughly 6kg/h on public transit
        double publicCO2 = 6;
        return publicCO2 * publicTime;
    }

    private static double calculateWalkCO2() {
        return 0;
    }

    private static double calculateFlightCO2(int numFlight, String haul) {
        // directly from yearly calculations
        double flightCO2 = 0;
        if (haul.equals("Short")) {
            if (numFlight == 0) {
                flightCO2 = 0;
            } else if (numFlight < 3) {
                flightCO2 = 225;
            } else if (numFlight < 6) {
                flightCO2 = 600;
            } else if (numFlight < 11) {
                flightCO2 = 1200;
            } else {
                flightCO2 = 1800;
            }
        } else {
            if (numFlight == 0) {
                flightCO2 = 0;
            } else if (numFlight < 3) {
                flightCO2 = 825;
            } else if (numFlight < 6) {
                flightCO2 = 2200;
            } else if (numFlight < 11) {
                flightCO2 = 4400;
            } else {
                flightCO2 = 6600;
            }
        }
        return flightCO2;
    }

    private static double calculateFoodCO2(int numFood, String foodType, String foodWaste) {
        // Daily / 365 in yearly calculations per meal
        // remove value dependent on foodWaste
        // estimate 365*3 = 1095 meals a year
        double wasteFactor = 0;
        if (foodWaste.equals("Frequently")) {
            wasteFactor = 140.4 / 1095;
        } else if (foodWaste.equals("Occasionally")) {
            wasteFactor = 70.2 / 1095;
        } else if (foodWaste.equals("Rarely")) {
            wasteFactor = 23.4 / 1095;
        }

        double foodFactor;
        if (foodType.equals("beef")) {
            foodFactor = 2500;
        } else if (foodType.equals("pork")) {
            foodFactor = 1450;
        } else if (foodType.equals("chicken")) {
            foodFactor = 950;
        } else if (foodType.equals("fish")) {
            foodFactor = 800;
        } else {
            foodFactor = 0;
        }

        return (numFood * foodFactor / (double) 365) + (numFood * wasteFactor);
    }

    private static double calculateClothesCO2(int numClothes, String ecoClothes) {
        // estimation from yearly, 360kg/yr monthly roughly 7kg/piece
        double clothesCO2 = 1;
        if (ecoClothes.equals("Regularly")) {
            clothesCO2 = 0.5;
        } else if (ecoClothes.equals("Occasionally")) {
            clothesCO2 = 0.7;
        }
        return clothesCO2 * numClothes * 7;
    }

    private static double calculateElectronicsCO2(int numElectronics) {
        // 300kg per item
        return 300 * numElectronics;
    }

    private static double calculateOtherCO2(int numOther) {
        // estimate 500kg per item assuming they're large purchases
        return 500 * numOther;
    }

    private static double calculateHousingCO2(double yearlyHouseEmissions) {
        // TODO: get all housing data and access csv data
        return yearlyHouseEmissions / 365;
    }

    public static double calculateTotalCO2(Map<String, Object> dailyActivity, Map<String, Object> yearlyData) {
        String carType = (String) yearlyData.get("carType");
        String foodWaste = (String) yearlyData.get("foodWaste");
        // TODO: get all yearly data, then take monthly energy bill data to find value
        double yearlyHouseEmissions = Double
                .parseDouble((String) yearlyData.get("houseEmis"));
        String ecoClothes = (String) yearlyData.get("buyEcoClothes");

        Map<String, Object> transData = (Map<String, Object>) dailyActivity.get("transportation");
        Map<String, Object> foodData = (Map<String, Object>) dailyActivity.get("food");
        Map<String, Object> consumpData = (Map<String, Object>) dailyActivity.get("consumption");

        double personalDist = Double.parseDouble((String) transData.get("personal"));
        double publicTime = Double.parseDouble((String) transData.get("public"));
        int numFlight = Integer.parseInt((String) transData.get("flightNum"));
        String haul = (String) transData.get("flightType");
        int numBeef = Integer.parseInt((String) foodData.get("beef"));
        int numPork = Integer.parseInt((String) foodData.get("pork"));
        int numChicken = Integer.parseInt((String) foodData.get("chicken"));
        int numFish = Integer.parseInt((String) foodData.get("fish"));
        int numClothes = Integer.parseInt((String) consumpData.get("clothes"));
        int numElectronics = Integer.parseInt((String) consumpData.get("electronics"));
        int numOther = Integer.parseInt((String) consumpData.get("other"));

        double personalCO2 = calculatePersonalCO2(personalDist, carType);
        double publicCO2 = calculatePublicCO2(publicTime);
        double flightCO2 = calculateFlightCO2(numFlight, haul);
        double beefCO2 = calculateFoodCO2(numBeef, "beef", foodWaste);
        double porkCO2 = calculateFoodCO2(numPork, "pork", foodWaste);
        double chickenCO2 = calculateFoodCO2(numChicken, "chicken", foodWaste);
        double fishCO2 = calculateFoodCO2(numFish, "fish", foodWaste);
        double clothesCO2 = calculateClothesCO2(numClothes, ecoClothes);
        double electronicsCO2 = calculateElectronicsCO2(numElectronics);
        double otherCO2 = calculateOtherCO2(numOther);
        double housingCO2 = calculateHousingCO2(yearlyHouseEmissions);
        return personalCO2 + publicCO2 + flightCO2 + beefCO2 + porkCO2 + chickenCO2 + fishCO2 +
                clothesCO2 + electronicsCO2 + otherCO2 + housingCO2;
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


    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
