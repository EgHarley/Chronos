package com.app.chronos.ui.home;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;

public class DAOadded {
    private FirebaseAuth firebaseAuth;


    private DatabaseReference databaseReference;

    public DAOadded(){

        String eventname;
        firebaseAuth = FirebaseAuth.getInstance();
        String UserId = firebaseAuth.getCurrentUser().getUid();

        FirebaseDatabase db =FirebaseDatabase.getInstance();
        databaseReference = db.getReference(AddedEvent.class.getSimpleName()+"/"+UserId);

    }
    public Task<Void> add(AddedEvent ev){

        return databaseReference.push().setValue(ev);

    }

    public Query get(String key){

        if (key == null){
            databaseReference.orderByKey().limitToFirst(4);
        }
        return databaseReference.orderByKey().startAfter(key).limitToFirst(4);
    }


    public Task<Void> update ( String key, HashMap<String, Object> hashMap ) {

       return databaseReference.child(key).updateChildren(hashMap);
    }

}
