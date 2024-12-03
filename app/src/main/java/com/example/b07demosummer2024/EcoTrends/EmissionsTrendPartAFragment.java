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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EmissionsTrendFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EmissionsTrendPartAFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private LineChart chart;
    private SeekBar seekBarX, seekBarY;
    private TextView tvX;
    private static final DecimalFormat df = new DecimalFormat("0.00");
    private Button backButton, dailyButton, weeklyButton, monthlyButton;
    private Map<String, Object> dailyActivity, weeklyActivity, monthlyActivity;
    private int year, month, day;
    private String date, startOfWeek, monthStr;
    private double[] dailyCO2 = new double[30];
    private double[] weeklyCO2 = new double[52];
    private double[] monthlyCO2 = new double[24];
    private List<Entry> dailyVals = new ArrayList<>();
    private List<Entry> weeklyVals = new ArrayList<>();
    private List<Entry> monthlyVals = new ArrayList<>();
    private LineDataSet dailySet, weeklySet, monthlySet;
    private LineData dailyData, weeklyData, monthlyData;


    public EmissionsTrendPartAFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EmissionsTrendFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EmissionsTrendPartAFragment newInstance(String param1, String param2) {
        EmissionsTrendPartAFragment fragment = new EmissionsTrendPartAFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

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
        int monthOfWeek = c.get(Calendar.MONTH) + 1;
        this.startOfWeek = yearOfWeek + "-" + monthOfWeek + "-" + dayOfMonth;
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
        Task<DataSnapshot> weeklyTask = getDataFromFirebase(userWeeklyActivityRef);

        Task<DataSnapshot> monthlyTask = getDataFromFirebase(userMonthlyActivityRef);

        Task<DataSnapshot> dailyTask = getDataFromFirebase(userDailyActivityRef);

        Task<List<Object>> allTasks = Tasks.whenAllSuccess(weeklyTask, monthlyTask,
                dailyTask);

        dailyActivity = null;
        weeklyActivity = null;
        monthlyActivity = null;

        allTasks.addOnSuccessListener(aVoid -> {
            if (weeklyTask.getResult() != null) {
                DataSnapshot weekData = weeklyTask.getResult().child(startOfWeek);
                if (weekData.exists()) {
                    weeklyActivity = (Map<String, Object>) weekData.getValue();
                }
            }
            if (monthlyTask.getResult() != null) {
                DataSnapshot monthData = monthlyTask.getResult().child(monthStr);
                if (monthData.exists()) {
                    monthlyActivity = (Map<String, Object>) monthData.getValue();
                }
            }
            if (dailyTask.getResult() != null) {
                DataSnapshot dailyData = dailyTask.getResult().child(date);
                if (dailyData.exists()) {
                    dailyActivity = (Map<String, Object>) dailyData.getValue();
                }
            }

            /*
                    // fetch past 30 days, 52 weeks, 24 months
                    Calendar c = Calendar.getInstance();
                    for (int i = 29; i >= 0; i--) {
                        c.set(year, month, day);
                        c.add(Calendar.DAY_OF_MONTH, -i);
                        int tmpYear = c.get(Calendar.YEAR);
                        int tmpMonth = c.get(Calendar.MONTH) + 1;
                        int tmpDay = c.get(Calendar.DAY_OF_MONTH);
                        String tmpDate = tmpYear + "-" + tmpMonth + "-" + tmpDay;
                        Map<String, Object> tmpDayActivity = dailyActivity.get(tmpDate);
                        float xVal = (new Date(tmpYear - 1900, tmpMonth - 1, tmpDay)).getTime();
                        float yVal;
                        if (tmpDayActivity == null) {
                            yVal = 0;
                        } else {
                            yVal = Float.parseFloat(tmpDayActivity.get("totalCO2").toString());
                        }
                        dailyVals.add(new Entry(xVal, Float.parseFloat(df.format(yVal))));
                    }

                    int firstDayOfWeek = c.getFirstDayOfWeek();
                    for (int i = 51; i >= 0; i--) {
                        // set to first day of week, then increment by 7
                        c.set(year, month, day);
                        int tmpDayOfWeek = c.get(Calendar.DAY_OF_WEEK);
                        c.add(Calendar.DAY_OF_MONTH, -(tmpDayOfWeek - firstDayOfWeek));
                        c.add(Calendar.DAY_OF_MONTH, -7 * i);
                        int tmpYear = c.get(Calendar.YEAR);
                        int tmpMonth = c.get(Calendar.MONTH) + 1;
                        int tmpDay = c.get(Calendar.DAY_OF_MONTH);
                        String tmpDate = tmpYear + "-" + tmpMonth + "-" + tmpDay;
                        Map<String, Object> tmpWeekActivity = weeklyActivity.get(tmpDate);
                        float xVal = (new Date(tmpYear - 1900, tmpMonth - 1, tmpDay)).getTime();
                        float yVal;
                        if (tmpWeekActivity == null) {
                            yVal = 0;
                        } else {
                            yVal = Float.parseFloat(tmpWeekActivity.get("totalCO2").toString());
                        }
                        weeklyVals.add(new Entry(xVal, Float.parseFloat(df.format(yVal))));
                    }

                    for (int i = 23; i >= 0; i--) {
                        c.set(year, month, day);
                        c.add(Calendar.MONTH, -i);
                        int tmpYear = c.get(Calendar.YEAR);
                        int tmpMonth = c.get(Calendar.MONTH) + 1;
                        String tmpDate = tmpYear + "-" + tmpMonth;
                        Map<String, Object> tmpMonthActivity = monthlyActivity.get(tmpDate);
                        float xVal = (new Date(tmpYear - 1900, tmpMonth - 1, 1)).getTime();
                        float yVal;
                        if (tmpMonthActivity == null) {
                            yVal = 0;
                        } else {
                            yVal = Float.parseFloat(tmpMonthActivity.get("totalCO2").toString());
                        }
                        monthlyVals.add(new Entry(xVal, Float.parseFloat(df.format(yVal))));
                    }

             */
            setToDaily();
                }).addOnFailureListener(e -> {
            Toast.makeText(getContext(), "Error Fetching Data", Toast.LENGTH_LONG).show();
            getParentFragmentManager().popBackStack();
        });


        //MonthlyActivity.getCO2 with parsing double ex.

            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getParentFragmentManager().popBackStack();
                    // currently main fragment, so push back to navigation
                    Intent myIntent = new Intent(getContext(), NavigationActivity.class);
                    getContext().startActivity(myIntent);
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
        String text = "You've emitted: "
                + (df.format(Double.parseDouble(weeklyActivity.get("totalCO2").toString())))
                + " kg CO2e this week!";
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