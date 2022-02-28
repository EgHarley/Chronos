package com.app.chronos;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.app.chronos.ui.home.CalendarAdapter;

public class AddEvent extends AppCompatDialogFragment  {
       private EditText title,location,description;
       private   Button timeButton;
   // private ExampleDialogListener listener;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
/*
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View views = inflater.inflate(R.layout.activity_add_event,null);


        builder.setView(views);
        builder.setPositiveButton("Sumbit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                String Title = title.getText().toString();
                String eventplace = location.getText().toString();
                String Description = description.getText().toString();
                //listener.applyTexts(Title, eventplace, Description);


            }
        });


        title=views.findViewById(R.id.EventTitle);
        location=views.findViewById(R.id.EventPlace);
        description=views.findViewById(R.id.EventDes);
        timeButton=views.findViewById(R.id.eventTime);


        builder.create();
*/
    }




}