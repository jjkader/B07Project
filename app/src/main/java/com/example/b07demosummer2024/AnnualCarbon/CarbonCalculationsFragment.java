package com.example.b07demosummer2024.AnnualCarbon;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b07demosummer2024.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class CarbonCalculationsFragment extends LoadFragment {

    public CarbonCalculationsFragment(){
        //Required Empty Constructor
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carbon_calculations, container, false);

        TextView total = view.findViewById(R.id.totalCalc);
        TextView totalTransportation = view.findViewById(R.id.transCalc);
        TextView totalFood = view.findViewById(R.id.foodCalc);
        TextView totalHousing = view.findViewById(R.id.houseCalc);
        TextView totalConsumption = view.findViewById(R.id.consumpCalc);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        String uid = user.getUid();
        FirebaseDatabase fdb = FirebaseDatabase.getInstance(
                "https://b07project-725cc-default-rtdb.firebaseio.com/");
        DatabaseReference reference = fdb.getReference().child("users").child(uid);


        AnnualCarbonInformation aci = new AnnualCarbonInformation(getContext());
        String transEmissions = (aci.transportationCalc()) + "kg";
        String foodEmissions = (aci.foodCalc()) + "kg";
        String houseEmissions = (aci.housingCalc()) + "kg";
        String consumpEmissions = (aci.consumptionCalc()) + "kg";
        String totalEmissions = (aci.totalCalc()) + "kg";

        totalTransportation.setText(transEmissions);
        totalHousing.setText(houseEmissions);
        totalFood.setText(foodEmissions);
        totalConsumption.setText(consumpEmissions);
        total.setText(totalEmissions);

        Map<String, Object> data = new HashMap<>();
        data.put("carType", aci.getCarType());
        data.put("foodWaste", aci.getFoodWaste());
        data.put("buyEcoClothes", aci.getBuyEcoClothes());
        data.put("howOftenRecycle", aci.getHowOftenRecycle());
        data.put("transportEmis", aci.transportationCalc());
        data.put("foodEmis", aci.foodCalc());
        data.put("houseEmis", aci.housingCalc());
        data.put("consumpEmis", aci.consumptionCalc());
        data.put("totalEmis", aci.totalCalc());
        reference.child("Yearly Data").updateChildren(data);
        return view;
    }
}