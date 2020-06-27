package com.example.security;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class ReportActivity extends Activity {
    FirebaseStorage storage;
    FirebaseDatabase database;
    ProgressDialog progressDialog;
    Button selectFile, upload;
    TextView notification;
    Button captureVideoButton;

    Uri videouri;
    Button sendfile;
    String url;
    private Button mRecordBtn;
    private TextView mRecordLabel;
    private String mFileName = null;
    private String FileName = null;
    private static final String LOG_TAG = "Record_log";
    private StorageReference mStorage;
    private MediaRecorder mediaRecorder;
    public static int VIDEO_CAPTURED = 1;
    public static final int RECORD_AUDIO = 0;


    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();
        selectFile = findViewById(R.id.select);
        upload = findViewById(R.id.upload);
        notification = findViewById(R.id.file);
        sendfile = findViewById(R.id.sendfile);
        sendfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send();
            }
        });
        selectFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(ReportActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    select();

                } else
                    ActivityCompat.requestPermissions(ReportActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 9);

            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (videouri != null)
                    uploadFile(videouri);
                else
                    Toast.makeText(ReportActivity.this, "select a file", Toast.LENGTH_SHORT).show();

            }


            private void uploadFile(Uri videouri) {
                progressDialog = new ProgressDialog(ReportActivity.this);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setTitle("Uploading file..");
                progressDialog.setProgress(0);
                progressDialog.show();
                final String file = System.currentTimeMillis() + "";
                final StorageReference storageReference = storage.getReference();
                storageReference.child("crimevideo.com").child(file).putFile(videouri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                    @Override
                    public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                        result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                url = uri.toString();
                            }
                        });
                        DatabaseReference reference = database.getReference();
                        reference.child("Crime videos").child(file).setValue(url)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {


                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(ReportActivity.this, "File successfully uploaded..", Toast.LENGTH_SHORT).show();
                                        } else
                                            Toast.makeText(ReportActivity.this, "File not successfully uploaded..", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                        int currentProgress = (int) (100 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                        progressDialog.setProgress(currentProgress);
                    }
                });

            }

                                  });
        mRecordBtn = findViewById(R.id.audio);
        mRecordLabel = findViewById(R.id.textaudio);
        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        FileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        FileName += "/recorded_crime.mp4";
        mFileName += "/recorded_audio.3gp";
        mRecordBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (ActivityCompat.checkSelfPermission(ReportActivity.this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(ReportActivity.this, new String[]{Manifest.permission.RECORD_AUDIO},
                                RECORD_AUDIO);

                    } else {
                        startRecording();
                        mRecordLabel.setText("Recording Started..");
                    }

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    stopRecording();
                    mRecordLabel.setText("Recording Stopped...");
                }
                return false;
            }
        });

        TextView t2 = findViewById(R.id.text2);
        t2.setMovementMethod(LinkMovementMethod.getInstance());
        captureVideoButton = findViewById(R.id.CaptureVideoButton);

        captureVideoButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent captureVideoIntent = new Intent(android.provider.MediaStore.ACTION_VIDEO_CAPTURE);
                startActivityForResult(captureVideoIntent, VIDEO_CAPTURED);
            }
        });



    }

    public void onRequestPermisssionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 9 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            select();
        } else
            Toast.makeText(ReportActivity.this, "please provide permission.", Toast.LENGTH_SHORT).show();

    }

    private void select() {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 86);

    }


    private void startRecording() {
        try {
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setOutputFile(mFileName);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (IllegalStateException ise){
        }
        catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

    }

    private void stopRecording() {
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder=null;
        Toast.makeText(getApplicationContext(),"Recorded audio",Toast.LENGTH_LONG).show();
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 86 && resultCode == RESULT_OK && data != null) {
            videouri = data.getData();
            notification.setText("A file is selected:" + data.getData().getLastPathSegment());
        } else
            Toast.makeText(ReportActivity.this, "please select a file", Toast.LENGTH_SHORT).show();
    }
    public void send(){
        Intent intent = new Intent(ReportActivity.this,sms.class);
        intent.putExtra("video", url);
        startActivity(intent);
    }

}
