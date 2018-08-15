package com.example.android.employeesmanagementsoftware.DepartmentDB;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.android.employeesmanagementsoftware.EmployeeCreation;
import com.example.android.employeesmanagementsoftware.EmployeeDB.EmployeeActivity;
import com.example.android.employeesmanagementsoftware.R;
import com.example.android.employeesmanagementsoftware.data.Contracts.DepartmentContract;
import com.example.android.employeesmanagementsoftware.data.Contracts.DepartmentContract.DepartmentEntry;
import com.example.android.employeesmanagementsoftware.data.DBHelpers.EmployeesManagementDbHelper;

/**
 * Created by Monica  on 7/11/2018.
 */
//need to attach her job with database
// convert actvity to fregment
    //TODO Notify adapter to change,notes and performance of each employee
public class DepartmentActivity extends AppCompatActivity {

    private EmployeesManagementDbHelper helper;
    private TextView description;
    public   ListView employees;
    private EmployeeAdapter adapterEmp;
   private  long departmentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.department);
        helper = new EmployeesManagementDbHelper(this);

        description = (TextView) findViewById(R.id.description);
        Intent intent = getIntent();
        departmentId = intent.getExtras().getLong("departmentId");
        //setting name,description of department
        Cursor cursorDep = helper.getDepartment(departmentId);
        if (cursorDep.moveToFirst()) {
            description.setText(cursorDep.getString(cursorDep.getColumnIndex(DepartmentEntry.COLUMN_DEPARTMENT_DESCRIPTION)));
            setTitle(cursorDep.getString(cursorDep.getColumnIndex(DepartmentEntry.COLUMN_DEPARTMENT_NAME)));
        }
       cursorDep.close();

        //setting list of employees in this department
        employees = findViewById(R.id.employees_list);
        Cursor cursorEmp = helper.getEmployessOfDepartment(departmentId);


        adapterEmp = new EmployeeAdapter(this, cursorEmp);
        employees.setAdapter(adapterEmp);


        RelativeLayout emptyView = (RelativeLayout) findViewById(R.id.empty_view);
        employees.setEmptyView(emptyView);
        employees.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Intent intent = new Intent(DepartmentActivity.this, EmployeeActivity.class);
                intent.putExtra("employeeId", id);
                startActivity(intent);


            }
        });
        // cursorEmp.close();

        //TODO need to implement helper meyhod to get tasks per department

        //setting list of tasks in this department
     /*   Cursor cursorTask = helper.get
        ListView tasksList = (ListView)findViewById(R.id.tasks_list);
        TaskAdapter adapterTask = new TaskAdapter(this,cursorTask);
       tasksList.setAdapter(adapterTask);
        RelativeLayout emptyTasks = (RelativeLayout)findViewById(R.id.empty_tasks);
        tasksList.setEmptyView(emptyTasks);
*/

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DepartmentActivity.this, EmployeeCreation.class);
                intent.putExtra("departmentId", departmentId);
                Log.i("insert",String.valueOf(departmentId));
                startActivity(intent);

            }
        });

     //   ((EmployeeAdapter) employees.getAdapter()).notifyDataSetChanged();

//        cursorTask.close();

    }
/*
    @Override
    protected void onStart() {
        Log.i("state","start");
        super.onStart();
        ((EmployeeAdapter) employees.getAdapter()).notifyDataSetChanged();   }
*/

}