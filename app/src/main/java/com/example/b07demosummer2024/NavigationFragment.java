package com.example.b07demosummer2024;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class NavigationFragment extends Fragment{

    public NavigationFragment() {
        // Required empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.feature_navigation, container, false);

        Button buttonNavTracker = view.findViewById(R.id.buttonNavTracker);
        Button buttonNavGauge = view.findViewById(R.id.buttonNavGauge);
        Button buttonNavBalance = view.findViewById(R.id.buttonNavBalance);
        Button buttonNavHub = view.findViewById(R.id.buttonNavHub);
        Button buttonNavCalculate = view.findViewById(R.id.buttonNavCalculate);
        Button buttonNavProfile = view.findViewById(R.id.buttonNavProfile);

        buttonNavTracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new SpinnerFragment());
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
                loadFragment(new LoginFragment());
            } // TODO: Need to replace with the actual fragment once created
        });

        buttonNavProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new RegisterFragment());
            } // TODO: Need to replace with the actual fragment once created
        });

        return view;
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}

