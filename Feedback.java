package com.example.security;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Feedback extends AppCompatActivity {
    RatingBar ratingBar;

    private TextView tvRateCount, tvRateMessage;

    private float ratedValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        ratingBar =findViewById(R.id.ratingBar);

        tvRateCount = findViewById(R.id.tvRateCount);

        tvRateMessage = findViewById(R.id.tvRateMessage);
        Button submit=findViewById(R.id.submit);
        final EditText response=findViewById(R.id.response);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override

            public void onRatingChanged(RatingBar ratingBar, float rating,

                                        boolean fromUser) {

                ratedValue = ratingBar.getRating();

                tvRateCount.setText("Your Rating : " + ratedValue + "/5.");

                if (ratedValue < 1) {

                    tvRateMessage.setText("Very Bad,Need to improve");

                } else if (ratedValue < 2) {

                    tvRateMessage.setText("Ok.");

                } else if (ratedValue < 3) {

                    tvRateMessage.setText("Not bad.");

                } else if (ratedValue < 4) {

                    tvRateMessage.setText("Nice");

                } else if (ratedValue < 5) {

                    tvRateMessage.setText("Very Nice");

                } else if (ratedValue == 5) {

                    tvRateMessage.setText("Great work!!");
                }
            }

        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subject=tvRateCount.getText().toString();
                String message=response.getText().toString();
                if (subject.isEmpty()) {
                    tvRateCount.setError("Please rate us");
                    tvRateCount.requestFocus();
                    return;
                }
                if (subject.isEmpty()) {
                    response.setError("Please enter your feedback");
                    response.requestFocus();
                    return;
                }
                else {
                    Intent email = new Intent(Intent.ACTION_SEND);
                    email.putExtra(Intent.EXTRA_EMAIL, "yathishnv27@gmail.com");
                    email.putExtra(Intent.EXTRA_SUBJECT, subject);
                    email.putExtra(Intent.EXTRA_TEXT, message);
                    email.setType("message/rfc822");
                    startActivity(Intent.createChooser(email, "Choose an Email App :"));
                }
            }
        });
    }

}



