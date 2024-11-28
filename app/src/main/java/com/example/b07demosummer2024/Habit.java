package com.example.b07demosummer2024;

public class Habit {

    public String id;
    public String type;
    public String impact;
    public String reminder;
    public String description;
    public Boolean active;
    public int image;

    public Habit() {}

    public Habit(String id, String type, String impact, String reminder, String description, Boolean active, int image) {
        this.id = id;
        this.impact = impact;
        this.type = type;
        this.reminder = reminder;
        this.description = description;
        this.active = active;
        this.image = image;
    }
}
