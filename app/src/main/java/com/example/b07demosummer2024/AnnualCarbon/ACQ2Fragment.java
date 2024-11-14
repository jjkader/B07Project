package com.example.b07demosummer2024.AnnualCarbon;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.example.b07demosummer2024.R;

public class ACQ2Fragment extends Fragment{
    public ACQ2Fragment(){

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_a_c_q2, container, false);

        TextView response = view.findViewById(R.id.Q2Response);
        String text;
        if (AnnualCarbonInformation.PersonalVehicleUse[1] == 0){
            text = "Please enter your choice.";

        }
        else{
            text = "You have selected: " + AnnualCarbonInformation.PVU[1];
        }
        response.setText(text);

        Button gasoline = view.findViewById(R.id.Q2Gasoline);
        Button diesel = view.findViewById(R.id.Q2Diesel);
        Button hybrid = view.findViewById(R.id.Q2Hybrid);
        Button electric = view.findViewById(R.id.Q2Electric);
        Button idk = view.findViewById(R.id.Q2IDK);
        Button back = view.findViewById(R.id.Q2Back);
        Button next = view.findViewById(R.id.Q2Next);

        gasoline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnnualCarbonInformation.PersonalVehicleUse[1] = 1;
                AnnualCarbonInformation.PVU[1] = "Gasoline";
                String text = "You have selected: Gasoline";
                response.setText(text);
            }
        });

        diesel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnnualCarbonInformation.PersonalVehicleUse[1] = 2;
                AnnualCarbonInformation.PVU[1] = "Diesel";
                String text = "You have selected: Diesel";
                response.setText(text);
            }
        });

        hybrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnnualCarbonInformation.PersonalVehicleUse[1] = 3;
                AnnualCarbonInformation.PVU[1] = "Hybrid";
                String text = "You have selected: Hybrid";
                response.setText(text);
            }
        });

        electric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnnualCarbonInformation.PersonalVehicleUse[1] = 4;
                AnnualCarbonInformation.PVU[1] = "Electric";
                String text = "You have selected: Electric";
                response.setText(text);
            }
        });

        idk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnnualCarbonInformation.PersonalVehicleUse[1] = 5;
                AnnualCarbonInformation.PVU[1] = "I don't know";
                String text = "You have selected: I don't know";
                response.setText(text);
            }

        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new ACQ1Fragment());
            }

        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AnnualCarbonInformation.PersonalVehicleUse[1] != 0){
                    //loadFragment(new ACQ3Fragment);
                }
            }

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