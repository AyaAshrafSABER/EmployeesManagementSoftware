package com.example.android.employeesmanagementsoftware;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.employeesmanagementsoftware.data.DBHelpers.EmployeesManagementDbHelper;
import com.example.android.employeesmanagementsoftware.data.Contracts.TaskContract;
import com.example.android.employeesmanagementsoftware.data.Contracts.TaskContract.TaskEntry;


public class TaskActivity extends AppCompatActivity {


    EmployeesManagementDbHelper emdb = new EmployeesManagementDbHelper(this);
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

public Cursor getTasksCursor(){
        SQLiteDatabase db  = emdb.getReadableDatabase(); //get readable instance of the db

    //specify the columns to be read
        String [] columns = {
                TaskEntry.COLUMN_TASK_NAME,
                TaskEntry.COLUMN_TASK_DESCRIPTION,
                TaskEntry.COLUMN_TASK_EVALUATION,
                TaskEntry.COLUMN_TASK_DEADLINE
        };

    //cursor is a table containing the rows returned form the query
        Cursor cursor =  db.query(TaskContract.TABLE_NAME,columns,null,null,null,null,null);

        return cursor;

    }

    public Cursor getTasksCursor(int task_id){

        SQLiteDatabase db  = emdb.getReadableDatabase(); //get readable instance of the db

        //specify the columns to be read
        String [] columns = {
                TaskEntry.COLUMN_TASK_NAME,
                TaskEntry.COLUMN_TASK_DESCRIPTION,
                TaskEntry.COLUMN_TASK_EVALUATION,
                TaskEntry.COLUMN_TASK_DEADLINE
        };

        String selection

        //cursor is a table containing the rows returned form the query
        Cursor cursor =  db.query(TaskContract.TABLE_NAME,columns,null,null,null,null,null);

        return cursor;

    }

}
