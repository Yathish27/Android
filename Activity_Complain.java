package com.example.security;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Activity_Complain extends Activity {
    EditText editTextTo,editTextSubject,editTextMessage;
    Button sendemail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain);

        editTextTo=findViewById(R.id.textemail);
        editTextSubject=findViewById(R.id.editText2);
        editTextMessage=findViewById(R.id.editText3);

        sendemail=findViewById(R.id.button1);

        sendemail.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View arg0) {
                String to=editTextTo.getText().toString();
                String subject=editTextSubject.getText().toString();
                String message=editTextMessage.getText().toString();
                if (to.isEmpty()) {
                    editTextTo.setError("Please enter your email-Id");
                    editTextTo.requestFocus();
                    return;
                }
                if (subject.isEmpty()) {
                    editTextSubject.setError("Please enter your subject");
                    editTextSubject.requestFocus();
                    return;
                }
                else {


                    Intent email = new Intent(Intent.ACTION_SEND);
                    email.putExtra(Intent.EXTRA_EMAIL, new String[]{to});
                    email.putExtra(Intent.EXTRA_SUBJECT, subject);
                    email.putExtra(Intent.EXTRA_TEXT, message);

                    //need this to prompts email client only
                    email.setType("message/rfc822");

                    startActivity(Intent.createChooser(email, "Choose an Email App :"));
                }
            }

        });
    }

   }

