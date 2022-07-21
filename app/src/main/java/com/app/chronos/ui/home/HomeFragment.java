package com.app.chronos.ui.home;

import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.chronos.R;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class HomeFragment<show> extends Fragment {

    private HomeViewModel homeViewModel;
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    public static LocalDate selectedDate = LocalDate.now();
    public  static String monthYearFromDate = "";
    private LocalDate date;
    private View root;
    private Button prev,next;
    private TemporalAccessor dates;
    ArrayList<String> daysInMonth;
    private ListView hourListView;


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private ArrayList<String> my;

    public HomeFragment(){
        //safe


    }

    private RelativeLayout ml;
    private TextView t1;
    private TextView t2;
    private TextView t3;
    private TextView t4;
    private TextView t5;
    private TextView t6;
    private TextView t7;


    public FloatingActionButton floatingActionButton;
    public FloatingActionButton floatingActionButton1;
    Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
    TextView CurrentDate;
    String dateFormat;
    Button timeButton;
    int hour, minute;
    EditText title,description,location;
    //CheckBox checkBox;
    //TextToSpeech tts;

    // Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
    //Context context;
    //java.text.SimpleDateFormat dateFormats = new java.text.SimpleDateFormat("MMMM yyyy", Locale.ENGLISH);
   // java.text.SimpleDateFormat monthFormat = new java.text.SimpleDateFormat("MMMM", Locale.ENGLISH);
    //java.text.SimpleDateFormat yearFormat = new java.text.SimpleDateFormat("yyyy", Locale.ENGLISH);
    //java.text.SimpleDateFormat eventDateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_BEHIND);
    /*    homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);*/
        root = inflater.inflate(R.layout.fragment_home, container, false);

        calendarRecyclerView = root.findViewById(R.id.calendarRecycleView);
        monthYearText = root.findViewById(R.id.monthYearTV);

        ml = root.findViewById(R.id.hmfrag);
        t1 = root.findViewById(R.id.sun);
        t2 = root.findViewById(R.id.mon);
        t3 = root.findViewById(R.id.tue);
        t4 = root.findViewById(R.id.wed);
        t5 = root.findViewById(R.id.thu);
        t6 = root.findViewById(R.id.fri);
        t7 = root.findViewById(R.id.sat);

        //checkBox = root.findViewById(R.id.checkboxRemind);



        prev = root.findViewById(R.id.prevMonth);
        prev.setBackgroundColor(0x00000000);
        next= root.findViewById(R.id.nxtMonth);
        next.setBackgroundColor(0x00000000);

        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");

         monthYearFromDate = selectedDate.format(formatter);
        if (!TextUtils.isEmpty(monthYearFromDate)){
            monthYearText.setText(monthYearFromDate);
        }else {
            monthYearText.setText(String.valueOf(selectedDate));
        }

        prev.setOnClickListener(v -> {
            selectedDate = selectedDate.minusMonths(1);
            monthYearText.setText(String.valueOf(selectedDate.format(formatter)));
            monthYearFromDate = selectedDate.format(formatter);
            setMonthView();



        });
        next.setOnClickListener(v -> {
            selectedDate = selectedDate.plusMonths(1);
            monthYearText.setText(String.valueOf(selectedDate.format(formatter)));
            monthYearFromDate = selectedDate.format(formatter);
            setMonthView();

            
        });



        setMonthView();





        return root;


    }

//end




    private void setMonthView() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        daysInMonth = new ArrayList<>(daysInMonthArray(selectedDate));
        monthYearFromDate = selectedDate.format(formatter);
        prev =  prev.findViewById(R.id.prevMonth);
        next =  next.findViewById(R.id.nxtMonth);
        monthYearText = root.findViewById(R.id.monthYearTV);
        calendarRecyclerView = root.findViewById(R.id.calendarRecycleView);
        /* = daysInMonthArray(selectedDate);*/
        //checkBox = root.findViewById(R.id.checkboxRemind);
        //TextToSpeech tts = null;

        CalendarAdapter calendarAdapter = new CalendarAdapter(calendarRecyclerView,monthYearText,prev,next,monthYearFromDate,daysInMonth,getContext().getApplicationContext());
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext().getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);


    }


    private ArrayList<String> daysInMonthArray(LocalDate date)
    {
        ArrayList<String> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);

        int daysInMonth = yearMonth.lengthOfMonth();

        LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        for(int i = 1; i <= 42; i++)
        {
            if(i <= dayOfWeek || i > daysInMonth + dayOfWeek)
            {
                daysInMonthArray.add("");
            }
            else
            {
                daysInMonthArray.add(String.valueOf(i - dayOfWeek));
            }
        }

        return  daysInMonthArray;
    }

    private void SetUpCalendar(){

        String currentDate = dateFormat.format(String.valueOf(calendar.getTime()));
        CurrentDate.setText(currentDate);
        CurrentDate.setBackgroundColor(0x00000000);
      //  CurrentDate.setBackgroundColor(Color.parseColor("#"));


    }
   /* public void popTimePicker()
    {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute)
            {
                hour = selectedHour;
                minute = selectedMinute;
                timeButton.setText(String.format(Locale.getDefault(), "%02d:%02d",hour, minute));
            }
        };

    */

        // int style = AlertDialog.THEME_HOLO_DARK;

       // TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), /*style,*/ onTimeSetListener, hour, minute, true);

       // timePickerDialog.setTitle("Select Time");
       // timePickerDialog.show();

    }






    /*@Override
    public void onItemClick(int position, String dayText) {

        if (dayText.equals(" ")){
            String message = "Selected Date" + dayText + " " + monthYearFromDate;
            Toast.makeText(getContext().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        }
    }
*/
//}