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


public class ACQ12Fragment extends LoadFragment {

    public ACQ12Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_a_c_q12, container, false);

        TextView textview = view.findViewById(R.id.Q12Response);
        set(textview, AnnualCarbonInformation.Housing, AnnualCarbonInformation.H, 1);

        Button one = view.findViewById(R.id.Q12One);
        Button two = view.findViewById(R.id.Q12Two);
        Button three = view.findViewById(R.id.Q12Three);
        Button five = view.findViewById(R.id.Q12Five);
        Button back = view.findViewById(R.id.Q12Back);
        Button next = view.findViewById(R.id.Q12Next);

        one.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.Housing[1] = 1;
                AnnualCarbonInformation.H[1] = "1";
                set(textview, AnnualCarbonInformation.Housing, AnnualCarbonInformation.H, 1);
            }
        });

        two.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.Housing[1] = 2;
                AnnualCarbonInformation.H[1] = "2";
                set(textview, AnnualCarbonInformation.Housing, AnnualCarbonInformation.H, 1);
            }
        });

        three.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.Housing[1] = 3;
                AnnualCarbonInformation.H[1] = "3-4";
                set(textview, AnnualCarbonInformation.Housing, AnnualCarbonInformation.H, 1);
            }
        });

        five.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.Housing[1] = 4;
                AnnualCarbonInformation.H[1] = "5 or more";
                set(textview, AnnualCarbonInformation.Housing, AnnualCarbonInformation.H, 1);
            }
        });

        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                loadFragment(new ACQ11Fragment());
            }
        });

        next.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                if (AnnualCarbonInformation.Housing[1] != 0){
                    loadFragment(new ACQ13Fragment());
                }
            }
        });

        return view;
    }
}