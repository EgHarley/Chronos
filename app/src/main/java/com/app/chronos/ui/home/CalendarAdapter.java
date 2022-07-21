  package com.app.chronos.ui.home;

  import android.app.AlarmManager;
  import android.app.AlertDialog;
  import android.app.PendingIntent;
  import android.app.TimePickerDialog;
  import android.content.BroadcastReceiver;
  import android.content.Context;
  import android.content.DialogInterface;
  import android.content.DialogInterface.OnClickListener;
  import android.content.Intent;
  import android.content.IntentFilter;
  import android.speech.tts.TextToSpeech;
  import android.util.Log;
  import android.view.LayoutInflater;
  import android.view.View;
  import android.view.ViewGroup;
  import android.widget.Button;
  import android.widget.CheckBox;
  import android.widget.EditText;
  import android.widget.TextView;
  import android.widget.TimePicker;
  import android.widget.Toast;

  import androidx.annotation.NonNull;
  import androidx.recyclerview.widget.GridLayoutManager;
  import androidx.recyclerview.widget.RecyclerView;

  import com.app.chronos.R;
  import com.google.firebase.database.DatabaseReference;
  import com.google.firebase.database.FirebaseDatabase;

  import java.text.ParseException;
  import java.text.SimpleDateFormat;
  import java.time.LocalDate;
  import java.time.format.DateTimeFormatter;
  import java.util.ArrayList;
  import java.util.Calendar;
  import java.util.Date;
  import java.util.Locale;
  import java.util.TimeZone;
  public class CalendarAdapter  extends RecyclerView.Adapter<CalendarViewHolder>  {

     // private BroadcastReceiver mReceiver = null;
      TextToSpeech tts;

     FirebaseDatabase firebaseDatabase;
     DatabaseReference databaseReference;
      private int MY_DATA_CHECK_CODE = 0;

     Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
     private RecyclerView calendarRecyclerView;



    private final ArrayList<String> daysOfMonth;
    private Context context;
    private Context getContext;
    private EditText title,location,description;
    private Button timeButton;
    private Button timeButtonEnd;
    private CheckBox checkreminder;


     TextView monthYearText;
     public  static String monthYearFromDate = "";
     public static LocalDate selectedDate = LocalDate.now();

     String dateFormat;
     private String eventDateFormat;
     private TextView monthyear;
     private Button next,prev;
     private Intent intent;

      private int notificationId = 1;

      private AlarmManager alarmManager  = null;
      private PendingIntent pendingIntent  = null;
      private int minusoftime ;
      private Button timeremind ;


//TextToSpeech tts;

    public CalendarAdapter ( RecyclerView calendarRecyclerView, TextView monthYearText, Button prev, Button next, String monthYearFromDate, ArrayList<String> daysOfMonth, Context context ) {
        this.daysOfMonth = daysOfMonth;
        this.context = context;
        this.monthYearFromDate = monthYearFromDate;
        this.prev = prev;
        this.next = next;
        this.monthYearText = monthYearText;
        this.calendarRecyclerView = calendarRecyclerView;
        //this.checkreminder = checkBox;
        //this.tts = tts;

    }



    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell,parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * 0.166666);
        //parent.setBackgroundColor(Color.parseColor("#753a88"));
        //parent.setBackgroundColor(Color.parseColor("#42275a"));


        return new CalendarViewHolder(view);

    }



    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {




        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(" ");



        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");



        String dayText = daysOfMonth.get(position);
    holder.dayOfMonth.setText(dayText);
    //holder.cale.setTextColor(Color.parseColor("red"));

    holder.dayOfMonth.setOnClickListener(v -> {


        final AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
        View dialogView= LayoutInflater.from(v.getRootView().getContext()).inflate(R.layout.activity_add_event,null);

        final EditText title = (EditText)dialogView.findViewById(R.id.EventTitle);
        final EditText location=(EditText)dialogView.findViewById(R.id.EventPlace);
        final EditText description=(EditText)dialogView.findViewById(R.id.EventDes);
        final Button timeButton =(Button)dialogView.findViewById(R.id.eventTimeStart);
        timeButton.setBackgroundColor(0x00000000);
        final Button timeButtonEnd =(Button)dialogView.findViewById(R.id.eventTimeEnd);
        timeButtonEnd.setBackgroundColor(0x00000000);
        final CheckBox checkreminder =(CheckBox) dialogView.findViewById(R.id.checkboxRemind);
        //final TextView monthYearText = (TextView)dialogView.findViewById(R.id.monthYearTV);
        //monthYearText = dialogView.findViewById(R.id.monthYearTV);
        prev =  prev.findViewById(R.id.prevMonth);
        next =  next.findViewById(R.id.nxtMonth);;
        calendarRecyclerView = dialogView.findViewById(R.id.calendarRecycleView);
        //checkreminder = checkreminder.findViewById(R.id.checkboxRemind);
        final String date = dayText.format(String.valueOf(position),"dd", Locale.ENGLISH);





        DAOadded dao = new DAOadded();

        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int hours= calendar.get(Calendar.HOUR_OF_DAY);
                int minutes= calendar.get(Calendar.MINUTE);
                int second = calendar.get(Calendar.SECOND);
                TimePickerDialog timePickerDialog = new TimePickerDialog(dialogView.getContext(), R.style.Theme_AppCompat_Dialog,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                //minusoftime = minute - 5;
                                Calendar c = Calendar.getInstance();
                                c.set(Calendar.HOUR_OF_DAY,hourOfDay);
                                c.set(Calendar.MINUTE,minute );
                                c.set(Calendar.SECOND,00);
                                c.setTimeZone(TimeZone.getDefault());
                                SimpleDateFormat hformate = new SimpleDateFormat("h:mm:00 a",Locale.ENGLISH);
                                String eventTime = hformate.format(c.getTime());
                                timeButton.setText(eventTime);

                            }
                        },hours,minutes,false);
                timePickerDialog.show();
            }
        });


        timeButtonEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendarX = Calendar.getInstance();
                int hours= calendarX.get(Calendar.HOUR_OF_DAY);
                int minutes= calendarX.get(Calendar.MINUTE);
                int second = calendarX.get(Calendar.SECOND);
                TimePickerDialog timePickerDialog = new TimePickerDialog(dialogView.getContext(), R.style.Theme_AppCompat_Dialog,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                //minusoftime = (Calendar.MINUTE - 5) ;
                                Calendar x = Calendar.getInstance();
                                x.set(Calendar.HOUR_OF_DAY,hourOfDay);
                                x.set(Calendar.MINUTE,minute);
                                x.set(Calendar.SECOND,00);
                                x.setTimeZone(TimeZone.getDefault());
                                SimpleDateFormat hformate = new SimpleDateFormat("h:mm:00 a",Locale.ENGLISH);
                                String eventTimex = hformate.format(x.getTime());
                                timeButtonEnd.setText(eventTimex);

                            }
                        },hours,minutes,false);
                timePickerDialog.show();
            }
        });
       // Calendar calendar = Calendar.getInstance();
        //int hours= calendar.get(Calendar.HOUR_OF_DAY);
        //int minutes= calendar.get(Calendar.MINUTE);
       // int second = calendar.get(Calendar.SECOND);
       // int hourOfDay = 0;
       // int minute = 0;
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



        //submit======================================================
        builder.setView(dialogView);
                builder.setNegativeButton("Back", new OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        builder.setCancelable(true);
                    }
                });


        builder.setPositiveButton("Submit", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {



       AddedEvent ev = new AddedEvent();
               String titles = (title.getText().toString().trim());
               String loc = (location.getText().toString().trim());
               String timeS = (timeButton.getText().toString().trim()) ;
               String timeE = (timeButtonEnd.getText().toString().trim()) ;
               String des = (description.getText().toString().trim()) ;
                String dd = (dayText);
                String MMyy =  (monthYearFromDate);
                String CH = (checkreminder.getText().toString().trim());
               // String TS = (tts.toString());


                ev.setTitle(titles);
                ev.setLocation(loc);
                ev.setTimeStart(timeS);
                ev.setTimeEnd(timeE);
                ev.setDate(dd);
                ev.setDescription(des);
                ev.setMonthyear(MMyy);
                ev.setReminder(CH);
                //ev.setVoiceNotif(TS);

                if (title.getText().toString().isEmpty()||location.getText().toString().isEmpty()||timeButton.getText().toString().isEmpty()||description.getText().toString().isEmpty()) {
                    Toast.makeText(context, "Please provide some text", Toast.LENGTH_SHORT).show();

                }else {


                    dao.add(ev).addOnFailureListener(er ->
                    {
                        Toast.makeText(context, "Error ", Toast.LENGTH_SHORT).show();
                    }).addOnSuccessListener(suc ->
                    {
                        Toast.makeText(context, "Event Saved", Toast.LENGTH_SHORT).show();

                    });
                    setAlarm(titles, dd, MMyy, timeS,timeE,des,loc,CH);
                }











            }//end of positive




        }).setView(dialogView);//end of positive

        builder.show();




    });




    }

      private void setAlarm ( String titles, String dd, String MMyy, String timeS, String timeE, String des, String loc, String CH ) {
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
          //intent.putExtra("voiceNotif", TS);

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


    tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
        @Override
        public void onInit ( int i ) {
            if (i == TextToSpeech.SUCCESS) {
                tts.setLanguage(Locale.ENGLISH);
                tts.setSpeechRate(1.0f);
                tts.speak("Your activity"+textte+ " are successfully saved!", TextToSpeech.QUEUE_ADD, null);
            }
        }
    });
          } catch (ParseException e) {
              e.printStackTrace();
          }



      }


      //end of onBind
/*
      //@Override
      protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
          onActivityResult(requestCode, resultCode, data);

          if (requestCode == 1) {
              if (resultCode == RESULT_OK && data != null) {
                  ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                  title.setText(text.get(0));
              }
          }

      }

 */






      private void setMonthView() {

         RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context.getApplicationContext(), 7);
         calendarRecyclerView.setLayoutManager(layoutManager);
         calendarRecyclerView.setAdapter(this);


     }



  //  private Context getActivity() {
   //     return getActivity();
   // }


    @Override
    public int getItemCount() {
        return daysOfMonth.size();
    }



    public void setContext(Context context) {
        this.context = context;
    }




      public interface OnItemListener {
        void onItemClick(int position, String dayText);
    }


  }
