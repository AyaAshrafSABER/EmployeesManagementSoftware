package com.example.android.employeesmanagementsoftware.DepartmentDB;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

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
public class DepartmentActivity extends AppCompatActivity {

    private EmployeesManagementDbHelper helper;
    private EditText description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.department);
//        helper = new EmployeesManagementDbHelper(this);
//
//        description = (EditText)findViewById(R.id.description);
//
//        Intent intent = getIntent();
//        //TODO lazm fi list l departments n pass l id k "departmentId"
//        final long departmentId = intent.getExtras().getLong("departmentId");
//
//        //setting name,description of department
//        Cursor cursorDep = helper.getDepartment(departmentId);
//        description.setText(cursorDep.getString(cursorDep.getColumnIndex(DepartmentEntry.COLUMN_DEPARTMENT_DESCRIPTION)));
//        setTitle(cursorDep.getString(cursorDep.getColumnIndex(DepartmentEntry.COLUMN_DEPARTMENT_NAME)));
//        cursorDep.close();
//
//        //setting list of employees in this department
//        ListView listView = findViewById(R.id.employees_list);
//
//        Cursor cursorEmp = helper.getEmployessOfDepartment(departmentId);
//        EmployeeAdapter adapterEmp = new EmployeeAdapter(this,cursorEmp);
//        listView.setAdapter(adapterEmp);
//
//        RelativeLayout emptyView = (RelativeLayout)findViewById(R.id.empty_view);
//        listView.setEmptyView(emptyView);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//
//                Intent intent = new Intent(DepartmentActivity.this, EmployeeActivity.class);
//                intent.putExtra("employeeId",id);
//                startActivity(intent);
//
//            }
//        });
//        cursorEmp.close();

        //TODO need to implement helper meyhod to get tasks per department

        //setting list of tasks in this department
     /*   Cursor cursorTask = helper.get
        ListView tasksList = (ListView)findViewById(R.id.tasks_list);
        TaskAdapter adapterTask = new TaskAdapter(this,cursorTask);
       tasksList.setAdapter(adapterTask);
        RelativeLayout emptyTasks = (RelativeLayout)findViewById(R.id.empty_tasks);
        tasksList.setEmptyView(emptyTasks);


        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DepartmentActivity.this, EmployeeCreation.class);
              intent.putExtra("departmentId",departmentId);
                startActivity(intent);

            }
        });

        cursorTask.close();
*/
    }

}