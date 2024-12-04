package com.example.b07demosummer2024;

import static com.github.mikephil.charting.utils.ColorTemplate.rgb;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
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
import java.util.List;
import java.util.Map;


public class EmissionsGraphActivity extends AppCompatActivity {

    BarChart barChart;
    Map<String, Object> data;

    // Variables for bar data sets
    BarDataSet barDataSet1;

    float con, food, house, trans, tot;

    // ArrayList for storing entries
    ArrayList<BarEntry> barEntries;

    ArrayList<Integer> bars;

    // Creating a string array for displaying days
    String[] emission = new String[]{"Consumption", "Food", "House", "Transportation", "Total"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_emissions_graph);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.ecoGauge), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        barChart = findViewById(R.id.BarChart);
        // Creating a new bar data set
        barEntries = new ArrayList<>();
        bars = new ArrayList<>();
        DatabaseReference db = FirebaseDatabase.getInstance(
                "https://b07project-725cc-default-rtdb.firebaseio.com/").getReference();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = currentUser.getUid();
        DatabaseReference yearlyData = db.child("users").child(uid);
        Task<DataSnapshot> task = getDataFromFirebase(yearlyData);
        Task<List<Object>> taskComplete = Tasks.whenAllSuccess(task);
        taskComplete.addOnSuccessListener(aVoid -> {
            if (task.getResult() == null) {
                Toast.makeText(EmissionsGraphActivity.this, "There was an error retrieving the data", Toast.LENGTH_SHORT).show();
            }
            data = (Map<String, Object>)(task.getResult().child("Yearly Data").getValue());
            con = Float.parseFloat(data.get("consumpEmis").toString());
            food = Float.parseFloat(data.get("foodEmis").toString());
            house = Float.parseFloat(data.get("houseEmis").toString());
            trans = Float.parseFloat(data.get("transportEmis").toString());
            tot = Float.parseFloat(data.get("totalEmis").toString());
            barEntries.add(new BarEntry(1f, con));
            barEntries.add(new BarEntry(2f, food));
            barEntries.add(new BarEntry(3f, house));
            barEntries.add(new BarEntry(4f, trans));
            barEntries.add(new BarEntry(5f, tot));
            barDataSet1 = new BarDataSet(barEntries, "Emissions");
            int[] colors = {rgb("#009999"), rgb("#742df9"), rgb("#2df9f9"), rgb("#71f72e"), rgb("#f72e2e")};
            barDataSet1.setColors(colors);

            BarData data = new BarData(barDataSet1);
            barChart.setData(data);
            barChart.getDescription().setEnabled(false);
            XAxis xAxis = barChart.getXAxis();
            xAxis.setValueFormatter(new IndexAxisValueFormatter(emission));
            xAxis.setCenterAxisLabels(true);
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setGranularity(1f);
            xAxis.setGranularityEnabled(true);
            barChart.setDragEnabled(true);
            barChart.setVisibleXRangeMaximum(6);
            data.setBarWidth(0.75f);
            barChart.getXAxis().setAxisMinimum(0);
            barChart.animate();
            barChart.invalidate();
        });
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