package com.example.b07demosummer2024;

import java.util.Calendar;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;

public class User {
    public String firstname;
    public String lastname;
    public String email;
    public String password;
    public Map<String, Map<String, Map<String,String>>> dailyActivity;
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
    }
    public static void addDailyActivity(Map<String, Map<String, Object>> dailyActivity,
                                        String date, String personalDist, String publicTime, String walkDist,
                                        String numFlights, String haul, String numBeef, String numPork,
                                        String numChicken, String numFish, String numPlant, String numClothes,
                                        String numElectronics, String otherType, String numOther) {
        Map<String, String> transportation = new HashMap<>();
        transportation.put("personal", personalDist);
        transportation.put("public", publicTime);
        transportation.put("walk", walkDist);
        transportation.put("flightNum", numFlights);
        transportation.put("flightType", haul);

        Map<String, String> food = new HashMap<>();
        food.put("beef", numBeef);
        food.put("pork", numPork);
        food.put("chicken", numChicken);
        food.put("fish", numFish);
        food.put("plant", numPlant);

        Map<String, String> consumption = new HashMap<>();
        consumption.put("clothes", numClothes);
        consumption.put("electronics", numElectronics);
        consumption.put("otherType", otherType);
        consumption.put("other", numOther);

        Map<String,Object> info = new HashMap<>();
        info.put("transportation", transportation);
        info.put("food", food);
        info.put("consumption", consumption);

        dailyActivity.put(date, info);
    }
}

