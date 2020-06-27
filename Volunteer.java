package com.example.security;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class Volunteer extends AppCompatActivity {
    Switch vol;
    EditText mobile, name;
    Button join;
    String phone, vname;
    TextView display;
    TextView dphone;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer);
        vol = findViewById(R.id.check);
        TextView vc = (TextView) findViewById(R.id.vol);
        mobile = findViewById(R.id.getnumber);
        name = findViewById(R.id.getname);
        join = findViewById(R.id.vb);
        dphone=(TextView)findViewById(R.id.insertphone);
        display = findViewById(R.id.insert);
        vol.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                } else {
                    mobile.setError("Please switch in participation");
                    name.setError("Please switch in participation");
                }
            }
        });
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone = mobile.getText().toString();
                vname = name.getText().toString();
                if (phone.isEmpty()) {
                    mobile.setError("Please enter number to join");
                    mobile.requestFocus();
                }
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Volunteer");
                databaseReference.child("Volunteer-Name").push().setValue(vname);
                databaseReference.child("Contact-Number").push().setValue(phone);
                Toast.makeText(Volunteer.this, "Joined Successfully", Toast.LENGTH_SHORT).show();
            }
        });
        vc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone = mobile.getText().toString();
                vname = name.getText().toString();

                Vuser user = new Vuser(vname,phone );
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Volunteer");
                ref.addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                collectPhoneNumbers((Map<String,Object>) dataSnapshot.getValue());
                                collectName((Map<String,Object>) dataSnapshot.getValue());

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                //handle databaseError
                            }
                        });

            }
        });
    }
    public void collectPhoneNumbers(Map<String,Object> user) {

        ArrayList<Long> phoneNumbers = new ArrayList<>();


        //iterate through each user, ignoring their UID
        for (Map.Entry<String, Object> entry : user.entrySet()) {

            //Get user map
            Map singleUser = (Map) entry.getValue();
            //Get phone field and append to list
            phoneNumbers.add((Long) singleUser.get("Contact-Number"));


        }
        System.out.println(phoneNumbers.toString());
        display.setText( phoneNumbers.toString());

    }
        public void collectName(Map<String,Object> user) {

            ArrayList<Long> names = new ArrayList<>();


            //iterate through each user, ignoring their UID
            for (Map.Entry<String, Object> entry : user.entrySet()){

                //Get user map
                Map singleUser = (Map) entry.getValue();
                //Get phone field and append to list
                names.add((Long) singleUser.get("Volunteer-Name"));

            }
            System.out.println(names.toString());
            dphone.setText( names.toString());



    }
}
