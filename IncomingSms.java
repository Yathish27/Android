package com.example.security;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class IncomingSms extends AppCompatActivity {
    private static final int MY_PERMISSION_REQUEST_RECEIVE_SMS=0;
    public TextView Smsreceive;
    Button msg;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incoming_sms);
        Smsreceive=findViewById(R.id.smsBody);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECEIVE_SMS)) {


            }
            else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, MY_PERMISSION_REQUEST_RECEIVE_SMS);

            }
        }
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String address = extras.getString("MessagePhoneNumber");
            String message = extras.getString("Message");
            TextView addressField = (TextView) findViewById(R.id.address);
            TextView messageField = (TextView) findViewById(R.id.smsBody);
            addressField.setText("Message From : " +address);
            messageField.setText("Messsage : "+message);
            Intent smsIntent = new Intent( getApplicationContext(),IncomingSms.class );
            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 1,smsIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            Notification notification = new
                    Notification.Builder(getApplicationContext())
                    .setContentTitle( "New Unread Message\n"+address )
                    .setContentText( "You have an unread message :"+message )
                    .setColor( Color.BLUE )
                    .setContentIntent( pendingIntent )
                    .setSmallIcon( android.R.drawable. sym_action_chat )
                    .build();
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService( NOTIFICATION_SERVICE );
            assert notificationManager != null;
            notificationManager.notify( 1,notification);



        }
        msg = (Button)findViewById(R.id.btn_msg);
        msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), IncomingSms.class);
                startActivity(intent);
            }

    });
    }

    public  void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case MY_PERMISSION_REQUEST_RECEIVE_SMS: {
                if(requestCode>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(this,"Permission Granted",Toast.LENGTH_SHORT).show();
                }
                else
                Toast.makeText(IncomingSms.this, "please provide permission.", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void onBackPressed()
    {
            Intent intent = new Intent(IncomingSms.this,HomeActivity.class);
            startActivity(intent);
        }

}

