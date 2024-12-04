package com.example.b07demosummer2024.EcoTrends;

import static com.example.b07demosummer2024.DailyActivity.DailyTrackingActivity.userRef;

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
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmissionsTrendFragment extends Fragment {
    private static final int DAILY_DATA_POINTS = 30;
    private static final int WEEKLY_DATA_POINTS = 26;
    private static final int MONTHLY_DATA_POINTS = 12;
    private LineChart chart;
    private TextView tvX;
    private static final DecimalFormat df = new DecimalFormat("0.00");
    private Button backButton, dailyButton, weeklyButton, monthlyButton;
    private Map<String, Map<String, Object>> dailyActivity, weeklyActivity, monthlyActivity;
    private int year, month, day;
    private List<Entry> dailyVals = new ArrayList<>();
    private List<Entry> weeklyVals = new ArrayList<>();
    private List<Entry> monthlyVals = new ArrayList<>();
    private LineDataSet dailySet, weeklySet, monthlySet;
    private LineData dailyData, weeklyData, monthlyData;


    public EmissionsTrendFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Calendar c = Calendar.getInstance();
        this.year = c.get(Calendar.YEAR);
        this.month = c.get(Calendar.MONTH);
        this.day = c.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_emissions_trend, container, false);
        tvX = view.findViewById(R.id.textX);
        String unitExplanation = "in kg of CO2";
        tvX.setText(unitExplanation);
        chart = view.findViewById(R.id.lineChart);

        backButton = view.findViewById(R.id.buttonBack);
        dailyButton = view.findViewById(R.id.buttonDaily);
        weeklyButton = view.findViewById(R.id.buttonWeekly);
        monthlyButton = view.findViewById(R.id.buttonMonthly);

        ImageButton logo = (ImageButton) view.findViewById(R.id.planetzeLogo);

        logo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://planetze.io/"));
                startActivity(browserIntent);
            }
        });

        // get all information
        Task<DataSnapshot> task = getDataFromFirebase(userRef);

        Task<List<Object>> taskComplete = Tasks.whenAllSuccess(task);

        dailyActivity = null;
        weeklyActivity = null;
        monthlyActivity = null;

        taskComplete.addOnSuccessListener(aVoid -> {
            if (task.getResult() == null) {
                Toast.makeText(getContext(), "Error Fetching Data", Toast.LENGTH_LONG).show();
                getParentFragmentManager().popBackStack();
            } else if (task.getResult().child("dailyActivity") == null) {
                Toast.makeText(getContext(), "Error Fetching Data", Toast.LENGTH_LONG).show();
                getParentFragmentManager().popBackStack();
            } else if (task.getResult().child("weeklyActivity") == null) {
                Toast.makeText(getContext(), "Error Fetching Data", Toast.LENGTH_LONG).show();
                getParentFragmentManager().popBackStack();
            } else if (task.getResult().child("monthlyActivity") == null) {
                Toast.makeText(getContext(), "Error Fetching Data", Toast.LENGTH_LONG).show();
                getParentFragmentManager().popBackStack();
            }
            dailyActivity = (Map<String, Map<String, Object>>)
                    task.getResult().child("dailyActivity").getValue();
            weeklyActivity = (Map<String, Map<String, Object>>)
                    task.getResult().child("weeklyActivity").getValue();
            monthlyActivity = (Map<String, Map<String, Object>>)
                    task.getResult().child("monthlyActivity").getValue();

            // fetch past 30 days, 52 weeks, 24 months
            Calendar c = Calendar.getInstance();
            for (int i = DAILY_DATA_POINTS-1; i >= 0; i--) {
                c.set(year, month, day);
                c.add(Calendar.DAY_OF_MONTH, -i);
                int tmpYear = c.get(Calendar.YEAR);
                int tmpMonth = c.get(Calendar.MONTH) + 1;
                int tmpDay = c.get(Calendar.DAY_OF_MONTH);
                String tmpDate = tmpYear + "-" + tmpMonth + "-" + tmpDay;
                Map<String, Object> tmpDayActivity = dailyActivity.get(tmpDate);
                float xVal = (new Date(tmpYear - 1900, tmpMonth-1, tmpDay)).getTime();
                float yVal;
                if (tmpDayActivity == null) {
                    yVal = 0;
                } else {
                    yVal = Float.parseFloat(tmpDayActivity.get("totalCO2").toString());
                }
                dailyVals.add(new Entry(xVal, Float.parseFloat(df.format(yVal))));
            }

            int firstDayOfWeek = c.getFirstDayOfWeek();
            for (int i = WEEKLY_DATA_POINTS-1; i >= 0; i--) {
                // set to first day of week, then increment by 7
                c.set(year, month, day);
                int tmpDayOfWeek = c.get(Calendar.DAY_OF_WEEK);
                c.add(Calendar.DAY_OF_MONTH, -(tmpDayOfWeek - firstDayOfWeek));
                c.add(Calendar.DAY_OF_MONTH, -7*i);
                int tmpYear = c.get(Calendar.YEAR);
                int tmpMonth = c.get(Calendar.MONTH) + 1;
                int tmpDay = c.get(Calendar.DAY_OF_MONTH);
                String tmpDate = tmpYear + "-" + tmpMonth + "-" + tmpDay;
                Map<String, Object> tmpWeekActivity = weeklyActivity.get(tmpDate);
                float xVal = (new Date(tmpYear - 1900, tmpMonth-1, tmpDay)).getTime();
                float yVal;
                if (tmpWeekActivity == null) {
                    yVal = 0;
                } else {
                    yVal = Float.parseFloat(tmpWeekActivity.get("totalCO2").toString());
                }
                weeklyVals.add(new Entry(xVal, Float.parseFloat(df.format(yVal))));
            }

            for (int i = MONTHLY_DATA_POINTS-1; i >= 0; i--) {
                c.set(year, month, day);
                c.add(Calendar.MONTH, -i);
                int tmpYear = c.get(Calendar.YEAR);
                int tmpMonth = c.get(Calendar.MONTH) + 1;
                String tmpDate = tmpYear + "-" + tmpMonth;
                Map<String, Object> tmpMonthActivity = monthlyActivity.get(tmpDate);
                float xVal = (new Date(tmpYear - 1900, tmpMonth-1, 1)).getTime();
                float yVal;
                if (tmpMonthActivity == null) {
                    yVal = 0;
                } else {
                    yVal = Float.parseFloat(tmpMonthActivity.get("totalCO2").toString());
                }
                monthlyVals.add(new Entry(xVal, Float.parseFloat(df.format(yVal))));
            }

            dailySet = new LineDataSet(dailyVals, "Daily Emissions");
            weeklySet = new LineDataSet(weeklyVals, "Weekly Emissions");
            monthlySet = new LineDataSet(monthlyVals, "Monthly Emissions");

            dailyData = new LineData(dailySet);
            weeklyData = new LineData(weeklySet);
            monthlyData = new LineData(monthlySet);

            chart.getDescription().setText("Emissions Trend Graph");
            // enable touch gestures
            chart.setTouchEnabled(true);

            chart.setDragDecelerationFrictionCoef(0.9f);

            // enable scaling and dragging
            chart.setDragEnabled(true);
            chart.setScaleEnabled(true);
            chart.setDrawGridBackground(false);
            chart.setHighlightPerDragEnabled(true);
            chart.setExtraTopOffset(10); // extra padding for labels

            // default to daily
            setToDaily();
        }).addOnFailureListener(e -> {
            Toast.makeText(getContext(), "Error Fetching Data", Toast.LENGTH_LONG).show();
            getParentFragmentManager().popBackStack();
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getParentFragmentManager().popBackStack();
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

    private void setToDaily() {
        if (dailyVals.isEmpty()) {
            Toast.makeText(getContext(), "No data available for daily trends", Toast.LENGTH_SHORT).show();
            return;
        }
        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new ValueFormatter() {
            private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            @Override
            public String getFormattedValue(float value) {
                return dateFormat.format(new Date((long) value));
            }
        });

        xAxis.setLabelRotationAngle(-45);
        xAxis.setGranularity(86400000f);
        chart.setData(dailyData);
        chart.invalidate();
    }

    private void setToWeekly() {
        if (weeklyVals.isEmpty()) {
            Toast.makeText(getContext(), "No data available for weekly trends", Toast.LENGTH_SHORT).show();
            return;
        }
        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new ValueFormatter() {
            private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM", Locale.getDefault());
            @Override
            public String getFormattedValue(float value) {
                return dateFormat.format(new Date((long) value));
            }
        });
        xAxis.setLabelRotationAngle(-45);
        xAxis.setGranularity(2.628e+9f); // Approx. one month in milliseconds (30.44 days)
        // equivalent x to monthly because there are many weeks to display
        chart.setData(weeklyData);
        chart.invalidate();
    }

    private void setToMonthly() {
        if (monthlyVals.isEmpty()) {
            Toast.makeText(getContext(), "No data available for monthly trends", Toast.LENGTH_SHORT).show();
            return;
        }
        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new ValueFormatter() {
            private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM", Locale.getDefault());
            @Override
            public String getFormattedValue(float value) {
                return dateFormat.format(new Date((long) value));
            }
        });
        xAxis.setLabelRotationAngle(-45);
        xAxis.setGranularity(2.628e+9f); // Approx. one month in milliseconds (30.44 days)
        chart.setData(monthlyData);
        chart.invalidate();
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