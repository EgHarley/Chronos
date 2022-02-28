package com.app.chronos.ui.Event;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.RecyclerView;

import com.app.chronos.R;
import com.app.chronos.ui.home.AddedEvent;
import com.app.chronos.ui.home.AlarmBrodcast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.MyViewHolder> {


    private Context context;
    ArrayList<AddedEvent> list;
    View view = null;
    Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
    private RecyclerView calendarRecyclerView;
    String key;
    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;
    public TextView ViewTitle,ViewLocation,ViewTime,ViewDes,ViewDate,ViewMonthyear;

    public  static String monthYearFromDate = "";
    public static LocalDate selectedDate = LocalDate.now();
    private View dialogView;
    private Intent intents;


    FirebaseDatabase firebaseDatabase;
    Task<Void> databaseReference;
    FirebaseAuth fAuth;
    private ArrayList<String> daysOfMonth;
    private String UserId;


    public RVAdapter ( Context context, ArrayList<AddedEvent> list,ArrayList<String> daysOfMonth,String monthYearFromDate ) {
        this.context = context;
        this.list = list;
        this.monthYearFromDate = monthYearFromDate;
        this.daysOfMonth = daysOfMonth;
    }






    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder ( @NonNull ViewGroup parent, int viewType ) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_item, parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public  void onBindViewHolder ( @NonNull MyViewHolder holder, int position ) {
        //EditText edit = null;


        AddedEvent addedEvent = list.get(position);
        holder.ViewTitle.setText(addedEvent.getTitle());
        holder.ViewLocation.setText(addedEvent.getLocation());
        holder.ViewTime.setText(addedEvent.getTime());
        holder.ViewDes.setText(addedEvent.getDescription());
        holder.ViewDate.setText(addedEvent.getDate());
        holder.ViewMonthyear.setText(addedEvent.getMonthyear());
        holder.txtOption.setOnClickListener(v -> {

            PopupMenu popupMenu = new PopupMenu(context,holder.txtOption);
            popupMenu.inflate(R.menu.option_menu);
            popupMenu.setOnMenuItemClickListener(item-> {


                switch (item.getItemId()){
                    case R.id.menu_edit://new cardview layout same to the cardview, then edit


                        String dayText = String.valueOf(addedEvent.getDate());
                        //final String date = dayText.format(String.valueOf(position),"dd", Locale.ENGLISH);
                        //holder.dayOfMonth.setText(dayText);



                            final AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
                            View dialogView = LayoutInflater.from(v.getRootView().getContext()).inflate(R.layout.activity_add_event, null);

                            //View myview=dialogView.getRootView();
                            final EditText title = (EditText) dialogView.findViewById(R.id.EventTitle);
                            final EditText location = (EditText) dialogView.findViewById(R.id.EventPlace);
                            final EditText description = (EditText) dialogView.findViewById(R.id.EventDes);
                            final Button timeButton = (Button) dialogView.findViewById(R.id.eventTime);
                            // Button submit=myview.findViewById(R.id.usubmit);
                            final String date = dayText.format(String.valueOf(addedEvent.getDate()), "dd", Locale.ENGLISH);

                            fAuth = FirebaseAuth.getInstance();
                            String UserId = fAuth.getCurrentUser().getUid();

                            title.setText(addedEvent.getTitle());
                            location.setText(addedEvent.getLocation());
                            timeButton.setText(addedEvent.getTime());
                            description.setText(addedEvent.getDescription());


                            builder.show();
                            timeButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick ( View v ) {
                                    Calendar calendar = Calendar.getInstance();
                                    int hours = calendar.get(Calendar.HOUR_OF_DAY);
                                    int minutes = calendar.get(Calendar.MINUTE);
                                    int seconds = calendar.get(Calendar.SECOND);
                                    TimePickerDialog timePickerDialog = new TimePickerDialog(dialogView.getContext(), R.style.Theme_AppCompat_Dialog,
                                            new TimePickerDialog.OnTimeSetListener() {
                                                @Override
                                                public void onTimeSet ( TimePicker view, int hourOfDay, int minute ) {
                                                    Calendar c = Calendar.getInstance();
                                                    c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                                    c.set(Calendar.MINUTE, minute);
                                                    c.set(Calendar.SECOND, 00);
                                                    c.setTimeZone(TimeZone.getDefault());
                                                    SimpleDateFormat hformate = new SimpleDateFormat("hh:mm:00 a", Locale.ENGLISH);
                                                    String eventTime = hformate.format(c.getTime());
                                                    timeButton.setText(eventTime);

                                                }
                                            }, hours, minutes, false);
                                    timePickerDialog.show();
                                }
                            });

                            builder.setView(dialogView);
                            builder.setNegativeButton("Back", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick ( DialogInterface dialog, int i ) {
                                    builder.setCancelable(true);
                                }
                            });


                            builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick ( DialogInterface dialog, int i ) {
                                    HashMap<String, Object> hashMap = new HashMap<>();
                                    hashMap.put("title", title.getText().toString());
                                    hashMap.put("location", location.getText().toString());
                                    hashMap.put("time", timeButton.getText().toString());
                                    hashMap.put("description", description.getText().toString());

                                    String titles = (title.getText().toString().trim());
                                    String loc = (location.getText().toString().trim());
                                    String times = (timeButton.getText().toString().trim());
                                    String des = (description.getText().toString().trim());
                                    String dd = (date.trim());
                                    String MMyy = (monthYearFromDate.trim());
                                    //String dd = (date);
                                    //String MMyy =  (monthYearFromDate);

                                    addedEvent.setTitle(titles);
                                    addedEvent.setLocation(loc);
                                    addedEvent.setTime(times);
                                    addedEvent.setDate(dd);
                                    addedEvent.setDescription(des);
                                    addedEvent.setMonthyear(MMyy);

                                    if (title.getText().toString().isEmpty()||location.getText().toString().isEmpty()||timeButton.getText().toString().isEmpty()||description.getText().toString().isEmpty()) {
                                        Toast.makeText(context, "Please Enter or record the text", Toast.LENGTH_SHORT).show();

                                    }else {


                                        firebaseDatabase = FirebaseDatabase.getInstance();
                                        databaseReference = firebaseDatabase.getReference("AddedEvent/" + UserId)
                                                .child(addedEvent.getKey()).updateChildren(hashMap)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess ( Void aVoid ) {
                                                        Toast.makeText(context, "Event is already updated ", Toast.LENGTH_SHORT).show();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure ( @NonNull Exception e ) {
                                                        Toast.makeText(context, "Invalid update", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }
                                    setAlarm(titles, times, des, loc, dd, MMyy);

                                }
                            }).setView(dialogView);
                            builder.show();

                                break;
                    case R.id.menu_delete:

                        fAuth = FirebaseAuth.getInstance();
                        UserId = fAuth.getCurrentUser().getUid();

                        AlertDialog.Builder builders=new AlertDialog.Builder(holder.ViewTitle.getContext());
                        builders.setTitle("Delete");
                        builders.setMessage("Are you sure you want to delete this event?");

                        builders.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                firebaseDatabase = FirebaseDatabase.getInstance();
                                databaseReference = firebaseDatabase.getReference("AddedEvent/"+UserId)
                                        .child(addedEvent.getKey()).removeValue();
                                Toast.makeText(context, "Event is deleted", Toast.LENGTH_SHORT).show();
                            }
                        });

                        builders.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(context, "Canceled deletion. ", Toast.LENGTH_SHORT).show();
                            }
                        });

                        builders.show();
                        break;

                }
                return false;
            });

popupMenu.show();
        });





    }



    private void setAlarm ( String titles, String times, String loc ,String des,String dd ,String MMyy    )  {
        AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context.getApplicationContext(), AlarmBrodcast.class);
        intent.putExtra("event", titles);
        intent.putExtra("date", dd);
        intent.putExtra("monthyear", MMyy);
        intent.putExtra("time", times);
        intent.putExtra("location", loc);
        intent.putExtra("description", des);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);
        String date = dd;
        String my = MMyy;
        String tm = times;


        try {
            Date date1 = new SimpleDateFormat("d MMMM yyyy hh:mm:ss a").parse(date+" "+my+" "+tm);
            am.set(AlarmManager.RTC_WAKEUP, date1.getTime(), pendingIntent);

        } catch (ParseException e) {
            e.printStackTrace();
        }




    }





    @Override
    public int getItemCount () {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView ViewTitle,ViewLocation,ViewTime,ViewDes,ViewDate,ViewMonthyear;
        public TextView txtOption;
        public final TextView dayOfMonth;
        //public BreakIterator dayOfMonth;
        //public TextView dayOfMonth;
      //  private TextView eventText, timeAndDateText;
     //   private LinearLayout toplayout;

        public MyViewHolder ( @NonNull View itemView ) {
            super(itemView);
           // ViewTitle= (TextView) itemView.findViewById(R.id.eventTit);
          //  timeAndDateText = (TextView) itemView.findViewById(R.id.time_and_date);
          //  toplayout = (LinearLayout) itemView.findViewById(R.id.toplayout);
            dayOfMonth = itemView.findViewById(R.id.cellDayText);

            ViewTitle = itemView.findViewById(R.id.textViewtitle);
            ViewLocation = itemView.findViewById(R.id.textViewlocation);
            ViewTime = itemView.findViewById(R.id.textViewtime);
            ViewDes = itemView.findViewById(R.id.textViewdes);
            ViewDate = itemView.findViewById(R.id.textViewdate);
            ViewMonthyear = itemView.findViewById(R.id.textViewmonthyear);
            txtOption = itemView.findViewById(R.id.txt_option);
        }
    }
}

