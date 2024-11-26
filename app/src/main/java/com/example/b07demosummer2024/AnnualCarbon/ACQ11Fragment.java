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


public class ACQ11Fragment extends LoadFragment {

    public ACQ11Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_a_c_q11, container, false);

        TextView textview = view.findViewById(R.id.Q11Response);
        set(textview, AnnualCarbonInformation.Housing, AnnualCarbonInformation.H, 0);

        Button det = view.findViewById(R.id.Q11Det);
        Button semi = view.findViewById(R.id.Q11Semi);
        Button town = view.findViewById(R.id.Q11Town);
        Button apt = view.findViewById(R.id.Q11Apt);
        Button other = view.findViewById(R.id.Q11Other);
        Button back = view.findViewById(R.id.Q11Back);
        Button next = view.findViewById(R.id.Q11Next);

        det.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.Housing[0] = 1;
                AnnualCarbonInformation.H[0] = "Detached";
                set(textview, AnnualCarbonInformation.Housing, AnnualCarbonInformation.H, 0);
            }
        });

        semi.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.Housing[0] = 2;
                AnnualCarbonInformation.H[0] = "Semi-detached";
                set(textview, AnnualCarbonInformation.Housing, AnnualCarbonInformation.H, 0);
            }
        });

        town.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.Housing[0] = 3;
                AnnualCarbonInformation.H[0] = "Townhouse";
                set(textview, AnnualCarbonInformation.Housing, AnnualCarbonInformation.H, 0);
            }
        });

        apt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.Housing[0] = 4;
                AnnualCarbonInformation.H[0] = "Condo/Apartment";
                set(textview, AnnualCarbonInformation.Housing, AnnualCarbonInformation.H, 0);
            }
        });

        other.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.Housing[0] = 5;
                AnnualCarbonInformation.H[0] = "Other";
                set(textview, AnnualCarbonInformation.Housing, AnnualCarbonInformation.H, 0);
            }
        });

        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                loadFragment(new ACQ10Fragment());
            }
        });

        next.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                if (AnnualCarbonInformation.Housing[0] != 0){
                    loadFragment(new ACQ12Fragment());
                }
            }
        });


        return view;
    }
}