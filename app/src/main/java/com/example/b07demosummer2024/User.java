package com.example.b07demosummer2024;

import static com.example.b07demosummer2024.DailyActivity.DailyTrackingActivity.userDailyActivityRef;
import static com.example.b07demosummer2024.DailyActivity.DailyTrackingActivity.userMonthlyActivityRef;
import static com.example.b07demosummer2024.DailyActivity.DailyTrackingActivity.userWeeklyActivityRef;

import android.sax.StartElementListener;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.b07demosummer2024.DailyActivity.ChangeDayActivityFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class User {
    public String firstname;
    public String lastname;
    public String email;
    public String password;
    public Map<String, Map<String, Map<String,String>>> dailyActivity;
    public Map<String, Map<String, Map<String,String>>> weeklyActivity;
    public Map<String, Map<String, Map<String,String>>> monthlyActivity;
    public User(){

    }
    public User(String fn, String ln, String e, String p){
        this.firstname = fn;
        this.lastname = ln;
        this.email = e;
        this.password = p;
        // format of daily_activity
        /*
        "date": [
            {"type": trans_type, "dist": dist, "haul": haul},
            {"type": food_type, "servings": servings},
            {"clothes": num_clothes, "electronics": num_electronics, "other": num_other}
        ];
        */
        dailyActivity = new HashMap<>();
        weeklyActivity = new HashMap<>();
        monthlyActivity = new HashMap<>();
    }
    public static void addActivity(Map<String, Map<String, Object>> dailyActivity,
                                   Map<String, Map<String, Object>> weeklyActivity,
                                   Map<String, Map<String, Object>> monthlyActivity,
                                   Map<String, Map<String, Object>> prevDailyActivity,
                                   String date, String personalDist, String publicTime, String walkDist,
                                   String numFlights, String haul, String numBeef, String numPork,
                                   String numChicken, String numFish, String numPlant, String numClothes,
                                   String numElectronics, String otherType, String numOther) {
        Map<String, Object> transportation = new HashMap<>();
        transportation.put("personal", personalDist);
        transportation.put("public", publicTime);
        transportation.put("walk", walkDist);
        transportation.put("flightNum", numFlights);
        transportation.put("flightType", haul);

        Map<String, Object> food = new HashMap<>();
        food.put("beef", numBeef);
        food.put("pork", numPork);
        food.put("chicken", numChicken);
        food.put("fish", numFish);
        food.put("plant", numPlant);

        Map<String, Object> consumption = new HashMap<>();
        consumption.put("clothes", numClothes);
        consumption.put("electronics", numElectronics);
        consumption.put("otherType", otherType);
        consumption.put("other", numOther);

        Map<String,Object> info = new HashMap<>();
        info.put("transportation", transportation);
        info.put("food", food);
        info.put("consumption", consumption);

        dailyActivity.put(date, info);

        // get previous daily data to subtract from weekly/monthly (since it's overwritten)
        double prevPersonal = 0;
        double prevPublic = 0;
        double prevWalk = 0;
        int prevNumFlights = 0;
        int prevNumBeef = 0;
        int prevNumPork = 0;
        int prevNumChicken = 0;
        int prevNumFish = 0;
        int prevNumPlant = 0;
        int prevNumClothes = 0;
        int prevNumElectronics = 0;
        int prevNumOther = 0;
        if (prevDailyActivity != null) {
            Map<String, Object> prevTrans = prevDailyActivity.get("transportation");
            Map<String, Object> prevFood = prevDailyActivity.get("food");
            Map<String, Object> prevConsump = prevDailyActivity.get("consumption");

            prevPersonal = Double.parseDouble((String) prevTrans.get("personal"));
            prevPublic = Double.parseDouble((String) prevTrans.get("public"));
            prevWalk = Double.parseDouble((String) prevTrans.get("walk"));
            prevNumFlights = Integer.parseInt((String) prevTrans.get("flightNum"));

            prevNumBeef = Integer.parseInt((String) prevFood.get("beef"));
            prevNumPork = Integer.parseInt((String) prevFood.get("pork"));
            prevNumChicken = Integer.parseInt((String) prevFood.get("chicken"));
            prevNumFish = Integer.parseInt((String) prevFood.get("fish"));
            prevNumPlant = Integer.parseInt((String) prevFood.get("plant"));

            prevNumClothes = Integer.parseInt((String) prevConsump.get("clothes"));
            prevNumElectronics = Integer.parseInt((String) prevConsump.get("electronics"));
            prevNumOther = Integer.parseInt((String) prevConsump.get("other"));
        }

        if (weeklyActivity.get("transportation") != null) {
            Map<String, Object> weekTrans = weeklyActivity.get("transportation");
            Map<String, Object> weekFood = weeklyActivity.get("food");
            Map<String, Object> weekConsump = weeklyActivity.get("consumption");

            addToData(weekTrans, "flightNum", Integer.parseInt(numFlights) - prevNumFlights);
            addToData(weekTrans, "personal", Double.parseDouble(personalDist) - prevPersonal);
            addToData(weekTrans, "public", Double.parseDouble(publicTime) - prevPublic);
            addToData(weekTrans, "walk", Double.parseDouble(walkDist) - prevWalk);
            weekTrans.put("flightType", haul);

            addToData(weekFood, "beef", Integer.parseInt(numBeef) - prevNumBeef);
            addToData(weekFood, "pork", Integer.parseInt(numPork) - prevNumPork);
            addToData(weekFood, "chicken", Integer.parseInt(numChicken) - prevNumChicken);
            addToData(weekFood, "fish", Integer.parseInt(numFish) - prevNumFish);
            addToData(weekFood, "plant", Integer.parseInt(numPlant) - prevNumPlant);

            addToData(weekConsump, "clothes", Integer.parseInt(numClothes) - prevNumClothes);
            addToData(weekConsump, "electronics", Integer.parseInt(numElectronics) - prevNumElectronics);
            addToData(weekConsump, "other", Integer.parseInt(numOther) - prevNumOther);
            weekConsump.put("otherType", otherType);

            putActivityFields(weeklyActivity, weekTrans, weekFood, weekConsump);
        } else {
            putActivityFields(weeklyActivity, transportation, food, consumption);
        }

        if (monthlyActivity.get("transportation") != null) {
            Map<String, Object> monthTrans = (Map<String, Object>) monthlyActivity.get("transportation");
            Map<String, Object> monthFood = (Map<String, Object>) monthlyActivity.get("food");
            Map<String, Object> monthConsump = (Map<String, Object>) monthlyActivity.get("consumption");

            addToData(monthTrans, "flightNum", Integer.parseInt(numFlights) - prevNumFlights);
            addToData(monthTrans, "personal", Double.parseDouble(personalDist) - prevPersonal);
            addToData(monthTrans, "public", Double.parseDouble(publicTime) - prevPublic);
            addToData(monthTrans, "walk", Double.parseDouble(walkDist) - prevWalk);
            monthTrans.put("flightType", haul);

            addToData(monthFood, "beef", Integer.parseInt(numBeef) - prevNumBeef);
            addToData(monthFood, "pork", Integer.parseInt(numPork) - prevNumPork);
            addToData(monthFood, "chicken", Integer.parseInt(numChicken) - prevNumChicken);
            addToData(monthFood, "fish", Integer.parseInt(numFish) - prevNumFish);
            addToData(monthFood, "plant", Integer.parseInt(numPlant) - prevNumPlant);

            addToData(monthConsump, "clothes", Integer.parseInt(numClothes) - prevNumClothes);
            addToData(monthConsump, "electronics", Integer.parseInt(numElectronics) - prevNumElectronics);
            addToData(monthConsump, "other", Integer.parseInt(numOther) - prevNumOther);
            monthConsump.put("otherType", otherType);

            putActivityFields(monthlyActivity, monthTrans, monthFood, monthConsump);
        } else {
            putActivityFields(monthlyActivity, transportation, food, consumption);
        }
    }

    private static void putActivityFields(Map<String, Map<String, Object>> data,
                                          Map<String, Object> transportation,
                                          Map<String, Object> food,
                                          Map<String, Object> consumption) {
        data.put("transportation", transportation);
        data.put("food", food);
        data.put("consumption", consumption);
    }

    private static void addToData(Map<String, Object> data, String key, int addedValue) {
        addedValue += Integer.parseInt((String) data.get(key));
        data.put(key, Integer.toString(addedValue));
    }

    private static void addToData(Map<String, Object> data, String key, double addedValue) {
        addedValue += Double.parseDouble((String) data.get(key));
        data.put(key, Double.toString(addedValue));
    }

    /**
     * Updates the CO2 values in the database.
     * @param data DataSnapshot to user's uid.
     * @param dailyRef Reference to user's dailyActivity.
     * @param weeklyRef Reference to user's weeklyActivity.
     * @param monthlyRef Reference to user's monthlyActivity.
     * @param date Date to update (YYYY/MM/DD).
     * @param startOfWeek Date of first day of the week.
     * @param monthStr Date in (YYYY/MM) format.
     * @param totalDayCO2 New total CO2.
     */
    public static void updateCO2(@NonNull DataSnapshot data, DatabaseReference dailyRef,
                                 DatabaseReference weeklyRef, DatabaseReference monthlyRef,
                                 String date, String startOfWeek, String monthStr, double totalDayCO2) {
        Map<String, Object> CO2Total = new HashMap<>();
        Double prevCO2Total = (Double) data.child("dailyActivity").child(date).child("totalCO2").getValue();
        if (prevCO2Total == null) {
            prevCO2Total = 0.0;
        }
        CO2Total.put("totalCO2", Double.toString(totalDayCO2));
        dailyRef.child(date).updateChildren(CO2Total);

        Map<String, Object> weekCO2Total = new HashMap<>();
        Double weekCO2 = (Double) data.child("weeklyActivity").child(startOfWeek).child("totalCO2").getValue();
        if (weekCO2 == null) {
            weekCO2 = 0.0;
        }
        weekCO2Total.put("totalCO2", Double.toString(totalDayCO2+weekCO2-prevCO2Total));
        weeklyRef.child(startOfWeek).updateChildren(weekCO2Total);

        Map<String, Object> monthCO2Total = new HashMap<>();
        Double monthCO2 = (Double) data.child("monthlyActivity").child(monthStr).child("totalCO2").getValue();
        if (monthCO2 == null) {
            monthCO2 = 0.0;
        }
        monthCO2Total.put("totalCO2", Double.toString(totalDayCO2+monthCO2-prevCO2Total));
        monthlyRef.child(monthStr).updateChildren(monthCO2Total);
    }

    /**
     * Updates the CO2 values in the database.
     * @param dailyRef Reference to user's dailyActivity.
     * @param weeklyRef Reference to user's weeklyActivity.
     * @param monthlyRef Reference to user's monthlyActivity.
     * @param date Date to update (YYYY/MM/DD).
     * @param startOfWeek Date of first day of the week.
     * @param monthStr Date in (YYYY/MM) format.
     * @param totalDayCO2 New total CO2.
     * @param prevCO2Total Old total CO2.
     * @param weekCO2 Current week's CO2 total.
     * @param monthCO2 Current month's CO2 total.
     */
    public static void updateCO2(@NonNull DatabaseReference dailyRef, @NonNull DatabaseReference weeklyRef,
                                 @NonNull DatabaseReference monthlyRef, String date, String startOfWeek,
                                 String monthStr, double totalDayCO2, double prevCO2Total,
                                 double weekCO2, double monthCO2) {
        Map<String, Object> CO2Total = new HashMap<>();

        CO2Total.put("totalCO2", Double.toString(totalDayCO2));
        dailyRef.child(date).updateChildren(CO2Total);

        Map<String, Object> weekCO2Total = new HashMap<>();
        weekCO2Total.put("totalCO2", Double.toString(totalDayCO2+weekCO2-prevCO2Total));
        weeklyRef.child(startOfWeek).updateChildren(weekCO2Total);

        Map<String, Object> monthCO2Total = new HashMap<>();
        monthCO2Total.put("totalCO2", Double.toString(totalDayCO2+monthCO2-prevCO2Total));
        monthlyRef.child(monthStr).updateChildren(monthCO2Total);
    }
}


