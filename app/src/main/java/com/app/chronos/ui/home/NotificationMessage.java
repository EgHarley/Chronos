package com.app.chronos.ui.home;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.app.chronos.R;

public class NotificationMessage extends AppCompatActivity {
    public TextView ViewTitle,ViewLocation,ViewTime,ViewDes,ViewDate,ViewMonthyear;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_item);
        ViewTitle = findViewById(R.id.textViewtitle);
        ViewLocation = findViewById(R.id.textViewlocation);
        ViewTime = findViewById(R.id.textViewtime);
        ViewDes = findViewById(R.id.textViewdes);
        ViewDate = findViewById(R.id.textViewdate);
        ViewMonthyear = findViewById(R.id.textViewmonthyear);
        Bundle bundle = getIntent().getExtras();
        ViewTitle.setText(bundle.getString("message"));
        ViewLocation.setText(bundle.getString("location"));
        ViewDes.setText(bundle.getString("description"));
        ViewTime.setText(bundle.getString("time"));
        ViewDate.setText(bundle.getString("date"));
        ViewMonthyear.setText(bundle.getString("monthyear"));

    }
}