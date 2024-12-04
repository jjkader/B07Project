package com.example.b07demosummer2024.EcoTrends;

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
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b07demosummer2024.NavigationActivity;
import com.example.b07demosummer2024.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class EmissionsTrendPartAFragment extends Fragment {
    private TextView tvX;
    private static final DecimalFormat df = new DecimalFormat("0.00");
    private Button backButton, dailyButton, weeklyButton, monthlyButton;
    private Map<String, Object> dailyActivity, weeklyActivity, monthlyActivity;
    private int year, month, day;
    private String date, startOfWeek, monthStr;


    public EmissionsTrendPartAFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Calendar c = Calendar.getInstance();
        this.year = c.get(Calendar.YEAR);
        this.month = c.get(Calendar.MONTH);
        this.day = c.get(Calendar.DAY_OF_MONTH);
        this.date = year + "-" + (month+1) + "-" + day;
        this.monthStr = year + "-" + (month+1);

        // get the first day of the current week (Sunday or Monday depending on Region)
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        int firstDayOfWeek = c.getFirstDayOfWeek();
        c.add(Calendar.DAY_OF_MONTH, -(dayOfWeek - firstDayOfWeek));
        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        int yearOfWeek = c.get(Calendar.YEAR);
        int monthOfWeek = c.get(Calendar.MONTH);
        this.startOfWeek = yearOfWeek + "-" + (monthOfWeek+1) + "-" + dayOfMonth;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_emissions_trend_part_a, container, false);
        tvX = view.findViewById(R.id.ACO2);
        backButton = view.findViewById(R.id.ABack);
        dailyButton = view.findViewById(R.id.ADaily);
        weeklyButton = view.findViewById(R.id.AWeekly);
        monthlyButton = view.findViewById(R.id.AMonthly);

        ImageButton logo = (ImageButton) view.findViewById(R.id.AplanetzeLogo);

        logo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://planetze.io/"));
                startActivity(browserIntent);
            }
        });


        // get all information
        Task<DataSnapshot> weeklyTask = getDataFromFirebase(userWeeklyActivityRef, startOfWeek);

        Task<DataSnapshot> monthlyTask = getDataFromFirebase(userMonthlyActivityRef, monthStr);

        Task<DataSnapshot> dailyTask = getDataFromFirebase(userDailyActivityRef, date);

        Task<List<Object>> allTasks = Tasks.whenAllSuccess(weeklyTask, monthlyTask,
                dailyTask);

        dailyActivity = null;
        weeklyActivity = null;
        monthlyActivity = null;

        allTasks.addOnSuccessListener(aVoid -> {
            if (dailyTask.getResult() == null ||
                    weeklyTask.getResult() == null || monthlyTask.getResult() == null) {
                Toast.makeText(getContext(), "No Data", Toast.LENGTH_LONG).show();
                getParentFragmentManager().popBackStack();
            }

            weeklyActivity = (Map<String, Object>) weeklyTask.getResult().getValue();
            monthlyActivity = (Map<String, Object>) monthlyTask.getResult().getValue();
            dailyActivity = (Map<String, Object>) dailyTask.getResult().getValue();

            setToDaily();
        }).addOnFailureListener(e -> {
            Toast.makeText(getContext(), "Error Fetching Data", Toast.LENGTH_LONG).show();
            getParentFragmentManager().popBackStack();
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().popBackStack();
            }
        });

        dailyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setToDaily();
            }
        });

        weeklyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setToWeekly();
            }
        });

        monthlyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setToMonthly();
            }
        });
        return view;
    }

    private Task<DataSnapshot> getDataFromFirebase(DatabaseReference ref, String date) {
        final TaskCompletionSource<DataSnapshot> taskCompletionSource = new TaskCompletionSource<>();
        ref.child(date).addListenerForSingleValueEvent(new ValueEventListener() {
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

    private void setToDaily() {
        if (dailyActivity == null) {
            Toast.makeText(getContext(), "No data available for daily trends", Toast.LENGTH_SHORT).show();
            return;
        }
        String text = "You've emitted: "
                + (df.format(Double.parseDouble(dailyActivity.get("totalCO2").toString())))
                + " kg CO2e today!";
        tvX.setText(text);
    }

    private void setToWeekly() {
        if (weeklyActivity == null) {
            Toast.makeText(getContext(), "No data available for weekly trends", Toast.LENGTH_SHORT).show();
            return;
        }
        double total = Double.parseDouble(weeklyActivity.get("totalCO2").toString());
        String totalStr = df.format(total);
        String text = "You've emitted: " + totalStr + " kg CO2e this week!";
        tvX.setText(text);
    }

    private void setToMonthly() {
        if (monthlyActivity == null) {
            Toast.makeText(getContext(), "No data available for monthly trends", Toast.LENGTH_SHORT).show();
            return;
        }
        String text = "You've emitted: "
                + (df.format(Double.parseDouble(monthlyActivity.get("totalCO2").toString())))
                + " kg CO2e this month!";
        tvX.setText(text);
    }
}