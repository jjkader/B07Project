package com.example.b07demosummer2024.EcoTrends;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.b07demosummer2024.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EcoGaugeHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EcoGaugeHomeFragment extends Fragment {

    Button overview, categoryBreakdown, trend, nationalComparison;

    public EcoGaugeHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_eco_gauge_home, container, false);

        overview = view.findViewById(R.id.buttonOverview);
        categoryBreakdown = view.findViewById(R.id.buttonCategoryBreakdown);
        trend = view.findViewById(R.id.buttonEmissionsTrend);
        nationalComparison = view.findViewById(R.id.buttonGlobalComparison);

        ImageButton logo = (ImageButton) view.findViewById(R.id.planetzeLogo);

        logo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://planetze.io/"));
                startActivity(browserIntent);
            }
        });

        overview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // load overview
            }
        });

        categoryBreakdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // load category breakdown
            }
        });

        trend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new EmissionsTrendFragment());
            }
        });

        nationalComparison.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // load national comparisons
            }
        });

        return view;
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}