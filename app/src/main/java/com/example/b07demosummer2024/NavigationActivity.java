package com.example.b07demosummer2024;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.b07demosummer2024.DailyActivity.DailyTrackingActivity;
import com.example.b07demosummer2024.DailyActivity.EcoTrackerHomeFragment;
import com.example.b07demosummer2024.AnnualCarbon.AnnualCarbonActivity;

public class NavigationActivity extends AppCompatActivity {

    public NavigationActivity() {
        // Required empty constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        ImageButton logo = findViewById(R.id.planetzeLogo);
        Button buttonNavTracker = findViewById(R.id.buttonNavTracker);
        Button buttonNavGauge = findViewById(R.id.buttonNavGauge);
        Button buttonNavBalance = findViewById(R.id.buttonNavBalance);
        Button buttonNavHub = findViewById(R.id.buttonNavHub);
        Button buttonNavCalculate = findViewById(R.id.buttonNavCalculate);
        Button buttonNavProfile = findViewById(R.id.buttonNavProfile);

        logo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://planetze.io/"));
                startActivity(browserIntent);
            }
        });

        /* NOTE: These will not work (related to main), need to set to activities */
        buttonNavTracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(NavigationActivity.this, DailyTrackingActivity.class);
                NavigationActivity.this.startActivity(myIntent);
            }
        });

        buttonNavGauge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new ScrollerFragment());
            } // TODO: Need to replace with the actual fragment once created
        });

        buttonNavBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new SpinnerFragment());
            } // TODO: Need to replace with the actual fragment once created
        });

        buttonNavHub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new ManageItemsFragment());
            } // TODO: Need to replace with the actual fragment once created
        });

        buttonNavCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(NavigationActivity.this,
                        AnnualCarbonActivity.class);
                // clear activities before questionnaire
                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                NavigationActivity.this.startActivity(myIntent);
                finish();
            }
        });

        buttonNavProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new SpinnerFragment());
            } // TODO: Need to replace with the actual fragment once created
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}

