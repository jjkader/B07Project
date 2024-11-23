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

public class LoadFragment extends Fragment{
    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void set(TextView View, int[] arr, String[] response, int index){
        String text;
        if (arr[index] == 0){
            text = "Please enter your choice";
        }
        else{
            text = "You have selected: " + response[index];
        }
        View.setText(text);
    }
}
