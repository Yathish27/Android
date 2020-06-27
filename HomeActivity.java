package com.example.security;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


public class HomeActivity extends AppCompatActivity {
    Button btnLogout;
    Button customButton;
    Switch switchEnableButton;
    Button report;
    Button nearby;
    Button profile;
    Button emergency;
    TextView panic;
    Button save;
    Switch guardian;
    boolean state=false;
    Button alert;
    Button tips;
    Button complain;
    Button Volunteer;
    Button feedback;
    Button help;
    Button contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PackageManager.PERMISSION_GRANTED);

        Button btn = findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchactivity();
            }
        });
        btnLogout = findViewById(R.id.logout);
        customButton = findViewById(R.id.custom_button);
        nearby = findViewById(R.id.nearby);
        switchEnableButton = findViewById(R.id.switch_enable_button);
        report = findViewById(R.id.report);
        profile = findViewById(R.id.profile);
        emergency = findViewById(R.id.emergency);
        alert=findViewById(R.id.alerts);
        panic = findViewById(R.id.panic_text);
        tips=findViewById(R.id.tips);
        complain=findViewById(R.id.complain);
        Volunteer=findViewById(R.id.volunteer);
        feedback=findViewById(R.id.feedback);
        help=findViewById(R.id.help);
        contact=findViewById(R.id.contactus);

        panic.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                save=findViewById(R.id.save);
                guardian =(Switch) findViewById(R.id.sms);
                final AlertDialog.Builder dialog1 = new AlertDialog.Builder(HomeActivity.this);
                dialog1.setCancelable(false);
                dialog1.setCustomTitle(getLayoutInflater().inflate(R.layout.btn_share, null));
                dialog1.setPositiveButton((CharSequence) save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        guardian.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if(isChecked){
                                    state=true;
                                }
                                else
                                    state=false;
                            }
                        });


                    }
                });
                dialog1.setNegativeButton("Back", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                AlertDialog build=dialog1.create();
                build.show();


            }
        });
        emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, EmergencyActivity.class);
                startActivity(i);
            }
        });
        alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(HomeActivity.this,com.example.security.IncomingSms.class);
                startActivity(intent);
            }
        });

        final MediaPlayer siren = MediaPlayer.create(this, R.raw.policesirene);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, profile.class);
                startActivity(intent);
            }
        });
        complain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this,Activity_Complain.class);
                startActivity(intent);
            }
        });
        tips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this,Safetytips.class);
                startActivity(intent);
            }
        });
        Volunteer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(HomeActivity.this,Volunteer.class);
                startActivity(intent);
            }
        });
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(HomeActivity.this,Feedback.class);
                startActivity(intent);
            }
        });
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(HomeActivity.this,Help.class);
                startActivity(intent);
            }
        });
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = "9538198724";
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                startActivity(intent);
            }
        });
        customButton.setOnClickListener(new View.OnClickListener() {

                                            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                            @Override
                                            public void onClick(View v) {


                                                Toast.makeText(HomeActivity.this, "Panic", Toast.LENGTH_SHORT).show();
                                                siren.start();
                                                panic_message();

                                            }
                                        });


        switchEnableButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                            customButton.setEnabled(true);
                            customButton.setOnClickListener(new View.OnClickListener() {
                                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                @Override
                                public void onClick(View v) {

                                    Toast.makeText(HomeActivity.this, "Panic", Toast.LENGTH_SHORT).show();
                                    siren.start();
                                    panic_message();
                                }
                            });
                        } else {
                            customButton.setEnabled(false);
                            if (siren.isPlaying())
                                siren.pause();
                        }
                    }
                });
                btnLogout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseAuth.getInstance().signOut();
                        Intent intToMain = new Intent(HomeActivity.this, LoginActivity.class);
                        startActivity(intToMain);
                    }
                });
                report.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        reportactivity();
                    }
                });
                nearby.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nearbyactivity();
                    }
                });

            }

    public void launchactivity(){
        Intent intent = new Intent( HomeActivity.this, MapsActivity.class );
        startActivity( intent );
    }
    public void reportactivity(){
        Intent intent=new Intent(HomeActivity.this,ReportActivity.class);
        startActivity(intent);
    }
    public void nearbyactivity(){
        Intent intent=new Intent(HomeActivity.this,MapnearbyActivity.class);
        startActivity(intent);
    }
    public String onLocationChanged(Location location) throws IOException {

        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(HomeActivity.this, Locale.getDefault());

        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        String city = addresses.get(0).getLocality();
        String state = addresses.get(0).getAdminArea();
        String country = addresses.get(0).getCountryName();
        String postalCode = addresses.get(0).getPostalCode();
        String knownName = addresses.get(0).getFeatureName();
    return address+city+state+country+postalCode+knownName;
    }

    public void onBackPressed()
    {
        int backButtonCount = 0;
        if(backButtonCount >= 1)
        {
            finish();
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);


        }
        else
        {
            Toast.makeText(HomeActivity.this, "Press the back button once again to close the application.", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void panic_message () {
        final LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, true);
        Location location = locationManager.getLastKnownLocation(bestProvider);


        assert location != null;
        String message = null;

        try {
            message = "Greetings Security Helpline\n" + "My current address: " + onLocationChanged(location) + "Urgent Help Required\n" + "(auto-generated)";
        } catch (IOException e) {
            e.printStackTrace();
        }
        final String finalMessage = message;
        FirebaseDatabase.getInstance().getReference("Users")
                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String gphone = dataSnapshot.child("guardian").getValue().toString();
                if(state) {
                    Intent smsIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("sms", gphone, null));
                    smsIntent.putExtra("sms_body", finalMessage);
                    startActivity(smsIntent);
                    Toast.makeText(HomeActivity.this, "SMS sent", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}



