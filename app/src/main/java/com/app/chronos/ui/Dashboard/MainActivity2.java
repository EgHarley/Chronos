package com.app.chronos.ui.Dashboard;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.chronos.R;
import com.app.timebox.Schedule;
import com.app.timebox.Time;
import com.app.timebox.TimetableView;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity2 extends Fragment implements View.OnClickListener, TextToSpeech.OnInitListener {

    private TextToSpeech myTTS;   // Define the TTS objecy
    private int MY_DATA_CHECK_CODE = 0;

    private View root;
    private MainActivity2 context;
    public static final int REQUEST_ADD = 1;
    public static final int REQUEST_EDIT = 2;

    private Button addBtn;
    private Button clearBtn;
    private Button saveBtn;
    private Button loadBtn;

    private TimetableView timetable;

    //EditActivity content
    public static final int RESULT_OK_ADD = 1;
    public static final int RESULT_OK_EDIT = 2;
    public static final int RESULT_OK_DELETE = 3;

    //private Context context;

    private Button goback;
    private Button deleteBtn;
    private Button submitBtn;
    private EditText subjectEdit;
    private EditText classroomEdit;
    private EditText professorEdit;
    private Spinner daySpinner;
    private TextView startTv;
    private TextView endTv;

    //request mode
    private int mode;

    private Schedule schedule;
    private int editIdx;
    private View v;


    public View onCreateView ( @NonNull LayoutInflater inflater,
                               ViewGroup container, Bundle savedInstanceState ) {


        root = inflater.inflate(R.layout.activity_main2, container, false);
        //init();

        this.context = this;
        addBtn = root.findViewById(R.id.add_btn);
        addBtn.setBackgroundColor(0x00000000);
        clearBtn = root.findViewById(R.id.clear_btn);
        clearBtn.setBackgroundColor(0x00000000);
        saveBtn = root.findViewById(R.id.save_btn);
        saveBtn.setBackgroundColor(0x00000000);
        loadBtn = root.findViewById(R.id.load_btn);
        loadBtn.setBackgroundColor(0x00000000);

        timetable = root.findViewById(R.id.timetable);
        initView();
/*
    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main2);


    }

 */
        return root;


    }

    private void speakWords ( String speech ) {
        //speak straight away
        //myTTS.setLanguage(Locale.US);
        //Toast (speech + " TTSTTTS");
        myTTS.speak(speech, TextToSpeech.LANG_COUNTRY_AVAILABLE, null);
    }
    @Override
    public void onInit ( int status ) {
        // TODO Auto-generated method stub
        if (status == TextToSpeech.SUCCESS) {
            if(myTTS.isLanguageAvailable(Locale.US)==TextToSpeech.LANG_AVAILABLE)
                myTTS.setLanguage(Locale.US);
        } else if (status == TextToSpeech.ERROR) {
            Toast.makeText(getActivity(), "Sorry! Text To Speech failed...", Toast.LENGTH_LONG).show();
        }
    }
/*
    private void init () {

        //timetable.setHeaderHighlight(2);
        initView();
    }

 */

    private void initView () {
        addBtn.setOnClickListener(this);
        clearBtn.setOnClickListener(this);
        saveBtn.setOnClickListener(this);
        loadBtn.setOnClickListener(this);

        timetable.setOnStickerSelectEventListener(new TimetableView.OnStickerSelectedListener() {
            @Override
            public void OnStickerSelected ( int idx, ArrayList<Schedule> schedules ) {
                Intent i = new Intent(getActivity(), EditActivity.class);
                i.putExtra("mode", REQUEST_EDIT);
                i.putExtra("idx", idx);
                i.putExtra("schedules", schedules);
                startActivityForResult(i, REQUEST_EDIT);
            }
        });

    }


    public void onClick ( View v ) {

//REQUEST EDIT
        /*
        if(mode == MainActivity2.REQUEST_EDIT){
            Intent i = new Intent(getActivity(), MainActivity2.class);
            editIdx = i.getIntExtra("idx",-1);
            ArrayList<Schedule> schedules = (ArrayList<Schedule>)i.getSerializableExtra("schedules");
            schedule = schedules.get(0);
            subjectEdit.setText(schedule.getClassTitle());
            classroomEdit.setText(schedule.getClassPlace());
            professorEdit.setText(schedule.getProfessorName());
            daySpinner.setSelection(schedule.getDay());
            deleteBtn.setVisibility(View.VISIBLE);
        }

         */

        switch (v.getId()) {
            /*
            case R.id.add_btn:

                    final AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
                    View dialogView= LayoutInflater.from(v.getRootView().getContext()).inflate(R.layout.activity_task,null);

                    this.context = this;
                    //final Button goback = (Button)dialogView.findViewById(R.id.goback);
                    final Button deleteBtn = (Button)dialogView.findViewById(R.id.delete_btn);
                    deleteBtn.setBackgroundColor(0x00000000);
                    final Button submitBtn = (Button)dialogView.findViewById(R.id.submit_btn);
                    submitBtn.setBackgroundColor(0x00000000);
                    final EditText subjectEdit = (EditText)dialogView.findViewById(R.id.subject_edit);
                    final EditText classroomEdit = (EditText)dialogView.findViewById(R.id.classroom_edit);
                    final EditText professorEdit = (EditText)dialogView.findViewById(R.id.professor_edit);
                    final Spinner daySpinner = (Spinner)dialogView.findViewById(R.id.day_spinner);
                    final TextView startTv = (TextView)dialogView.findViewById(R.id.start_time);
                    final TextView endTv = (TextView)dialogView.findViewById(R.id.end_time);

                    //set the default time
                    schedule = new Schedule();
                    schedule.setStartTime(new Time(10,0));
                    schedule.setEndTime(new Time(13,30));

                    //checkMode();
                    Intent i = new Intent(getActivity(),MainActivity2.class);
                    mode = i.getIntExtra("mode",MainActivity2.REQUEST_ADD);


                    //initViewsEdit();



                    daySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            schedule.setDay(position);
                            ((TextView)parent.getChildAt(0)).setTextColor(Color.parseColor("#FFFFFF"));
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    startTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            TimePickerDialog dialog = new TimePickerDialog(getActivity(),listener,schedule.getStartTime().getHour(), schedule.getStartTime().getMinute(), false);
                            dialog.show();
                        }

                        private TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                startTv.setText(hourOfDay + ":" + minute);
                                schedule.getStartTime().setHour(hourOfDay);
                                schedule.getStartTime().setMinute(minute);
                            }
                        };
                    });
                    endTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            TimePickerDialog dialog = new TimePickerDialog(getActivity(),listener,schedule.getEndTime().getHour(), schedule.getEndTime().getMinute(), false);
                            dialog.show();
                        }

                        private TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                endTv.setText(hourOfDay + ":" + minute);
                                schedule.getEndTime().setHour(hourOfDay);
                                schedule.getEndTime().setMinute(minute);
                            }
                        };
                    });



                    //submit======================================================
                    builder.setView(dialogView);
                    builder.setNegativeButton("Back", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            builder.setCancelable(true);
                        }
                    });


                    builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int in) {


                            switch (v.getId()){
                                case R.id.submit_btn:
                                        inputDataProcessing();
                                        Intent i = new Intent();
                                        ArrayList<Schedule> schedules = new ArrayList<Schedule>();
                                        ArrayList<Schedule> item = (ArrayList<Schedule>) i.getSerializableExtra("schedules");
                                        //you can add more schedules to ArrayList
                                        schedules.add(schedule);
                                        i.putExtra("schedules",schedules);
                                        getActivity().setResult(RESULT_OK_ADD,i);
                                        getActivity().finish();
                                        timetable.add(schedules);



                                    break;

                                case R.id.delete_btn:
                                     i = new Intent();
                                    i.putExtra("idx",editIdx);
                                    getActivity().setResult(RESULT_OK_DELETE, i);
                                    getActivity().finish();
                                    break;
                            }

                        }//end of positive



                    }).setView(dialogView);//end of positive
                    //builder.setView(dialogView);
                    builder.show();
                    //});//end of addbtn


                break;

             */
            case R.id.add_btn:
                Intent i = new Intent(getActivity(),EditActivity.class);
                i.putExtra("mode",REQUEST_ADD);
                startActivityForResult(i,REQUEST_ADD);
                break;
            case R.id.clear_btn:
                timetable.removeAll();
                break;
            case R.id.save_btn:
                saveByPreference(timetable.createSaveData());
                break;
            case R.id.load_btn:
                loadSavedData();
                break;
        }
    }

    private void finish () {
    }


    @Override
    public void onActivityResult ( int requestCode, int resultCode, @Nullable Intent data ) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getChildFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
        switch (requestCode) {
            case REQUEST_ADD:
                if (resultCode == RESULT_OK_ADD) {

                    //------------------------------
                    ArrayList<Schedule> item = (ArrayList<Schedule>) data.getSerializableExtra("schedules");
                    timetable.add(item);
                }
                break;
            case REQUEST_EDIT:
                /** Edit -> Submit */
                if (resultCode == RESULT_OK_EDIT) {
                    int idx = data.getIntExtra("idx", -1);
                    ArrayList<Schedule> item = (ArrayList<Schedule>) data.getSerializableExtra("schedules");
                    timetable.edit(idx, item);
                }
                /** Edit -> Delete */
                else if (resultCode == RESULT_OK_DELETE) {
                    int idx = data.getIntExtra("idx", -1);
                    timetable.remove(idx);
                }
                break;
        }
    }

    /**
     * save timetableView's data to SharedPreferences in json format
     */
    private void saveByPreference ( String data ) {
        SharedPreferences mPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = mPref.edit();
        editor.putString("timetable_demo", data);
        editor.commit();
        Toast.makeText(getActivity(), "saved!", Toast.LENGTH_SHORT).show();
    }

    /**
     * get json data from SharedPreferences and then restore the timetable
     */
    private void loadSavedData () {
        timetable.removeAll();
        SharedPreferences mPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String savedData = mPref.getString("timetable_demo", "");
        if (savedData == null && savedData.equals("")) return;
        timetable.load(savedData);
        Toast.makeText(getActivity(), "loaded!", Toast.LENGTH_SHORT).show();
    }


    //editactivity content

    /** check whether the mode is ADD or EDIT */
    /* private void checkMode(){
        Intent i = new Intent(getActivity(), MainActivity2.class);
        mode = i.getIntExtra("mode",MainActivity2.REQUEST_ADD);

        if(mode == MainActivity2.REQUEST_EDIT){
            loadScheduleData();
            deleteBtn.setVisibility(View.VISIBLE);
        }
    }
    private void loadScheduleData(){
        Intent i = new Intent(getActivity(), MainActivity2.class);
        editIdx = i.getIntExtra("idx",-1);
        ArrayList<Schedule> schedules = (ArrayList<Schedule>)i.getSerializableExtra("schedules");
        schedule = schedules.get(0);
        subjectEdit.setText(schedule.getClassTitle());
        classroomEdit.setText(schedule.getClassPlace());
        professorEdit.setText(schedule.getProfessorName());
        daySpinner.setSelection(schedule.getDay());
    }
    private void inputDataProcessing(){
        schedule.setClassTitle(subjectEdit.getText().toString());
        schedule.setClassPlace(classroomEdit.getText().toString());
        schedule.setProfessorName(professorEdit.getText().toString());
    }

     */



}


