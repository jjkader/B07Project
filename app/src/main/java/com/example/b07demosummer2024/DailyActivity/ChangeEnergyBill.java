package com.example.b07demosummer2024.DailyActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b07demosummer2024.LoginActivity;
import com.example.b07demosummer2024.R;
import com.google.firebase.database.DatabaseReference;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChangeEnergyBill#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChangeEnergyBill extends Fragment {
    private static final DecimalFormat df = new DecimalFormat("0.00");
    private static final String ARG_PARAM1 = "year";
    private static final String ARG_PARAM2 = "month";

    private int year;
    private int month;

    TextView date;
    RadioButton gas, electricity, oil, propane, wood;
    RadioGroup energyType;
    EditText bill;

    public ChangeEnergyBill() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param year Selected year.
     * @param month Selected month.
     * @return A new instance of fragment ChangeEnergyBill.
     */
    public static ChangeEnergyBill newInstance(int year, int month) {
        ChangeEnergyBill fragment = new ChangeEnergyBill();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, year);
        args.putInt(ARG_PARAM2, month);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            year = getArguments().getInt(ARG_PARAM1);
            month = getArguments().getInt(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_energy_bill, container, false);

        date = (TextView) view.findViewById(R.id.textDate);
        gas = (RadioButton) view.findViewById(R.id.radioGas);
        electricity = (RadioButton) view.findViewById(R.id.radioElectricity);
        oil = (RadioButton) view.findViewById(R.id.radioOil);
        propane = (RadioButton) view.findViewById(R.id.radioPropane);
        wood = (RadioButton) view.findViewById(R.id.radioWood);
        energyType = (RadioGroup) view.findViewById(R.id.radioGroupEnergyType);
        bill = (EditText) view.findViewById(R.id.editTextBill);

        Button setBill = (Button) view.findViewById(R.id.setBill);
        ImageButton logo = (ImageButton) view.findViewById(R.id.planetzeLogo);

        logo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://planetze.io/"));
                startActivity(browserIntent);
            }
        });

        String Date = year + "-" + (month+1);
        date.setText(Date);

        setBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selected = energyType.getCheckedRadioButtonId();
                if (selected != -1) {
                    RadioButton selectedButton = (RadioButton) view.findViewById(selected);
                    String energy = selectedButton.getText().toString();
                    DatabaseReference userRef = DailyTrackingActivity.userRef;
                    String billAmtStr = bill.getText().toString();
                    double billAmt;
                    try {
                        billAmt = Double.parseDouble(billAmtStr);
                        billAmtStr = df.format(billAmt);
                        Map<String, Object> energyBill = new HashMap<>();
                        energyBill.put("energyType", energy);
                        energyBill.put("billAmt", billAmtStr);
                        userRef.child("energyBill").child(Date).setValue(energyBill);

                        Toast.makeText(getContext(),
                                "Updated Energy Bill", Toast.LENGTH_SHORT).show();

                        getParentFragmentManager().popBackStack();
                    } catch(Exception e) {
                        Toast.makeText(getContext(),
                                "Please enter a valid bill amount", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(),
                            "Please select an energy type", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}