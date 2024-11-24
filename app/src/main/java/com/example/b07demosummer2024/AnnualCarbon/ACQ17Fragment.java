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


public class ACQ17Fragment extends LoadFragment {

    public ACQ17Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_a_c_q17, container, false);

        TextView textview = view.findViewById(R.id.Q17Response);
        set(textview, AnnualCarbonInformation.Housing, AnnualCarbonInformation.H, 6);

        Button prim = view.findViewById(R.id.Q17Prim);
        Button part = view.findViewById(R.id.Q17Part);
        Button no = view.findViewById(R.id.Q17No);
        Button back = view.findViewById(R.id.Q17Back);
        Button next = view.findViewById(R.id.Q17Next);

        prim.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.Housing[6] = 1;
                AnnualCarbonInformation.H[6] = "Yes, primarily";
                set(textview, AnnualCarbonInformation.Housing, AnnualCarbonInformation.H, 6);
            }
        });

        part.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.Housing[6] = 2;
                AnnualCarbonInformation.H[6] = "Yes, partially";
                set(textview, AnnualCarbonInformation.Housing, AnnualCarbonInformation.H, 6);
            }
        });

        no.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.Housing[6] = 1;
                AnnualCarbonInformation.H[6] = "No";
                set(textview, AnnualCarbonInformation.Housing, AnnualCarbonInformation.H, 6);
            }
        });

        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                loadFragment(new ACQ16Fragment());
            }
        });

        next.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                if (AnnualCarbonInformation.Housing[6] != 0){
                    loadFragment(new ACQ18Fragment());
                }
            }
        });

        return view;
    }
}