package com.example.b07demosummer2024.DailyActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.b07demosummer2024.NavigationActivity;
import com.example.b07demosummer2024.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GoalTrackerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GoalTrackerFragment extends Fragment {

    private ProgressBar pb;
    private TextView percentage;

    CheckBox goal1, goal2, goal3, goal4, goal5, goal6, goal7;

    private Button backButton;
    private String p;
    private int completed = 0;
    private int tot = 0;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "goalProgress";

    // TODO: Rename and change types of parameters
    private int[] goalProgress;

    public GoalTrackerFragment() {
        // Required empty public constructor
    }

    public static GoalTrackerFragment newInstance(int[] goalProgress) {
        GoalTrackerFragment fragment = new GoalTrackerFragment();
        Bundle args = new Bundle();
        args.putIntArray(ARG_PARAM1, goalProgress);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            goalProgress = getArguments().getIntArray(ARG_PARAM1);
        }
        else{
            goalProgress = new int[7];
        }
        for (int i = 0; i < 7; i++){
            if (goalProgress[i] != -1) {
                completed += goalProgress[i];
                tot += 1;
            }
        }
        completed = (completed*100)/tot;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_goal_tracker, container, false);
        backButton = view.findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent myIntent = new Intent(getActivity(), DailyTrackingActivity.class);
                getActivity().startActivity(myIntent);
            }
        });
        goal1 = view.findViewById(R.id.goal1);
        if (goalProgress[0] == -1){
            goal1.setVisibility(View.GONE);
        }
        else if (goalProgress[0] == 1){
            goal1.setChecked(true);
        }
        goal2 = view.findViewById(R.id.goal2);
        if (goalProgress[1] == -1){
            goal2.setVisibility(View.GONE);
        }
        else if (goalProgress[1] == 1){
            goal2.setChecked(true);
        }
        goal3 = view.findViewById(R.id.goal3);
        if (goalProgress[2] == -1){
            goal3.setVisibility(View.GONE);
        }
        else if (goalProgress[2] == 1){
            goal3.setChecked(true);
        }
        goal4 = view.findViewById(R.id.goal4);
        if (goalProgress[3] == -1){
            goal4.setVisibility(View.GONE);
        }
        else if (goalProgress[3] == 1){
            goal4.setChecked(true);
        }
        goal5 = view.findViewById(R.id.goal5);
        if (goalProgress[4] == -1){
            goal5.setVisibility(View.GONE);
        }
        else if (goalProgress[4] == 1){
            goal5.setChecked(true);
        }
        goal6 = view.findViewById(R.id.goal6);
        if (goalProgress[5] == -1){
            goal6.setVisibility(View.GONE);
        }
        else if (goalProgress[5] == 1){
            goal6.setChecked(true);
        }
        goal7 = view.findViewById(R.id.goal7);
        if (goalProgress[6] == -1){
            goal7.setVisibility(View.GONE);
        }
        else if (goalProgress[6] == 1){
            goal7.setChecked(true);
        }
        percentage = view.findViewById(R.id.percentage);
        pb = view.findViewById(R.id.progressBar);
        pb.setProgress(completed);
        p = completed + "%";
        percentage.setText(p);
        return view;
    }
}