package com.app.chronos.ui.home;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import static android.content.ContentValues.TAG;

public class DAOadded {
    private FirebaseAuth firebaseAuth;


    private DatabaseReference databaseReference;





    public DAOadded(){

        String eventname;
        firebaseAuth = FirebaseAuth.getInstance();
        String UserId = firebaseAuth.getCurrentUser().getUid();

        FirebaseDatabase db =FirebaseDatabase.getInstance();
        databaseReference = db.getReference(AddedEvent.class.getSimpleName()+"/"+UserId);



/*
        DatabaseReference users = databaseReference.child("AddedEvent");
        //  DatabaseReference receiver = users.child(firebaseUser.getUid());
        final DatabaseReference receiver =
                users.child("IgEUKmhXcwgM64xJpLeAsOaEmuq1");
        receiver.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange ( @NonNull DataSnapshot snapshot ) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    Log.d(TAG, "key count=" + postSnapshot.getKey());

                    for (DataSnapshot sender : postSnapshot.getChildren()) {

                        Log.d(TAG, "sender key count=" + sender.getKey());

                    }
                }
            }

            @Override
            public void onCancelled ( @NonNull DatabaseError error ) {
                Log.e("SHAN " ,error.getMessage());
            }
        });

 */
    }
    public Task<Void> add(AddedEvent ev){

        return databaseReference.push().setValue(ev);

    }

    public Query get(String key){

        if (key == null){
            databaseReference.orderByValue().limitToLast(4);
        }
        return databaseReference.orderByValue().startAt(key).limitToLast(4);
    }


    public Task<Void> update ( String key, HashMap<String, Object> hashMap ) {

       return databaseReference.child(key).updateChildren(hashMap);
    }

}
