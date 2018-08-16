package com.example.android.employeesmanagementsoftware;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.employeesmanagementsoftware.data.DBHelpers.EmployeesManagementDbHelper;

/*
made by menna
 */
//First you need to show departement and  the employees in this departement who work  in the task
public class TaskActivity extends AppCompatActivity {
    private EmployeesManagementDbHelper employeeDBHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        employeeDBHelper = new EmployeesManagementDbHelper(this);


        Bundle bundle= getIntent().getBundleExtra("bundle");
        Long id = bundle.getLong("id");
        Cursor cursor =employeeDBHelper.getSpecifiTaskCursor(id);

        String title =cursor.getString(1);
        String description =cursor.getString(2);
        String deadline =cursor.getString(3);
        String date =cursor.getString(4);
        String trainer =cursor.getString(5);


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
