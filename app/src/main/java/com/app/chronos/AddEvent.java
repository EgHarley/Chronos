package com.app.chronos;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddEvent extends AppCompatActivity {

    EditText title,location,description;
    Button addEvent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_add_event);
;
        title = findViewById(R.id.EventTitle);
        location = findViewById(R.id.EventPlace);
        description = findViewById(R.id.EventDes);
        addEvent = findViewById(R.id.submit);

        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!title.getText().toString().isEmpty() && !location.getText().toString().isEmpty()
                && !description.getText().toString().isEmpty()){

                }else {
                    Toast.makeText(AddEvent.this,"Please fill all the fields",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}