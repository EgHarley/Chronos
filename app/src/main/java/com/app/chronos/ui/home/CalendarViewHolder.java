package com.app.chronos.ui.home;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.chronos.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class CalendarViewHolder extends RecyclerView.ViewHolder /*implements View.OnClickListener*/{

    private static final int MAX_CALENDAR_DAYS = 42;
    Context context;

    List<Date> dates = new ArrayList<>();
  //  List<Events> eventsList = new ArrayList<>();


        public final TextView dayOfMonth;
        public TextView today;
       // private final CalendarAdapter.OnItemListener onItemListener;

     public CalendarViewHolder(@NonNull View itemView) {
        super(itemView);
        dayOfMonth = itemView.findViewById(R.id.cellDayText);





        /* this.onItemListener = onItemListener;*/
       /*  itemView.setOnClickListener(this);*/


     }



   /* @Override
    public void onClick(View v) {
        onItemListener.onItemClick(getAdapterPosition(),(String) dayOfMonth.getText());
    }*/
}
