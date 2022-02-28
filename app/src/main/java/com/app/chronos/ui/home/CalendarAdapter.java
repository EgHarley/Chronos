  package com.app.chronos.ui.home;

  import android.app.AlarmManager;
  import android.app.AlertDialog;
  import android.app.PendingIntent;
  import android.app.TimePickerDialog;
  import android.content.Context;
  import android.content.DialogInterface;
  import android.content.DialogInterface.OnClickListener;
  import android.content.Intent;
  import android.graphics.Color;
  import android.speech.RecognizerIntent;
  import android.view.LayoutInflater;
  import android.view.View;
  import android.view.ViewGroup;
  import android.widget.Button;
  import android.widget.EditText;
  import android.widget.TextView;
  import android.widget.TimePicker;
  import android.widget.Toast;

  import androidx.annotation.NonNull;
  import androidx.annotation.Nullable;
  import androidx.recyclerview.widget.GridLayoutManager;
  import androidx.recyclerview.widget.RecyclerView;

  import com.app.chronos.R;
  import com.google.firebase.database.DatabaseReference;
  import com.google.firebase.database.FirebaseDatabase;

  import java.io.Serializable;
  import java.text.DateFormat;
  import java.text.ParseException;
  import java.text.SimpleDateFormat;
  import java.time.LocalDate;
  import java.time.format.DateTimeFormatter;
  import java.time.format.DateTimeFormatterBuilder;
  import java.util.ArrayList;
  import java.util.Calendar;
  import java.util.Date;
  import java.util.Locale;
  import java.util.TimeZone;

  import static android.app.Activity.RESULT_OK;
  import static android.content.Context.ALARM_SERVICE;

  public class CalendarAdapter  extends RecyclerView.Adapter<CalendarViewHolder>  {


     FirebaseDatabase firebaseDatabase;
     DatabaseReference databaseReference;

     Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
     private RecyclerView calendarRecyclerView;


    private final ArrayList<String> daysOfMonth;
    private Context context;
    private Context getContext;
    private EditText title,location,description;
    private Button timeButton;

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



    public CalendarAdapter ( RecyclerView calendarRecyclerView, TextView monthYearText, Button prev, Button next, String monthYearFromDate, ArrayList<String> daysOfMonth, Context context ) {
        this.daysOfMonth = daysOfMonth;
        this.context = context;
        this.monthYearFromDate = monthYearFromDate;
        this.prev = prev;
        this.next = next;
        this.monthYearText = monthYearText;
        this.calendarRecyclerView = calendarRecyclerView;


    }



    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell,parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * 0.166666);

        return new CalendarViewHolder(view);

    }



    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {



        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(" ");



        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");



        String dayText = daysOfMonth.get(position);
    holder.dayOfMonth.setText(dayText);
    holder.dayOfMonth.setTextColor(Color.parseColor("red"));

    holder.dayOfMonth.setOnClickListener(v -> {


        final AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
        View dialogView= LayoutInflater.from(v.getRootView().getContext()).inflate(R.layout.activity_add_event,null);

        final EditText title = (EditText)dialogView.findViewById(R.id.EventTitle);
        final EditText location=(EditText)dialogView.findViewById(R.id.EventPlace);
        final EditText description=(EditText)dialogView.findViewById(R.id.EventDes);
        final Button timeButton =(Button)dialogView.findViewById(R.id.eventTime);
        //final TextView monthYearText = (TextView)dialogView.findViewById(R.id.monthYearTV);
        monthYearText = dialogView.findViewById(R.id.monthYearTV);
        prev =  prev.findViewById(R.id.prevMonth);
        next =  next.findViewById(R.id.nxtMonth);;
        calendarRecyclerView = dialogView.findViewById(R.id.calendarRecycleView);

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
                                //timeTonotify = hourOfDay + ":" + minute;
                                Calendar c = Calendar.getInstance();
                                c.set(Calendar.HOUR_OF_DAY,hourOfDay);
                                c.set(Calendar.MINUTE,minute);
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
               String times = (timeButton.getText().toString().trim()) ;
               String des = (description.getText().toString().trim()) ;
                String dd = (dayText);
                String MMyy =  (monthYearFromDate);

                ev.setTitle(titles);
                ev.setLocation(loc);
                ev.setTime(times);
                ev.setDate(dd);
                ev.setDescription(des);
                ev.setMonthyear(MMyy);


                if (title.getText().toString().isEmpty()||location.getText().toString().isEmpty()||timeButton.getText().toString().isEmpty()||description.getText().toString().isEmpty()) {
                    Toast.makeText(context, "Please Enter or record the text", Toast.LENGTH_SHORT).show();

                }else {


                    dao.add(ev).addOnFailureListener(er ->
                    {
                        Toast.makeText(context, "Error ", Toast.LENGTH_SHORT).show();
                    }).addOnSuccessListener(suc ->
                    {
                        Toast.makeText(context, "Event Saved", Toast.LENGTH_SHORT).show();
                    });

                }



                    setAlarm(titles, dd, MMyy, times,des,loc);



            }//end of positive




        }).setView(dialogView);

        builder.show();




    });




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

      private void setAlarm ( String titles, String dd, String MMyy, String times, String loc ,String des  )  {
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
              am.set(AlarmManager.RTC_WAKEUP, date1.getTime()+2000, pendingIntent);

          } catch (ParseException e) {
              e.printStackTrace();
          }


      }




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
