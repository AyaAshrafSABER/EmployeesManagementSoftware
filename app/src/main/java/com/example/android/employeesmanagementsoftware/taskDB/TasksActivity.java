package com.example.android.employeesmanagementsoftware.taskDB;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.android.employeesmanagementsoftware.R;
import com.example.android.employeesmanagementsoftware.data.DBHelpers.EmployeesManagementDbHelper;
import com.example.android.employeesmanagementsoftware.taskDB.Tasks;
import com.example.android.employeesmanagementsoftware.taskDB.TasksAdapter;
import com.example.android.employeesmanagementsoftware.data.Contracts.TaskContract.TaskEntry;


import java.util.ArrayList;

public class TasksActivity extends AppCompatActivity {
    private EmployeesManagementDbHelper employeeDBHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        employeeDBHelper = new EmployeesManagementDbHelper(this);
        Log.d("taks", "onCreate: Hhahahahahahahah");
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.eventlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<Tasks> tasks = new ArrayList<Tasks>();
     /*   Cursor cursor =employeeDBHelper.getAllTasksCursor();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Tasks task = new Tasks();
                task.setId(cursor.getString(cursor.getColumnIndex(TaskEntry._ID)));
                task.setTaskName(cursor.getString(cursor.getColumnIndex(TaskEntry.COLUMN_TASK_NAME)));
                task.setTaskDetails(cursor.getString(cursor.getColumnIndex(TaskEntry.COLUMN_TASK_DESCRIPTION)));
                task.setTaskDeadline(cursor.getString(cursor.getColumnIndex(TaskEntry.COLUMN_TASK_DEADLINE)));
                task.setTaskDate(cursor.getString(cursor.getColumnIndex(TaskEntry.COLUMN_TASK_DATE)));
                task.setTaskInstractor(cursor.getString(cursor.getColumnIndex(TaskEntry.COLUMN_TASK_INSTRUCTOR)));
                tasks.add(task);
                cursor.moveToNext();}
        }
        cursor.close();*/
//        for(int i=0;i<10;i++)
//        {Tasks task = new Tasks();
//            task.setId((Long.valueOf(i)));
//            task.setTaskDate((i+1)+"/10/2018");
//            task.setTaskDetails("Our job description directory contains job description examples covering all the most popular roles. We have examples of job descriptions you can quickly download and modify to suit your unique business requirements. You'll find a job description example for most common jobs. Starting with a sample job description will make sure you do not miss any of the key requirements for a role and new hires will have a better understanding of what their role is" +
//                    "What Is the Difference Between a Job Specification and a Job Description?\n" +
//                    "A job specification could be considered a more precise job description that details the exact educational degrees, experience, skills, and requirements for a role. In most cases, these terms are used interchangeably and nearly always describe the same document.\n" +
//                    "\n" +
//                    "Generic Job Description Template:\n" +
//                    "We also have a general job description template you can download that will cover any role.\n" +
//                    "\n" +
//                    "What are some tips on how to write a professional job description?\n" +
//                    "Make sure the title of the job position and description match. Do your research. If you're not familiar with the job, talk to someone who is and have them help with the description. Make sure it clearly defines the goals of the position and a timeline for reaching them.\n" +
//                    "\n" +
//                    "Do you have an example of a good job description I can check out?\n" +
//                    "Yes! Check out our sample job descriptions below, as well as our guide on how to write a job description. You should find everything you need to model your description on.\n" +
//                    "\n" +
//                    "Do your job descriptions mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmcontain work duties?\n" +
//                    "Our job descriptions contain the most common job duties list for each position. This should help you get your description written really quickly, although you may need to add job duty kkkkkkkkkkkkkkkjkhkhkhkhjhjhkjhjhjkhjhkjhkhk"+"\n");
//            task.setTaskInstractor("Ahmed Mohamed");
//            task.setTaskName("software development");
//            task.setTaskDeadline((i+1)+"/12/2018");
//            tasks.add(task);
//        }
        recyclerView.setAdapter(new TasksAdapter(this,tasks));
    }


    }



