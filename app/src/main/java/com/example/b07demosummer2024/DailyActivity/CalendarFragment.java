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
import android.widget.CalendarView;
import android.widget.TextView;

import com.example.b07demosummer2024.R;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalendarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalendarFragment extends Fragment {

    private static final String ARG_PARAM1 = "year";
    private static final String ARG_PARAM2 = "month";
    private static final String ARG_PARAM3 = "day";
    CalendarView calendar;
    TextView date;
    Button seeActivities, editActivities, editEnergyBill;

    String Date;
    static int year;
    static int month;
    static int day;

    public CalendarFragment() {
        // Required empty public constructor
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
    public static CalendarFragment newInstance(int year, int month, int day) {
        CalendarFragment fragment = new CalendarFragment();
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
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        calendar = (CalendarView) view.findViewById(R.id.calendarView);
        date = (TextView) view.findViewById(R.id.textDate);
        seeActivities = (Button) view.findViewById(R.id.seeDayActivity);
        editActivities = (Button) view.findViewById(R.id.editDayActivity);
        editEnergyBill = (Button) view.findViewById(R.id.editEnergyBill);

        // Set Current Date
        setDate();

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int day) {
                CalendarFragment.year = year;
                CalendarFragment.month = month;
                CalendarFragment.day = day;
                setDate();
            }
        });

        seeActivities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = EcoTrackerHomeFragment.newInstance(year, month, day);
                loadFragment(fragment);
            }
        });

        editActivities.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Fragment fragment = ChangeDayActivityFragment.newInstance(year, month, day);
                loadFragment(fragment);
            }
        });

        editEnergyBill.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                // TODO: Change energy bill for user (need it added to database first)
            }
        });

        return view;
    }

    private void setDate() {
        Date = year + "-" + (month+1) + "-" + day;
        date.setText(Date);
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}