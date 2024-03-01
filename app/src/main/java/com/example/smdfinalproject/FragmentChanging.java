package com.example.smdfinalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FragmentChanging extends AppCompatActivity {

    FragmentManager FM = getSupportFragmentManager();
    FragmentTransaction FT = FM.beginTransaction();
    LoginAdminFragment adminFragment = new LoginAdminFragment();
    ClientFragment clientFragment = new ClientFragment();

    FirebaseDatabase database;
    DatabaseReference reference;

    EditText Usernameadmin, Passwordadmin;

    String key = "";

    EditText User, Pass,regname,regusername,regpassword,regemail,regcnic,regphoneno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_fragment_changing);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        key = intent.getStringExtra("key");
        if (key.equals("admin"))
        {
            FT.replace(android.R.id.content, adminFragment);
        }
        else
        {
            FT.replace(android.R.id.content, clientFragment);
        }
        FT.commit();
    }

    public void SignInAdmin(View view) {
        database = FirebaseDatabase.getInstance("https://f-0250-sdm-final-default-rtdb.firebaseio.com/");
        reference = database.getReference("MyDatabase");

        Usernameadmin = findViewById(R.id.edttxtusernameadmin);
        Passwordadmin = findViewById(R.id.edttxtpasswordadmin);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String username = snapshot.child("Admin").child("username").getValue().toString();
                String password = snapshot.child("Admin").child("password").getValue().toString();
                if (username.equals(Usernameadmin.getText().toString()) && password.equals(Passwordadmin.getText().toString()))
                {
                    //Toast.makeText(FragmentChanging.this, "Admin Logged In Successfully", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(FragmentChanging.this, AdminActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(FragmentChanging.this, "Username or Password is Incorrect", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void signinclient(View view) {

        database = FirebaseDatabase.getInstance("https://f-0250-sdm-final-default-rtdb.firebaseio.com/");
        reference = database.getReference("MyDatabase");

        User=findViewById(R.id.usernameedittext);
        Pass=findViewById(R.id.passwordedittext2);


        reference.addValueEventListener(new ValueEventListener() {

            int[] check_username_existence = {0};
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                if (datasnapshot.child("Clients").hasChild(User.getText().toString()))
                {
                    Log.d("TAG", User.getText().toString());
                    String password = datasnapshot.child("Clients").child(User.getText().toString()).child("Password").getValue().toString();
                    Log.d("TAG", Pass.getText().toString());
                    if (password.equals(Pass.getText().toString())) {
                        Intent intent=new Intent(FragmentChanging.this, ClientMainActivity.class);
                        startActivity(intent);

                    }
                    else {
                        Toast.makeText(FragmentChanging.this, "Password is Incorrect", Toast.LENGTH_LONG).show();
                    }
                    check_username_existence[0] = 1;
                }
                if (check_username_existence[0] != 1)
                {
                    Toast.makeText(FragmentChanging.this, "Username is Incorrect", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }


    @SuppressLint("MissingInflatedId")
    public void register(View view) {
        setContentView(R.layout.registration);

    }


    public void RegisterClient(View view) {
        database = FirebaseDatabase.getInstance("https://f-0250-sdm-final-default-rtdb.firebaseio.com/");
        reference = database.getReference("MyDatabase");

        regname=findViewById(R.id.edittextname_admin);
        regusername=findViewById(R.id.edittextusername_admin);
        regpassword=findViewById(R.id.edittextpassword_admin);
        regemail=findViewById(R.id.edittextemail_admin);
        regcnic=findViewById(R.id.edittextcnic_admin);
        regphoneno=findViewById(R.id.edittextphone_admin);


        int[] check_username_existence = {0};

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                if (datasnapshot.child("Clients").hasChild(regusername.getText().toString()))
                {
                    Toast.makeText(FragmentChanging.this, "Client with this username already exists", Toast.LENGTH_LONG).show();
                    check_username_existence[0] = 1;
                }
                if (check_username_existence[0] != 1)
                {
                    reference.child("Clients").child(regusername.getText().toString()).child("Name").setValue(regname.getText().toString());
                    reference.child("Clients").child(regusername.getText().toString()).child("CNIC").setValue(regcnic.getText().toString());
                    reference.child("Clients").child(regusername.getText().toString()).child("Phone").setValue(regphoneno.getText().toString());
                    reference.child("Clients").child(regusername.getText().toString()).child("Email").setValue(regemail.getText().toString());
                    reference.child("Clients").child(regusername.getText().toString()).child("Password").setValue(regpassword.getText().toString());
                    Toast.makeText(FragmentChanging.this, "Successfully Registered, Please Login now", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}