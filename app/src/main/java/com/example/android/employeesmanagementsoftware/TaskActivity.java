package com.example.android.employeesmanagementsoftware;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;


public class TaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);


        Bundle bundle= getIntent().getBundleExtra("bundle");
        String title =bundle.getString("title");
        String date =bundle.getString("date");
        String trainer =bundle.getString("trainer");
        String description =bundle.getString("description");
        String deadline =bundle.getString("deadline");
        TextView titletext= (TextView) findViewById(R.id.tasktitle);
        TextView datetext = (TextView) findViewById(R.id.taskdate);
        TextView trainertext = (TextView) findViewById(R.id.tasktrainer);
        TextView descriptiontext = (TextView) findViewById(R.id.taskdesc);
        TextView deadlinetext =  (TextView)findViewById(R.id.deadline);
        titletext.setText(title);
        datetext.setText("Start Date: "+date);
        trainertext.setText(trainer);
        descriptiontext.setText(description);
        deadlinetext.setText("Deadline: "+deadline);

}
}
