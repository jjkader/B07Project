package com.example.b07demosummer2024.EcoTrends;

import static java.lang.Double.parseDouble;
import static java.lang.Long.parseLong;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b07demosummer2024.AnnualCarbon.AnnualCarbonInformation;
import com.example.b07demosummer2024.R;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;


public class RegionComparisonFragment extends Fragment {
    HashMap<String, Double> countriesMap = new HashMap<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_region_comparison, container, false);

        TextView showComp = view.findViewById(R.id.showComparison);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        String uid = user.getUid();
        FirebaseDatabase fdb = FirebaseDatabase.getInstance(
                "https://b07project-725cc-default-rtdb.firebaseio.com/");
        DatabaseReference reference = fdb.getReference().child("users").child(uid).child("Yearly Data");
        populateCountryMap(getContext(), countriesMap);
        Task<DataSnapshot> task = getDataFromFirebase(reference);
        Task<List<Object>> taskComplete = Tasks.whenAllSuccess(task);

        taskComplete.addOnSuccessListener(aVoid -> {
            if (task.getResult() == null) {
                Toast.makeText(getContext(), "Error Fetching Data", Toast.LENGTH_LONG).show();
                getParentFragmentManager().popBackStack();
            }
            String country = (String) task.getResult()
                    .child("country").getValue();
            double totalConsump = parseDouble((String) task.getResult()
                    .child("totalEmis").getValue()) / 1000;
            double countriesCosump = countriesMap.get(country);
            long percent = (long) (totalConsump / countriesCosump * 100) - 100;

            percent = Math.round(percent);
            if (percent < 100) {
                long newPercent = (long) (countriesCosump / totalConsump * 100);
                newPercent = Math.round(newPercent) - 100;
                String text = "You are " + Double.toString(newPercent)+ "% " +
                        "below " + country + " carbon emissions per capita";
                showComp.setText(text);
            } else if (percent == 100) {
                String text = "You are on par with " + country +
                        " carbon emissions per capita ";
                showComp.setText(text);
            } else {
                String text = "You are " + Double.toString(percent) + "% " +
                        "above " + country + " carbon emissions per capita";
                showComp.setText(text);
            }
        });
        return view;
    }

    private void populateCountryMap(Context context, HashMap<String, Double> map){
        InputStreamReader is;
        try {
            is = new InputStreamReader(context.getAssets().open("globalAvg.csv"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        BufferedReader reader = new BufferedReader(is);
        try{
            String line = reader.readLine();
            while ((line = reader.readLine()) != null){
                String[] tokens = line.split(",");
                String country = tokens[0];
                Double emissions = parseDouble(tokens[1]);
                map.put(country, emissions);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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