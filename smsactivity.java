package com.example.security;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class smsactivity extends Activity {
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;
    Button sendBtn;
    EditText txtphoneNo;
    EditText txtMessage;
    String phoneNo;
    String message;
    Button send;
    DatabaseReference getref;
    Button gurdian;
    Button police;
    Button medical;
    String gphone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smsactivity);
        send=findViewById(R.id.btnSendwa);
        sendBtn =  findViewById(R.id.btnSendSMS);
        txtphoneNo = findViewById(R.id.editText);
        txtMessage = findViewById(R.id.editText2);
        gurdian=findViewById(R.id.sendguardian);
        police=findViewById(R.id.sendpolice);
        medical=findViewById(R.id.sendmedical);
        Intent intent=getIntent();
        String str = intent.getStringExtra("location");
        txtMessage.setText("Greetings Security Helpline\n"+"My current address:"+str+"\n"+"Emergency!!Urgent Help required!!"+"\n"+"(auto-genrated)");
        sendBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sendSMSMessage();
            }
        });
        police.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message = txtMessage.getText().toString();
                AlertDialog.Builder builder = new AlertDialog.Builder(smsactivity.this);
                builder.setTitle("SMS Alert").setMessage("Are you sure, you want to send message to city-police?").setCancelable(false).setPositiveButton("YES", new
                        DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(smsactivity.this, "SMS processed",
                                        Toast.LENGTH_LONG).show();
                                Intent smsIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("sms","9480801000" ,null));
                                smsIntent.putExtra("sms_body", message);
                                startActivity(smsIntent);
                            }
                        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(smsactivity.this, "SMS cancelled", Toast.LENGTH_SHORT).show();

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
        medical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message = txtMessage.getText().toString();
                AlertDialog.Builder builder = new AlertDialog.Builder(smsactivity.this);
                builder.setTitle("SMS Alert").setMessage("Are you sure, you want to send message to city-police?").setCancelable(false).setPositiveButton("YES", new
                        DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(smsactivity.this, "SMS processed",
                                        Toast.LENGTH_LONG).show();
                                Intent smsIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("sms", "9013151515",null));
                                smsIntent.putExtra("sms_body", message);
                                startActivity(smsIntent);
                            }
                        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(smsactivity.this, "SMS cancelled", Toast.LENGTH_SHORT).show();

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
        gurdian.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference("Users")
                        .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        message = txtMessage.getText().toString();
                        gphone=dataSnapshot.child("guardian").getValue().toString();
                        Intent smsIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("sms", gphone,null));
                        smsIntent.putExtra("sms_body", message);
                        startActivity(smsIntent);
                        Toast.makeText(getApplicationContext(), "SMS sent.",
                                Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PackageManager pm = smsactivity.this.getPackageManager();
                try {
                    Intent waIntent = new Intent(Intent.ACTION_SEND);
                    waIntent.setType("text/plain");
                    String txt= txtMessage.getText().toString();
                    PackageInfo info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
                    waIntent.setPackage("com.whatsapp");
                    waIntent.putExtra(Intent.EXTRA_TEXT, txt);
                    startActivity(Intent.createChooser(waIntent, "Share with"));
                } catch (PackageManager.NameNotFoundException e) {
                    Toast.makeText(smsactivity.this, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
    }

    protected void sendSMSMessage() {
        phoneNo = txtphoneNo.getText().toString();
        message = txtMessage.getText().toString();



        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS);}

            else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);

            }
        }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_SEND_SMS) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if(phoneNo.isEmpty()){txtphoneNo.setError("Please enter phone number ");
                txtphoneNo.requestFocus();}
                else if(phoneNo.length()!=10){txtphoneNo.setError("Please enter valid phone number ");
                    txtphoneNo.requestFocus();}
                else{
                    Intent smsIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("sms", phoneNo,null));
                    smsIntent.putExtra("sms_body", message);
                    startActivity(smsIntent);
                Toast.makeText(getApplicationContext(), "SMS sent.",
                        Toast.LENGTH_LONG).show();}
            } else {
                Toast.makeText(getApplicationContext(),
                        "SMS failed, please try again.", Toast.LENGTH_LONG).show();

            }
        }

    }
}