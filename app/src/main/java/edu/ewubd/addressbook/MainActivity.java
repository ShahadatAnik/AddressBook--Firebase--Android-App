package edu.ewubd.addressbook;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button createNotes, exit;
    ArrayList<AddressList> arrayList;
    CustomAdapterAddress CustomAdapterAddress;
    private ListView showNotes;
    boolean doubleBackToExitPressedOnce = false;

    DatabaseReference myDB = FirebaseDatabase.getInstance().getReference("address");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        exit = findViewById(R.id.btn_exit);
        createNotes = findViewById(R.id.btn_create);
        showNotes = findViewById(R.id.tv_listview);

        exit.setOnClickListener(v-> finishAffinity());
        createNotes.setOnClickListener(v-> createAddress());

    }

    void createAddress(){
        Intent intent = new Intent(this, CreateAddress.class);
        startActivity(intent);
    }

    private interface FirebaseCallback{
        void onCallback(ArrayList<AddressList> arrayList);
    }

    private void getAddressesFromFB(FirebaseCallback fbc){
        myDB.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        ArrayList<AddressList> arrayList = new ArrayList<>();
                        for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                            AddressList al = new AddressList(
                                    String.valueOf(dsp.child("id").getValue()),
                                    String.valueOf(dsp.child("username").getValue()),
                                    String.valueOf(dsp.child("email").getValue()),
                                    String.valueOf(dsp.child("firstAddress").getValue()),
                                    String.valueOf(dsp.child("secondAddress").getValue()),
                                    String.valueOf(dsp.child("phone").getValue())
                            );
                            arrayList.add(al);
                        }
                        fbc.onCallback(arrayList);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d(TAG, databaseError.getMessage());
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        getAddressesFromFB(arrayList1 -> {
            arrayList = arrayList1;
            CustomAdapterAddress = new CustomAdapterAddress(getApplicationContext(),arrayList);
            showNotes.setAdapter(CustomAdapterAddress);
            CustomAdapterAddress.notifyDataSetChanged();
        });
    }


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}