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


public class ACQ15Fragment extends LoadFragment {

    public ACQ15Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_a_c_q15, container, false);

        TextView textview = view.findViewById(R.id.Q15Response);
        set(textview, AnnualCarbonInformation.Housing, AnnualCarbonInformation.H, 4);

        Button u50 = view.findViewById(R.id.Q15U50);
        Button fifty = view.findViewById(R.id.Q1550);
        Button hundred = view.findViewById(R.id.Q15100);
        Button onefifty = view.findViewById(R.id.Q15150);
        Button o200 = view.findViewById(R.id.Q15O200);
        Button back = view.findViewById(R.id.Q15Back);
        Button next = view.findViewById(R.id.Q15Next);

        u50.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.Housing[4] = 1;
                AnnualCarbonInformation.H[4] = "Under $50";
                set(textview, AnnualCarbonInformation.Housing, AnnualCarbonInformation.H, 4);
            }
        });

        fifty.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.Housing[4] = 2;
                AnnualCarbonInformation.H[4] = "$50-$100";
                set(textview, AnnualCarbonInformation.Housing, AnnualCarbonInformation.H, 4);
            }
        });

        hundred.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.Housing[4] = 3;
                AnnualCarbonInformation.H[4] = "$100-$150";
                set(textview, AnnualCarbonInformation.Housing, AnnualCarbonInformation.H, 4);
            }
        });

        onefifty.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.Housing[4] = 4;
                AnnualCarbonInformation.H[4] = "$150-$200";
                set(textview, AnnualCarbonInformation.Housing, AnnualCarbonInformation.H, 4);
            }
        });

        o200.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.Housing[4] = 5;
                AnnualCarbonInformation.H[4] = "Over $200";
                set(textview, AnnualCarbonInformation.Housing, AnnualCarbonInformation.H, 4);
            }
        });

        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                loadFragment(new ACQ14Fragment());
            }
        });

        next.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                if (AnnualCarbonInformation.Housing[4] != 0){
                    //loadFragment(new ACQ16Fragment());
                }
            }
        });



        return view;
    }
}