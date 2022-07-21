package com.app.chronos.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.app.chronos.R;
import com.ramotion.foldingcell.FoldingCell;

public class NotificationMessage extends AppCompatActivity {
    public TextView ViewTitle,ViewLocation,ViewTimeS,ViewTimeE,ViewDes,ViewDate,ViewMonthyear;

    TextToSpeech tts;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_item);


        // get our folding cell
        final FoldingCell fc = (FoldingCell) findViewById(R.id.folding_cell);

        // attach click listener to folding cell
        fc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fc.toggle(false);
                fc.initialize(2000, Color.DKGRAY, 2);
// or with camera height parameter
                fc.initialize(80, 2000, Color.DKGRAY, 1);
            }
        });
        ViewTitle = findViewById(R.id.textViewtitle);
        ViewLocation = findViewById(R.id.textViewlocation);
        ViewTimeS = findViewById(R.id.textViewtimeStart);
        ViewTimeE = findViewById(R.id.textViewtimeEnd);
        ViewDes = findViewById(R.id.textViewdes);
        ViewDate = findViewById(R.id.textViewdate);
        ViewMonthyear = findViewById(R.id.textViewmonthyear);
        Bundle bundle = getIntent().getExtras();
        ViewTitle.setText(bundle.getString("event"));
        ViewLocation.setText(bundle.getString("location"));
        ViewDes.setText(bundle.getString("description"));
        ViewTimeS.setText(bundle.getString("timeStart"));
        ViewTimeE.setText(bundle.getString("timeEnd"));
        ViewDate.setText(bundle.getString("date"));
        ViewMonthyear.setText(bundle.getString("monthyear"));



    }
}