package com.example.android.employeesmanagementsoftware.taskDB;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.android.employeesmanagementsoftware.TaskCreation.TaskCreation;
import com.example.android.employeesmanagementsoftware.data.Contracts.TaskContract.TaskEntry;
import com.example.android.employeesmanagementsoftware.R;
import com.example.android.employeesmanagementsoftware.data.DBHelpers.EmployeesManagementDbHelper;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
/*
made by menna
 */
//First you need to show departement and  the employees in this departement who work  in the task
public class TaskActivity extends AppCompatActivity {
    private EmployeesManagementDbHelper employeeDBHelper;
    private Long task_id;
    private TextView titletext;
    private TextView datetext;
    private TextView descriptiontext;
    private TextView deadlinetext ;
    private TasksFragment tasksFragment = TasksFragment.newInstance(0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        employeeDBHelper = new EmployeesManagementDbHelper(this);
         titletext =  findViewById(R.id.tasktitle);
         datetext = findViewById(R.id.taskdate);
         descriptiontext = findViewById(R.id.taskdesc);
         deadlinetext = findViewById(R.id.deadline);

        Intent intent= getIntent();
        task_id = intent.getExtras().getLong("task_id");
        Cursor cursor = employeeDBHelper.getSpecifiTaskCursor(task_id);
        if (cursor.moveToFirst()) {
            String title = cursor.getString(cursor.getColumnIndex(TaskEntry.COLUMN_TASK_NAME));
            String description = cursor.getString(cursor.getColumnIndex(TaskEntry.COLUMN_TASK_DESCRIPTION));
            String deadline = cursor.getString(cursor.getColumnIndex(TaskEntry.COLUMN_TASK_DEADLINE));
            String date = cursor.getString(cursor.getColumnIndex(TaskEntry.COLUMN_TASK_DATE));
            titletext.setText(title);
            datetext.setText("Start Date: " + date);
            descriptiontext.setText(description);
            deadlinetext.setText("Deadline: " + deadline);
        }

}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_task, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete) {
              employeeDBHelper.deleteTask(task_id );
              tasksFragment.updateTasksList(employeeDBHelper);
               this.finish();
        }
        if (id == R.id.action_update) {
            Intent intent = new Intent(TaskActivity.this, TaskCreation.class);
            intent.putExtra("task_id",(long) task_id);
            intent.putExtra("IsEdit", true);
            startActivity(intent);
            tasksFragment.updateTasksList(employeeDBHelper);
        }
        if (id == R.id.action_done) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());

// 2. Chain together various setter methods to set the dialog characteristics
            builder.setMessage("hello")
                    .setTitle("yarab");

// 3. Get the AlertDialog from create()
            AlertDialog dialog = builder.create();
        }

        return super.onOptionsItemSelected(item);
    }

}
