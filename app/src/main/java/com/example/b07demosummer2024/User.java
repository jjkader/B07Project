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
    public Map<String, Map<String, Map<String,String>>> daily_activity;
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
        daily_activity = new HashMap<>();
    }
    public static void addDailyActivity(Map<String, Map<String, Object>> daily_activity,
                                        String date, String personal_dist, String public_time, String walk_dist,
                                        String num_flights, String haul, String num_beef, String num_pork,
                                        String num_chicken, String num_fish, String num_plant, String num_clothes,
                                        String num_electronics, String other_type, String num_other) {
        Map<String, String> transportation = new HashMap<>();
        transportation.put("personal", personal_dist);
        transportation.put("public", public_time);
        transportation.put("walk", walk_dist);
        transportation.put("flight_num", num_flights);
        transportation.put("flight_type", haul);

        Map<String, String> food = new HashMap<>();
        food.put("beef", num_beef);
        food.put("pork", num_pork);
        food.put("chicken", num_chicken);
        food.put("fish", num_fish);
        food.put("plant", num_plant);

        Map<String, String> consumption = new HashMap<>();
        consumption.put("clothes", num_clothes);
        consumption.put("electronics", num_electronics);
        consumption.put("other_type", other_type);
        consumption.put("other", num_other);

        Map<String,Object> info = new HashMap<>();
        info.put("transportation", transportation);
        info.put("food", food);
        info.put("consumption", consumption);

        daily_activity.put(date, info);
    }
}

