package com.example.android.employeesmanagementsoftware.taskDB;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.android.employeesmanagementsoftware.TaskCreation.TaskCreation;
import com.example.android.employeesmanagementsoftware.data.Contracts.TaskContract.TaskEntry;
import com.example.android.employeesmanagementsoftware.R;
import com.example.android.employeesmanagementsoftware.data.DBHelpers.EmployeesManagementDbHelper;
/*
made by menna
 */
//First you need to show departement and  the employees in this departement who work  in the task
public class TaskActivity extends AppCompatActivity implements Evaluation.EvaluationListner{
    private EmployeesManagementDbHelper employeeDBHelper;
    private Long task_id;
    private TextView titletext;
    private TextView datetext;
    private TextView descriptiontext;
    private TextView deadlinetext ;
    private TasksFragment tasksFragment = TasksFragment.newInstance(0);
    private RatingBar mRatingBar;
    private TextView mEvaluation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
         employeeDBHelper = new EmployeesManagementDbHelper(this);
         titletext =  findViewById(R.id.tasktitle);
         datetext = findViewById(R.id.taskdate);
         descriptiontext = findViewById(R.id.taskdesc);
         deadlinetext = findViewById(R.id.deadline);
         mRatingBar = findViewById(R.id.ratingBar_task);
         mEvaluation = findViewById(R.id.evaluation);
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
            if (Integer.parseInt(cursor.getString(cursor.getColumnIndex(TaskEntry.COLUMN_TASK_COMPLETED)))> 0) {
                mRatingBar.setRating(Integer.parseInt(cursor.getString(cursor.getColumnIndex(TaskEntry.COLUMN_TASK_EVALUATION))));
                mRatingBar.setVisibility(View.VISIBLE);
                mEvaluation.setVisibility(View.VISIBLE);
            }

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
        employeeDBHelper.updateTaskEvaluation(task_id,true,rate);
        mRatingBar.setRating(rate);
        mRatingBar.setVisibility(View.VISIBLE);
        mEvaluation.setVisibility(View.VISIBLE);
    }
}
