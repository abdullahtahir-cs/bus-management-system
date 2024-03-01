package com.example.smdfinalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {

    EditText Nameedt, CNICedt, Phoneedt, Emailedt, Passwordedt, Usernameedt;
    TextView Nametxt, CNICtxt, Phonetxt, Emailtxt, Passwordtxt, Usernametxt;
    FirebaseDatabase database;
    DatabaseReference reference;
    int count = 1;
    Button del;
    String layout = "";
    RecyclerView recyclerview_buses_details;
    ArrayList<MyBus> MyBusList =new ArrayList<>();
    RecyclerViewAdapter adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        layout = "activity_admin";

        database = FirebaseDatabase.getInstance("https://f-0250-sdm-final-default-rtdb.firebaseio.com/");
        reference = database.getReference("MyDatabase");

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        if (layout.equals("show_clients") || layout.equals("register_client_byadmin") || layout.equals("update_delete_clients"))
        {
            layout = "accounts_handling";
            setContentView(R.layout.accounts_handling);
            onStart();
        }
        else if (layout.equals("new_bus"))
        {
            layout = "activity_admin";
            setContentView(R.layout.activity_admin);
            onStart();
        }
        else if (layout.equals("accounts_handling") || layout.equals("buses_details"))
        {
            layout = "activity_admin";
            setContentView(R.layout.activity_admin);
            onStart();
        }
        else
        {
            super.onBackPressed();
        }
    }

    public void AccountsHandling(View view) {
        setContentView(R.layout.accounts_handling);
        layout = "accounts_handling";
    }

    public void RegisterClient(View view) {
        setContentView(R.layout.register_client_byadmin);
        layout = "register_client_byadmin";
        BindFunction();
    }

    @SuppressLint({"MissingInflatedId", "CutPasteId"})
    public void UpdateDeleteClient(View view) {
        count = 1;

        layout = "update_delete_clients";
        setContentView(R.layout.update_delete_clients);
        BindFunction();
        del = findViewById(R.id.button5);

        Nameedt.setVisibility(View.INVISIBLE);
        CNICedt.setVisibility(View.INVISIBLE);
        Phoneedt.setVisibility(View.INVISIBLE);
        Emailedt.setVisibility(View.INVISIBLE);
        Passwordedt.setVisibility(View.INVISIBLE);

        Nametxt.setVisibility(View.INVISIBLE);
        CNICtxt.setVisibility(View.INVISIBLE);
        Phonetxt.setVisibility(View.INVISIBLE);
        Emailtxt.setVisibility(View.INVISIBLE);
        Passwordtxt.setVisibility(View.INVISIBLE);

    }

    public void UpdateClient(View view) {
        int[] check_username_existence = {0};

        if (count == 1) // means update button pressed once
        {
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                    for (DataSnapshot snapshot: datasnapshot.getChildren())
                    {
                        if (!(Usernameedt.getText().toString().equals("")))
                        {
                            if (datasnapshot.child("Clients").hasChild(Usernameedt.getText().toString())) {
                                check_username_existence[0] = 1;
                                Nameedt.setVisibility(View.VISIBLE);
                                CNICedt.setVisibility(View.VISIBLE);
                                Phoneedt.setVisibility(View.VISIBLE);
                                Emailedt.setVisibility(View.VISIBLE);
                                Passwordedt.setVisibility(View.VISIBLE);
                                Usernameedt.setEnabled(false);

                                Nametxt.setVisibility(View.VISIBLE);
                                CNICtxt.setVisibility(View.VISIBLE);
                                Phonetxt.setVisibility(View.VISIBLE);
                                Emailtxt.setVisibility(View.VISIBLE);
                                Passwordtxt.setVisibility(View.VISIBLE);
                                Usernameedt.setEnabled(false);
                                del.setEnabled(false);

                                Nameedt.setText(datasnapshot.child("Clients").child(Usernameedt.getText().toString()).child("Name").getValue().toString());
                                CNICedt.setText(datasnapshot.child("Clients").child(Usernameedt.getText().toString()).child("CNIC").getValue().toString());
                                Phoneedt.setText(datasnapshot.child("Clients").child(Usernameedt.getText().toString()).child("Phone").getValue().toString());
                                Emailedt.setText(datasnapshot.child("Clients").child(Usernameedt.getText().toString()).child("Email").getValue().toString());
                                Passwordedt.setText(datasnapshot.child("Clients").child(Usernameedt.getText().toString()).child("Password").getValue().toString());
                                break;
                            }
                        }
                    }
                    if (check_username_existence[0] != 1)
                    {
                        Toast.makeText(AdminActivity.this, "Client with this username does not exist", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        count++;
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        else if (count == 2)  // means update button pressed second time
        {
            reference.child("Clients").child(Usernameedt.getText().toString()).child("Name").setValue(Nameedt.getText().toString());
            reference.child("Clients").child(Usernameedt.getText().toString()).child("CNIC").setValue(CNICedt.getText().toString());
            reference.child("Clients").child(Usernameedt.getText().toString()).child("Phone").setValue(Phoneedt.getText().toString());
            reference.child("Clients").child(Usernameedt.getText().toString()).child("Email").setValue(Emailedt.getText().toString());
            reference.child("Clients").child(Usernameedt.getText().toString()).child("Password").setValue(Passwordedt.getText().toString());
        }
    }

    public void DeleteClient(View view) {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for (DataSnapshot snapshot: datasnapshot.getChildren())
                {
                    if (!(Usernameedt.getText().toString().equals("")))
                    {
                        if (datasnapshot.child("Clients").hasChild(Usernameedt.getText().toString())) {
                            datasnapshot.child("Clients").child(Usernameedt.getText().toString()).getRef().removeValue();
                            break;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void RegisterNewClient(View view) {
        int[] check_username_existence = {0};

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for (DataSnapshot snapshot: datasnapshot.getChildren())
                {
                    if (datasnapshot.child("Clients").hasChild(Usernameedt.getText().toString()))
                    {
                        Toast.makeText(AdminActivity.this, "Client with this username already exists", Toast.LENGTH_SHORT).show();
                        check_username_existence[0] = 1;
                        break;
                    }
                }
                if (check_username_existence[0] != 1)
                {
                    reference.child("Clients").child(Usernameedt.getText().toString()).child("Name").setValue(Nameedt.getText().toString());
                    reference.child("Clients").child(Usernameedt.getText().toString()).child("CNIC").setValue(CNICedt.getText().toString());
                    reference.child("Clients").child(Usernameedt.getText().toString()).child("Phone").setValue(Phoneedt.getText().toString());
                    reference.child("Clients").child(Usernameedt.getText().toString()).child("Email").setValue(Emailedt.getText().toString());
                    reference.child("Clients").child(Usernameedt.getText().toString()).child("Password").setValue(Passwordedt.getText().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void BindFunction()
    {
        Nameedt = findViewById(R.id.edittextnumberplate);
        CNICedt = findViewById(R.id.edittextto);
        Phoneedt = findViewById(R.id.edittextprice);
        Emailedt = findViewById(R.id.edittextemail_admin);
        Passwordedt = findViewById(R.id.edittextpassword_admin);
        Usernameedt = findViewById(R.id.edittextfrom);

        Nametxt = findViewById(R.id.textviewname_admin);
        CNICtxt = findViewById(R.id.textviewcnic_admin);
        Phonetxt = findViewById(R.id.textviewphone_admin);
        Emailtxt = findViewById(R.id.textviewemail_admin);
        Passwordtxt = findViewById(R.id.textviewpassword_admin);
        Usernametxt = findViewById(R.id.textviewusername_admin);
    }

    public void BusesDetails(View view)
    {
        Intent intent = new Intent(this, BusesDetailsActivity.class);
        startActivity(intent);
    }
}