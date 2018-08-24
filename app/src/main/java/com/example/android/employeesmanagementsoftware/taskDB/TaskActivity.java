package com.example.android.employeesmanagementsoftware.taskDB;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.android.employeesmanagementsoftware.EmployeeDB.EmployeeAdapter;
import com.example.android.employeesmanagementsoftware.TaskCreation.TaskCreation;
import com.example.android.employeesmanagementsoftware.data.Contracts.TaskContract.TaskEntry;
import com.example.android.employeesmanagementsoftware.R;
import com.example.android.employeesmanagementsoftware.data.DBHelpers.EmployeesManagementDbHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/*
made by menna
 */
//First you need to show departement and  the employees in this departement who work  in the task
public class TaskActivity extends AppCompatActivity implements Evaluation.EvaluationListner{
    private EmployeesManagementDbHelper employeeDBHelper;
    private TextView datetext;
    private TextView descriptiontext;
    private TextView deadlinetext ;
    private TasksFragment tasksFragment = TasksFragment.newInstance();
    private RatingBar mRatingBar;
    private TextView mEvaluation;
    private int position;
    private ArrayList<Task> tasks;
    private long taskID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
         employeeDBHelper = new EmployeesManagementDbHelper(this);
         datetext = findViewById(R.id.taskdate);
         descriptiontext = findViewById(R.id.taskdesc);
         deadlinetext = findViewById(R.id.deadline);
         mRatingBar = findViewById(R.id.ratingBar_task);
         mEvaluation = findViewById(R.id.evaluation);
         Intent intent= getIntent();
         position = intent.getExtras().getInt("position");
         tasks = (ArrayList<Task>) getIntent().getSerializableExtra("data");
         taskID = getIntent().getExtras().getLong("taskId");
            setTitle(tasks.get(position).getTaskName());
            datetext.setText(tasks.get(position).getTaskDate());
            descriptiontext.setText(tasks.get(position).getTaskDetails());
            deadlinetext.setText(tasks.get(position).getTaskDeadline());
            if (tasks.get(position).isDone()) {
                mRatingBar.setRating(tasks.get(position).getEvaluation());
                mRatingBar.setVisibility(View.VISIBLE);
                mEvaluation.setVisibility(View.VISIBLE);
            }
        setEmployees();

        }


    private void setEmployees(){

        Cursor cursor = employeeDBHelper.getEmployeesOfTask(taskID);
        ListView employees = (ListView)findViewById(R.id.employees_list);
        EmployeeAdapter adapter = new EmployeeAdapter(this,cursor);
        employees.setAdapter(adapter);
        RelativeLayout emptyView = (RelativeLayout) findViewById(R.id.empty_employees);
        employees.setEmptyView(emptyView);

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
            showDeleteConfirmationDialog();

        }
        if (id == R.id.action_update) {
            Intent intent = new Intent(TaskActivity.this, TaskCreation.class);
            intent.putExtra("task", tasks.get(position));
            intent.putExtra("task_id",tasks.get(position).getId());
            intent.putExtra("IsEdit", true);
            finish();
            startActivity(intent);
        }
        if (id == R.id.action_done) {
            openDialog();
        }

        return super.onOptionsItemSelected(item);
    }
    public void openDialog(){
        Evaluation evaluation = new Evaluation();
        evaluation.show(getSupportFragmentManager(),"EvaluationTaskDialog");
    }
    @Override
    public void applyingRating(int rate) {
        Log.v("ID From Activityr", "" + tasks.get(position).getId());
        boolean re = employeeDBHelper.updateTaskEvaluation(tasks.get(position).getId(),true,rate);
        Log.v("boolean", "" + re);
        tasks.get(position).setEvaluation(rate);
        tasks.get(position).setDone(true);
        TasksFragment.newInstance().updateTasksList(tasks.get(position),(int)taskID);
        mRatingBar.setRating(rate);
        mRatingBar.setVisibility(View.VISIBLE);
        mEvaluation.setVisibility(View.VISIBLE);
    }
    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete this task ?");
        builder.setPositiveButton("End", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (tasksFragment.deleteTaskFromList((int)tasks.get(position).getId())){
                    finish();
                }else
                    Toast.makeText(getApplicationContext(), "Can't close this department", Toast.LENGTH_LONG).show();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
