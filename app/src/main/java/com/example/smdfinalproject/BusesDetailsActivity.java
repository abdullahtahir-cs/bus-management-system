package com.example.smdfinalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BusesDetailsActivity extends AppCompatActivity {

    RecyclerView recyclerview_buses_details;
    ArrayList<MyBus> MyBusList =new ArrayList<>();
    RecyclerViewAdapter adapter;

    FirebaseDatabase database;
    DatabaseReference reference;

    EditText numberplate, from, to, price;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buses_details);
        database = FirebaseDatabase.getInstance("https://f-0250-sdm-final-default-rtdb.firebaseio.com/");
        reference = database.getReference("MyDatabase");
    }

    @Override
    protected void onStart() {
        super.onStart();
        BusesDetails();
    }

    public void BusesDetails() {
        setContentView(R.layout.buses_details);
        recyclerview_buses_details = findViewById(R.id.recyclerview_buses_Details);
        adapter =new RecyclerViewAdapter(MyBusList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerview_buses_details.setLayoutManager(layoutManager);
        recyclerview_buses_details.setItemAnimator(new DefaultItemAnimator());
        recyclerview_buses_details.setAdapter(adapter);

        reference.addValueEventListener(new ValueEventListener() {
            MyBus obj;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.child("Buses").getChildren())
                {
                    int seats_count=0;
                    String s = dataSnapshot.child("AvailableSeats").getValue().toString();
                    for (int i=0;i<s.length();i++)
                    {
                        if (s.charAt(i) == ',')
                        {
                            seats_count += 1;
                        }
                    }
                    s = String.valueOf(seats_count);
                    obj = new MyBus(dataSnapshot.getKey().toString(), dataSnapshot.child("From").getValue().toString(), dataSnapshot.child("To").getValue().toString(), dataSnapshot.child("Price").getValue().toString(), s);
                    MyBusList.add(obj);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void InsertNewBus(View view) {
        setContentView(R.layout.new_bus);

        numberplate = findViewById(R.id.edtnumberplate);
        from = findViewById(R.id.edtfrom);
        to = findViewById(R.id.edtto);
        price = findViewById(R.id.edtprice);
    }

    public void AddNewBus(View view) {
        String seats = "";

        for (int i = 0; i < 56; i++) {
            seats = seats + Integer.toString(i + 1);
            seats += ',';
        }

        reference.child("Buses").child(numberplate.getText().toString()).child("From").setValue(from.getText().toString());
        reference.child("Buses").child(numberplate.getText().toString()).child("To").setValue(to.getText().toString());
        reference.child("Buses").child(numberplate.getText().toString()).child("Price").setValue(price.getText().toString());
        reference.child("Buses").child(numberplate.getText().toString()).child("AvailableSeats").setValue(seats);
        reference.child("Buses").child(numberplate.getText().toString()).child("AllocatedSeats").setValue("");
    }
}