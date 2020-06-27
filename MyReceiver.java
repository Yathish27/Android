package com.example.security;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

public class MyReceiver extends BroadcastReceiver {
    private static final String SMS_RECEIVED="android.provider.Telephony.SMS_RECEIVED";
    private static final String TAG="SmsBroadcastReceiver";
    String msg,phoneNo="";


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG,"Intent Received: "+intent.getAction());
        if(intent.getAction()==SMS_RECEIVED)
        {
            Bundle dataBundle=intent.getExtras();
            if(dataBundle!=null)
            {
                Object [] mypdu=(Object[])dataBundle.get("pdus");
                final SmsMessage[] message=new SmsMessage[mypdu.length];
                for (int i=0;i<mypdu.length;i++)
                {
                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
                    {
                        String format=dataBundle.getString("format");
                        message[i]=SmsMessage.createFromPdu((byte[])mypdu[i],format);

                    }
                    else {
                        message[i]=SmsMessage.createFromPdu((byte[])mypdu[i]);

                    }
                    msg=message[i].getMessageBody();
                    phoneNo=message[i].getOriginatingAddress();

                }

                Toast.makeText(context,"Message : "+msg+'\n'+"Number: "+phoneNo,Toast.LENGTH_LONG).show();
                Intent smsIntent=new Intent(context,IncomingSms.class);
                smsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                smsIntent.putExtra("MessagePhoneNumber",phoneNo);
                smsIntent.putExtra("Message",msg);
                context.startActivity(smsIntent);



            }
        }
    }
}
