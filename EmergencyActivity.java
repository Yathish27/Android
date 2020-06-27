package com.example.security;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class EmergencyActivity extends AppCompatActivity {
    Button volunteer;
    Button covid;
    String phone;
    Button kar;
    Button police;
    Button fire;
    Button amb;
    Button acc;
    Button rescue;
    Button blood;
    Button women;
    Button pc;
    Button support;
    Button ngo1;
    Button ngo2;
    Button ngo3;
    Button ngo4;
    Button ngo5;
    Button ngo6;
    Button ngo7;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);
        covid=findViewById(R.id.covid);
        covid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone="tel:01123978046";
                alert();
            }
        });
        kar=findViewById(R.id.kar);
        kar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone="tel:08046848600";
                alert();
            }
        });
        police=findViewById(R.id.police);
        police.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone="tel:100";
                alert();
            }
        });
        fire=findViewById(R.id.fire);
        fire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone="tel:101";
                alert();

            }
        });
        amb=findViewById(R.id.ambulance);
        amb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone="tel:108";
                alert();

            }
        });
        acc=findViewById(R.id.accident);
        acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone="tel:103";
                alert();

            }
        });
        rescue=findViewById(R.id.rescue);
        rescue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone="tel:104";
                alert();

            }
        });
        blood=findViewById(R.id.blood);
        blood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone="tel:23347714";
                alert();

            }
        });
        women=findViewById(R.id.women);
        women.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone="tel:08022943225";
                alert();

            }
        });
        support=findViewById(R.id.support);
        support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone="tel:9535377158";
                alert();

            }
        });
        volunteer = findViewById(R.id.volunteer);
        volunteer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                phone = "tel:9341748698";
                alert();
            }
        });
        ngo1 = findViewById(R.id.ngo1);
        ngo1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                phone = "tel:9590099992";
                alert();
            }
        });
        ngo2 = findViewById(R.id.ngo2);
        ngo2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                phone = "tel:08022389009";
                alert();
            }
        });
        ngo3 = findViewById(R.id.ngo3);
        ngo3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                phone = "tel:01143239200";
                alert();
            }
        });
        ngo4 = findViewById(R.id.ngo4);
        ngo4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                phone = "tel:08105630067";
                alert();
            }
        });
        ngo5 = findViewById(R.id.ngo5);
        ngo5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                phone = "tel:08022862622";
                alert();
            }
        });
        ngo6 = findViewById(R.id.ngo6);
        ngo6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                phone = "tel:08023385874";
                alert();
            }
        });
        ngo7 = findViewById(R.id.ngo7);
        ngo7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                phone = "tel:08022862622";
                alert();
            }
        });


    }
    public void alert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(EmergencyActivity.this);
        builder.setTitle("Call Alert").setMessage("Are you sure, you want to call?").setCancelable(false).setPositiveButton("YES", new
                DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(EmergencyActivity.this, "Call processed",
                                Toast.LENGTH_SHORT).show();
                                call();
                    }
                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(EmergencyActivity.this, "Call cancelled", Toast.LENGTH_SHORT).show();

                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void call()
    {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse(phone));

                if (ActivityCompat.checkSelfPermission(EmergencyActivity.this,
    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
        return;
    }
    startActivity(callIntent);
}

}
