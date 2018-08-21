package com.example.android.employeesmanagementsoftware.DepartmentDB;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.android.employeesmanagementsoftware.DepartmentCreation;
import com.example.android.employeesmanagementsoftware.EmployeeCreation;
import com.example.android.employeesmanagementsoftware.EmployeeDB.EmployeeActivity;
import com.example.android.employeesmanagementsoftware.EmployeeDB.EmployeeAdapter;
import com.example.android.employeesmanagementsoftware.R;
import com.example.android.employeesmanagementsoftware.data.Contracts.DepartmentContract.DepartmentEntry;
import com.example.android.employeesmanagementsoftware.data.DBHelpers.EmployeesManagementDbHelper;

import java.util.ArrayList;

/**
 * Created by Monica  on 7/11/2018.
 */
//need to attach her job with database
// convert actvity to fregment

// /notes and performance of each employee,description edittext,Notify adapter to change
public class DepartmentActivity extends AppCompatActivity {

    private final int EMP_REQUEST = 1;
    private EmployeesManagementDbHelper helper;
    private TextView description;
    public ListView employees;
    private EmployeeAdapter adapterEmp;
    private long departmentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.department);
        helper = new EmployeesManagementDbHelper(this);
        setDepatementParameter();
        setEmployeeList();

        Log.i("start", "oncreate");
/*
        ArrayList<Long> arr = new  ArrayList<Long>();
        Long id = Long.valueOf(2);arr.add(0,id);
        helper.addTask("task1",4,"lol","08-08-2018",arr);
*/
        employees.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Intent intent = new Intent(DepartmentActivity.this, EmployeeActivity.class);
                intent.putExtra("employeeId", id);
                Log.i("perf", "id " + id);
                startActivityForResult(intent, EMP_REQUEST);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DepartmentActivity.this, EmployeeCreation.class);
                intent.putExtra("departmentId", departmentId);
                Log.i("insert", String.valueOf(departmentId));
                startActivityForResult(intent, EMP_REQUEST);
            }
        });

        displayTaskList();

    }

    private void setDepatementParameter() {
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
    }


    private void setEmployeeList() {
        //setting list of employees in this department
        employees = findViewById(R.id.employees_list);
        Cursor cursorEmp = helper.getEmployessOfDepartment(departmentId);

        adapterEmp = new EmployeeAdapter(this, cursorEmp);
        employees.setAdapter(adapterEmp);
        RelativeLayout emptyView = (RelativeLayout) findViewById(R.id.empty_view);
        employees.setEmptyView(emptyView);
        // cursorEmp.close();
    }

    private void displayTaskList() {
        Cursor cursorTask = helper.getTasksOfDepartment(departmentId);
        ListView tasksList = (ListView) findViewById(R.id.tasks_list);
        TaskAdapter adapterTask = new TaskAdapter(this, cursorTask);
        tasksList.setAdapter(adapterTask);
        RelativeLayout emptyTasks = (RelativeLayout) findViewById(R.id.empty_tasks);
        tasksList.setEmptyView(emptyTasks);
        //    cursorTask.close();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == EMP_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                Cursor cursor = helper.getEmployessOfDepartment(departmentId);
                adapterEmp.swapCursor(cursor);
                adapterEmp.notifyDataSetChanged();
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_depatment, menu);
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

            return true;
        }
        if (id == R.id.action_update) {
            Intent intent = new Intent(DepartmentActivity.this, DepartmentCreation.class);
            intent.putExtra("depatmentID", departmentId);
            intent.putExtra("IsEdit", true);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

}