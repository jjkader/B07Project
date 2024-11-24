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


public class ACQ13Fragment extends LoadFragment {


    public ACQ13Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_a_c_q13, container, false);

        TextView textview = view.findViewById(R.id.Q13Response);
        set(textview, AnnualCarbonInformation.Housing, AnnualCarbonInformation.H, 2);

        Button uone = view.findViewById(R.id.Q13UOne);
        Button onetwo = view.findViewById(R.id.Q13OneTwo);
        Button otwo = view.findViewById(R.id.Q13OTwo);
        Button back = view.findViewById(R.id.Q13Back);
        Button next = view.findViewById(R.id.Q13Next);

        uone.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.Housing[2] = 1;
                AnnualCarbonInformation.H[2] = "Under 1000 sq. ft.";
                set(textview, AnnualCarbonInformation.Housing, AnnualCarbonInformation.H, 2);
            }
        });

        onetwo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.Housing[2] = 2;
                AnnualCarbonInformation.H[2] = "1000-2000 sq. ft.";
                set(textview, AnnualCarbonInformation.Housing, AnnualCarbonInformation.H, 2);
            }
        });

        otwo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.Housing[2] = 3;
                AnnualCarbonInformation.H[2] = "Over 2000 sq. ft.";
                set(textview, AnnualCarbonInformation.Housing, AnnualCarbonInformation.H, 2);
            }
        });

        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                loadFragment(new ACQ12Fragment());
            }
        });

        next.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                if (AnnualCarbonInformation.Housing[2] != 0){
                    loadFragment(new ACQ14Fragment());
                }
            }
        });

        return view;
    }
}