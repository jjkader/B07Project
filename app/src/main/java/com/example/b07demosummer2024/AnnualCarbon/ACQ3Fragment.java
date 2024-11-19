package com.example.b07demosummer2024.AnnualCarbon;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Switch;

import com.example.b07demosummer2024.R;

public class ACQ3Fragment extends LoadFragment {


    public ACQ3Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_a_c_q3, container, false);

        Switch miles =  view.findViewById(R.id.Q3Miles);
        TextView textview = view.findViewById(R.id.Q3Response);
        Button Op1 = view.findViewById(R.id.Q35000);
        Button Op2 = view.findViewById(R.id.Q310000);
        Button Op3 = view.findViewById(R.id.Q315000);
        Button Op4 = view.findViewById(R.id.Q320000);
        Button Op5 = view.findViewById(R.id.Q325000);
        Button Op6 = view.findViewById(R.id.Q335000);
        Button back = view.findViewById(R.id.Q3Back);
        Button next = view.findViewById(R.id.Q3Next);

        set(textview, AnnualCarbonInformation.PersonalVehicleUse, AnnualCarbonInformation.PVU, 2);
        if (AnnualCarbonInformation.PersonalVehicleUse[2] != 0){
            String kmm = " kilometers/miles";
            textview.append(kmm);
        }


        miles.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){
                    String text1 = "Up to 3000";
                    String text2 = "3000-6000";
                    String text3 = "6000-9000";
                    String text4 = "9000-12000";
                    String text5 = "12000-15000";
                    String text6 = "15000+";
                    Op1.setText(text1);
                    Op2.setText(text2);
                    Op3.setText(text3);
                    Op4.setText(text4);
                    Op5.setText(text5);
                    Op6.setText(text6);
                }
                else{
                    String text1 = "Up to 5000";
                    String text2 = "5000-10000";
                    String text3 = "10000-15000";
                    String text4 = "15000-20000";
                    String text5 = "20000-25000";
                    String text6 = "25000+";
                    Op1.setText(text1);
                    Op2.setText(text2);
                    Op3.setText(text3);
                    Op4.setText(text4);
                    Op5.setText(text5);
                    Op6.setText(text6);
                }
            }
        });

        Op1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.PersonalVehicleUse[2] = 1;
                AnnualCarbonInformation.PVU[2] = "Up to 5000/3000";
                set(textview, AnnualCarbonInformation.PersonalVehicleUse, AnnualCarbonInformation.PVU, 2);
                String kmm = " kilometers/miles";
                textview.append(kmm);
            }
        });

        Op2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.PersonalVehicleUse[2] = 2;
                AnnualCarbonInformation.PVU[2] = "5000-10000/3000-6000";
                set(textview, AnnualCarbonInformation.PersonalVehicleUse, AnnualCarbonInformation.PVU, 2);
                String kmm = " kilometers/miles";
                textview.append(kmm);
            }
        });

        Op3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.PersonalVehicleUse[2] = 3;
                AnnualCarbonInformation.PVU[2] = "10000-15000/6000-9000";
                set(textview, AnnualCarbonInformation.PersonalVehicleUse, AnnualCarbonInformation.PVU, 2);
                String kmm = " kilometers/miles";
                textview.append(kmm);
            }
        });

        Op4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.PersonalVehicleUse[2] = 1;
                AnnualCarbonInformation.PVU[2] = "15000-20000/9000-12000";
                set(textview, AnnualCarbonInformation.PersonalVehicleUse, AnnualCarbonInformation.PVU, 2);
                String kmm = " kilometers/miles";
                textview.append(kmm);
            }
        });

        Op5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.PersonalVehicleUse[2] = 1;
                AnnualCarbonInformation.PVU[2] = "20000-25000/12000-15000";
                set(textview, AnnualCarbonInformation.PersonalVehicleUse, AnnualCarbonInformation.PVU, 2);
                String kmm = " kilometers/miles";
                textview.append(kmm);
            }
        });

        Op6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                AnnualCarbonInformation.PersonalVehicleUse[2] = 1;
                AnnualCarbonInformation.PVU[2] = "25000+/15000+";
                set(textview, AnnualCarbonInformation.PersonalVehicleUse, AnnualCarbonInformation.PVU, 2);
                String kmm = " kilometers/miles";
                textview.append(kmm);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new ACQ2Fragment());
            }

        });

        next.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (AnnualCarbonInformation.PersonalVehicleUse[2] != 0){
                    loadFragment(new ACQ4Fragment());
                }
            }
        });
        return view;
    }


}