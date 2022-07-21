package com.app.chronos.ui.Event;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.app.chronos.navBar;
import com.app.chronos.ui.home.AddedEvent;
import com.app.chronos.ui.home.AlarmBrodcast;
import com.app.chronos.ui.home.BootUpReceiver;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.ramotion.foldingcell.FoldingCell;

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

    TextToSpeech tts;


    private Button timeButton;
    private Button timeButtonEnd;
    FoldingCell fcs;
    private Context context;
    ArrayList<AddedEvent> list;
    View view = null;
    Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
    private RecyclerView calendarRecyclerView;
    String key;
    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;
    public TextView ViewTitle,ViewLocation,ViewTimeS,ViewTimeE,ViewDes,ViewDate,ViewMonthyear;

    public  static String monthYearFromDate = "";
    public static LocalDate selectedDate = LocalDate.now();
    private View dialogView;
    private Intent intents;

    private View viewlet;

    FirebaseDatabase firebaseDatabase;
    Task<Void> databaseReference;
    FirebaseAuth fAuth;
    private ArrayList<String> daysOfMonth;
    private String UserId;


    public RVAdapter ( Context context, ArrayList<AddedEvent> list, ArrayList<String> daysOfMonth, String monthYearFromDate, FoldingCell fc ) {
        this.context = context;
        this.list = list;
        this.monthYearFromDate = monthYearFromDate;
        this.daysOfMonth = daysOfMonth;
        this.fcs = fc;
        
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder ( @NonNull ViewGroup parent, int viewType ) {
        //View v = LayoutInflater.from(context).inflate(R.layout.layout_item, parent,false);
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.layout_item,parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();

        return new MyViewHolder(view);
    }

    @Override
    public  void onBindViewHolder ( @NonNull MyViewHolder holder, int position ) {
        //EditText edit = null;


        AddedEvent addedEvent = list.get(position);
        holder.ViewTitle.setText(addedEvent.getTitle());
        holder.ViewLocation.setText(addedEvent.getLocation());
        holder.ViewTimeS.setText(addedEvent.getTimeStart());
        holder.ViewTimeE.setText(addedEvent.getTimeEnd());
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

                            /*
                        final FoldingCell fc = (FoldingCell) dialogView.findViewById(R.id.folding_cell);

                        // attach click listener to folding cell
                        fc.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View ve) {
                                fc.toggle(false);
                            }
                        });

                             */
                            //View myview=dialogView.getRootView();
                            final EditText title = (EditText) dialogView.findViewById(R.id.EventTitle);
                            final EditText location = (EditText) dialogView.findViewById(R.id.EventPlace);
                            final EditText description = (EditText) dialogView.findViewById(R.id.EventDes);
                            final Button timeButton = (Button) dialogView.findViewById(R.id.eventTimeStart);
                            final Button timeButtonEnd = (Button) dialogView.findViewById(R.id.eventTimeEnd);
                        final CheckBox checkreminder =(CheckBox) dialogView.findViewById(R.id.checkboxRemind);
                            // Button submit=myview.findViewById(R.id.usubmit);
                            final String date = dayText.format(String.valueOf(addedEvent.getDate()), "dd", Locale.ENGLISH);

                            fAuth = FirebaseAuth.getInstance();
                            String UserId = fAuth.getCurrentUser().getUid();

                            title.setText(addedEvent.getTitle());
                            location.setText(addedEvent.getLocation());
                            timeButton.setText(addedEvent.getTimeStart());
                            timeButtonEnd.setText(addedEvent.getTimeEnd());
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
                            timeButtonEnd.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick ( View v ) {
                                    Calendar calendarX = Calendar.getInstance();
                                    int hours = calendarX.get(Calendar.HOUR_OF_DAY);
                                    int minutes = calendarX.get(Calendar.MINUTE);
                                    int seconds = calendarX.get(Calendar.SECOND);
                                    TimePickerDialog timePickerDialog = new TimePickerDialog(dialogView.getContext(), R.style.Theme_AppCompat_Dialog,
                                            new TimePickerDialog.OnTimeSetListener() {
                                                @Override
                                                public void onTimeSet ( TimePicker view, int hourOfDay, int minute ) {
                                                    Calendar x = Calendar.getInstance();
                                                    x.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                                    x.set(Calendar.MINUTE, minute);
                                                    x.set(Calendar.SECOND, 00);
                                                    x.setTimeZone(TimeZone.getDefault());
                                                    SimpleDateFormat hformate = new SimpleDateFormat("hh:mm:00 a", Locale.ENGLISH);
                                                    String eventTimex = hformate.format(x.getTime());
                                                    timeButtonEnd.setText(eventTimex);

                                                }
                                            }, hours, minutes, false);
                                    timePickerDialog.show();
                                }
                            });

                        checkreminder.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick ( View view ) {

                                if (checkreminder.isChecked()) {

                                    Calendar calendar = Calendar.getInstance();
                                    int hours= calendar.get(Calendar.HOUR_OF_DAY);
                                    int minutes= calendar.get(Calendar.MINUTE);
                                    int second = calendar.get(Calendar.SECOND);
                                    TimePickerDialog timePickerDialog = new TimePickerDialog(dialogView.getContext(), R.style.Theme_AppCompat_Dialog,
                                            new TimePickerDialog.OnTimeSetListener() {
                                                @Override
                                                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                                    //minusoftime = minute - 5;
                                                    Calendar a = Calendar.getInstance();
                                                    a.set(Calendar.HOUR_OF_DAY,hourOfDay);
                                                    a.set(Calendar.MINUTE,minute );
                                                    a.set(Calendar.SECOND,00);
                                                    a.setTimeZone(TimeZone.getDefault());
                                                    SimpleDateFormat hformate = new SimpleDateFormat("h:mm:00 a",Locale.ENGLISH);
                                                    String eventTimeR = hformate.format(a.getTime());
                                                    checkreminder.setText(eventTimeR);

                                                }
                                            },hours,minutes,false);
                                    timePickerDialog.show();


                                }


                            }
                        });

                        //=======================================
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
                                    hashMap.put("timeStart", timeButton.getText().toString());
                                    hashMap.put("timeEnd", timeButtonEnd.getText().toString());
                                    hashMap.put("description", description.getText().toString());
                                    hashMap.put("reminder",checkreminder.getText().toString().trim());

                                    String titles = (title.getText().toString().trim());
                                    String loc = (location.getText().toString().trim());
                                    String timeS = (timeButton.getText().toString().trim());
                                    String timeE = (timeButtonEnd.getText().toString().trim());
                                    String des = (description.getText().toString().trim());
                                    String dd = (date.trim());
                                    String MMyy = (monthYearFromDate.trim());
                                    String CH = (checkreminder.getText().toString().trim());
                                    //String dd = (date);
                                    //String MMyy =  (monthYearFromDate);

                                    addedEvent.setTitle(titles);
                                    addedEvent.setLocation(loc);
                                    addedEvent.setTimeStart(timeS);
                                    addedEvent.setTimeEnd(timeE);
                                    addedEvent.setDate(dd);
                                    addedEvent.setDescription(des);
                                    addedEvent.setMonthyear(MMyy);
                                    addedEvent.setReminder(CH);

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
                                                        tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
                                                            @Override
                                                            public void onInit ( int i ) {
                                                                if (i == TextToSpeech.SUCCESS) {
                                                                    tts.setLanguage(Locale.ENGLISH);
                                                                    tts.setSpeechRate(1.0f);
                                                                    tts.speak("Your activity is successfully updated", TextToSpeech.QUEUE_ADD, null);
                                                                }
                                                            }
                                                        });
                                                    }
                                                })

                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure ( @NonNull Exception e ) {
                                                        Toast.makeText(context, "Invalid update", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                        setAlarm(titles, timeS, timeE, des, loc, dd, MMyy,CH);
                                    }


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
                                tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
                                    @Override
                                    public void onInit ( int i ) {
                                        if (i == TextToSpeech.SUCCESS) {
                                            tts.setLanguage(Locale.ENGLISH);
                                            tts.setSpeechRate(1.0f);
                                            tts.speak("Your activity is successfully deleted", TextToSpeech.QUEUE_ADD, null);
                                        }
                                    }
                                });
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



    private void setAlarm ( String titles, String timeS, String timeE, String loc ,String des,String dd ,String MMyy,  String CH )  {
        AlarmManager am1 = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        AlarmManager am2 = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context.getApplicationContext(), AlarmBrodcast.class);
        intent.putExtra("event", titles);
        intent.putExtra("date", dd);
        intent.putExtra("monthyear", MMyy);
        intent.putExtra("timeStart", timeS);
        intent.putExtra("timeEnd", timeE);
        intent.putExtra("location", loc);
        intent.putExtra("description", des);
        intent.putExtra("reminder", CH);

        Intent intent2 = new Intent(context.getApplicationContext(), BootUpReceiver.class);
        intent2.putExtra("event", titles);
        intent2.putExtra("date", dd);
        intent2.putExtra("monthyear", MMyy);
        intent2.putExtra("timeStart", timeS);
        intent2.putExtra("timeEnd", timeE);
        intent2.putExtra("location", loc);
        intent2.putExtra("description", des);
        intent2.putExtra("reminder", CH);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        String date = dd;
        String my = MMyy;
        String tm = timeS;
        String textte = titles;
        //dup
        PendingIntent pendingIntent2 = PendingIntent.getBroadcast(context.getApplicationContext(), 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
        String dat2 = dd;
        String my2 = MMyy;
        String ch2 = CH;


        try {
            Date date2 = new SimpleDateFormat("d MMMM yyyy h:mm:ss a").parse(dat2 + " " + my2 + " " + ch2);
            am2.set(AlarmManager.RTC_WAKEUP,  date2.getTime() + 10, pendingIntent2);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        try {
            Date date1 = new SimpleDateFormat("d MMMM yyyy h:mm:ss a").parse(date + " " + my + " " + tm);
            am1.set(AlarmManager.RTC_WAKEUP,  date1.getTime() + 10, pendingIntent);


        } catch (ParseException e) {
            e.printStackTrace();
        }




    }


    @Override
    public int getItemCount () {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView ViewTitle,ViewLocation,ViewTimeS,ViewTimeE,ViewDes,ViewDate,ViewMonthyear;
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

            final FoldingCell fc = (FoldingCell) itemView.findViewById(R.id.folding_cell);
            fc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fc.toggle(false);
                    fc.initialize(2000, Color.DKGRAY, 2);
// or with camera height parameter
                    fc.initialize(80, 2000, Color.DKGRAY, 1);
                }
            });


            dayOfMonth = itemView.findViewById(R.id.cellDayText);

            ViewTitle = itemView.findViewById(R.id.textViewtitle);
            ViewLocation = itemView.findViewById(R.id.textViewlocation);
            ViewTimeS = itemView.findViewById(R.id.textViewtimeStart);
            ViewTimeE = itemView.findViewById(R.id.textViewtimeEnd);
            ViewDes = itemView.findViewById(R.id.textViewdes);
            ViewDate = itemView.findViewById(R.id.textViewdate);
            ViewMonthyear = itemView.findViewById(R.id.textViewmonthyear);
            txtOption = itemView.findViewById(R.id.txt_option);
        }


    }
}

