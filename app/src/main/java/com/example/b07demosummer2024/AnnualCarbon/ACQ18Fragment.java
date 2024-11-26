package com.example.b07demosummer2024.AnnualCarbon;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.b07demosummer2024.R;


public class ACQ18Fragment extends LoadFragment {

    public ACQ18Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_a_c_q18, container, false);

        TextView textview = view.findViewById(R.id.Q18Response);
        set(textview, AnnualCarbonInformation.Consumption, AnnualCarbonInformation.C, 0);

        Button monthly = view.findViewById(R.id.Q18Monthly);
        Button quarterly = view.findViewById(R.id.Q18Quarterly);
        Button annually = view.findViewById(R.id.Q18Annually);
        Button rarely = view.findViewById(R.id.Q18Rarely);
        Button back = view.findViewById(R.id.Q18Back);
        Button next = view.findViewById(R.id.Q18Next);

        monthly.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.Consumption[0] = 1;
                AnnualCarbonInformation.C[0] = "Monthly";
                set(textview, AnnualCarbonInformation.Consumption, AnnualCarbonInformation.C, 0);
            }
        });

        quarterly.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.Consumption[0] = 2;
                AnnualCarbonInformation.C[0] = "Quarterly";
                set(textview, AnnualCarbonInformation.Consumption, AnnualCarbonInformation.C, 0);
            }
        });

        annually.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.Consumption[0] = 3;
                AnnualCarbonInformation.C[0] = "Annually";
                set(textview, AnnualCarbonInformation.Consumption, AnnualCarbonInformation.C, 0);
            }
        });

        rarely.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.Consumption[0] = 4;
                AnnualCarbonInformation.C[0] = "Rarely";
                set(textview, AnnualCarbonInformation.Consumption, AnnualCarbonInformation.C, 0);
            }
        });

        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                loadFragment(new ACQ17Fragment());
            }
        });

        next.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                if (AnnualCarbonInformation.Consumption[0] != 0){
                    loadFragment(new ACQ19Fragment());
                }
            }
        });


        return view;
    }
}