 package com.app.chronos.ui.Event;

 import android.content.SharedPreferences;
 import android.content.pm.ActivityInfo;
 import android.graphics.Color;
 import android.os.Bundle;
 import android.preference.PreferenceManager;
 import android.text.TextUtils;
 import android.view.LayoutInflater;
 import android.view.View;
 import android.view.ViewGroup;
 import android.widget.LinearLayout;
 import android.widget.RelativeLayout;
 import android.widget.TextView;
 import android.widget.Toast;

 import androidx.annotation.NonNull;
 import androidx.fragment.app.Fragment;
 import androidx.recyclerview.widget.LinearLayoutManager;
 import androidx.recyclerview.widget.RecyclerView;
 import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

 import com.app.chronos.R;
 import com.app.chronos.ui.home.AddedEvent;
 import com.app.chronos.ui.home.DAOadded;
 import com.google.firebase.auth.FirebaseAuth;
 import com.google.firebase.database.DataSnapshot;
 import com.google.firebase.database.DatabaseError;
 import com.google.firebase.database.DatabaseReference;
 import com.google.firebase.database.FirebaseDatabase;
 import com.google.firebase.database.ValueEventListener;

 import java.time.LocalDate;
 import java.time.format.DateTimeFormatter;
 import java.util.ArrayList;

 public class EventView<playService> extends Fragment {

     //String event_name,location,  event_description,  date, time,  monthyear;
    // ListView listView;
    // ArrayList<String>arrayList = new ArrayList<>();
     private ArrayList<String> daysOfMonth;
     public  static String monthYearFromDate = "";
     private TextView monthYearText;

     public static LocalDate selectedDate = LocalDate.now();
     //ArrayAdapter<AddedEvent> adapter;
     RecyclerView recyclerView;
     private ArrayList<AddedEvent> list;

     private RVAdapter adapter;

     /*private FirebaseDatabase root;
     private DatabaseReference reference;*/
   FirebaseDatabase firebaseDatabase;
   DatabaseReference databaseReference;
   FirebaseAuth fAuth;

    // private View ViewTitle,ViewLocation,ViewDescription,ViewTime,ViewDate,ViewMonthyear;
     View allview;
     private RelativeLayout ml;
     private LinearLayout cl;

     //RecyclerView recyclerView;
     //RVAdapter adapter;
     DAOadded dao;
     private View root;
     // private View root;
     private SwipeRefreshLayout swipeRefreshLayout;

     String key = null;
boolean isLoading=false;




     public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {



         root = inflater.inflate(R.layout.fragment_event, container, false);
         monthYearText = root.findViewById(R.id.monthYearTV);
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");

         monthYearFromDate = selectedDate.format(formatter);


         fAuth = FirebaseAuth.getInstance();
         String UserId = fAuth.getCurrentUser().getUid();

         firebaseDatabase = FirebaseDatabase.getInstance();
         databaseReference = firebaseDatabase.getReference("AddedEvent/"+UserId);

//         ml = root.findViewById(R.id.ELayout);
         //cl = root.findViewById(R.id.card_layout);
//         Load_setting();

         recyclerView = root.findViewById(R.id.rvs);
         recyclerView.setHasFixedSize(true);
         LinearLayoutManager manager = new LinearLayoutManager(getActivity());
         list = new ArrayList<>();
         daysOfMonth = new ArrayList<>();
         //  adapter = new RVAdapter(getActivity());
         adapter = new RVAdapter(getContext(),list,daysOfMonth,monthYearFromDate);
         recyclerView.setLayoutManager(manager);
         recyclerView.setAdapter(adapter);
         dao = new DAOadded();
         loadData();

         //swipeRefreshLayout = root.findViewById(R.id.swipe_refresh);
        /* swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
             @Override
             public void onRefresh() {
                 recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
                 {
                     @Override
                     public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy)
                     {
                         LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                         int totalItem = linearLayoutManager.getItemCount();
                         int lastVisible = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                         if(totalItem< lastVisible)
                         {
                             if(isLoading==false)
                             {
                                 isLoading=true;
                                 loadData();
                             }
                         }
                     }
                 });

                 Toast.makeText(getContext(), "Refreshed", Toast.LENGTH_LONG).show();
                 swipeRefreshLayout.setRefreshing(false);
             }
         });


         */

         //swipeRefreshLayout = root.findViewById(R.id.swipe_refresh);
         //adapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1);
        /* recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
         {
             @Override
             public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy)
             {
                 LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                 int totalItem = linearLayoutManager.getItemCount();
                 int lastVisible = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                 if(totalItem< lastVisible+3)
                 {
                     if(!isLoading)
                     {
                         isLoading=true;
                         loadData();
                     }
                 }
             }
         });

         */



        return root;
    }
    //end

    private void loadData(){
        //swipeRefreshLayout.setRefreshing(true);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange ( @NonNull DataSnapshot snapshot ) {

             //   ArrayList<AddedEvent> addedEvents = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()){

                    AddedEvent addedEvent = data.getValue(AddedEvent.class);
                    list.add(addedEvent);

                    addedEvent.setKey(data.getKey());
                    key = data.getKey();

                }

                //adapter.setItem(list);
                adapter.notifyDataSetChanged();
                //isLoading =false;
                //swipeRefreshLayout.setRefreshing(false);

            }
            @Override
            public void onCancelled ( @NonNull DatabaseError error ) {
                Toast.makeText(getContext(),"dbs error", Toast.LENGTH_SHORT).show();
                //swipeRefreshLayout.setRefreshing(false);
                //isLoading =false;
            }
        });

    }







    private void Load_setting(){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        boolean chk_night = sp.getBoolean("Night",false);
        if(chk_night){
            ml.setBackgroundColor(Color.parseColor("#222222"));
            //cl.setBackgroundColor(Color.parseColor("#222222"));
            //monthYearText.setTextColor(Color.parseColor("#ffffff"));
        }else {
            ml.setBackgroundColor(Color.parseColor("#cbc8f9"));
            //cl.setBackgroundColor(Color.parseColor("#cbc8f9"));
            //monthYearText.setTextColor(Color.parseColor("#000000"));
        }

        String orien = sp.getString("ORIENTATION","false");
        if("1".equals(orien)){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        }else if("2".equals(orien)){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        }else if("3".equals(orien)){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        }

    }

    private void setRequestedOrientation ( int screenOrientationPortrait ) {
    }

    protected void OnResume(){
        Load_setting();
        super.onResume();
    }












 }