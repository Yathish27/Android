package com.example.security;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Help extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        TextView content=findViewById(R.id.contentPanel);
        content.setText("1.What if there is a registration error??\n"+"Developer : There might be wrong in the details or network error or maybe internal error due to server down\n\n"
        +"2.What to do if there Login error??\n"+"Developer :Try to remember the password correctly if you forget password you can reset through mail but have write emailId correctly\n\n" +
                "3.What does Panic button do and how to make it work??\n"+"Developer : Panic alert is used in case of protection from offence and can be accessed by allowed required permission by clicking Create your panic button\n\n"
        +"4.How to send Emergency SMS to police or family or others??\n "+"Developer : You send your location by clicking Location sharing button and set your location and address and click send SMS button then you have various option to send.\n\n"
        +"5.Is there WhatsApp sharing of location?\n"+"Developer : Yes,Of course in Location sharing button you share through WhatsApp\n\n"+
                "6.How to report crime through vidoes or audio??\n"+"Developer : By clicking Report crime button and capturing video or recording the audio then by uploading the file you get link generated with video or audio which can be shared to helpline numbers\n\n"
        +"7.How get details of NGOs??\n"+"Developer : By clicking NGOs button available in nearby button which also contains nearby Emergency places\n\n"
        +"8.What to do after becoming Volunteer??\n"+"You will be notified to us and will be added to volunteering club list then you will receive alert message in case someone requires help in your Region\n\n" +
                "Further questions write to us in feedback \n or can mail at any of these\n yathishnv27@gmail.com\nprashantab8505@gmail.com\nbhasha7337893669@gmail.com\n\nThank You");

    }
}
