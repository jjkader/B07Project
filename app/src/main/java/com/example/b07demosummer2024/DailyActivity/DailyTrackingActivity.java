package com.example.b07demosummer2024.DailyActivity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.b07demosummer2024.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseUser;

public class DailyTrackingActivity extends AppCompatActivity {

    public static DatabaseReference db = FirebaseDatabase.getInstance(
            "https://b07project-725cc-default-rtdb.firebaseio.com/").getReference();
    public static FirebaseAuth auth = FirebaseAuth.getInstance();

    public static FirebaseUser user = auth.getCurrentUser();
    public static String uid = user.getUid();
    public static DatabaseReference userRef = db.child("users").child(uid);
    public static DatabaseReference userDailyActivityRef = userRef.child("dailyActivity");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_daily_tracking);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}