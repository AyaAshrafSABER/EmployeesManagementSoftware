package com.example.android.employeesmanagementsoftware.TaskCreation;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import com.example.android.employeesmanagementsoftware.R;
import com.example.android.employeesmanagementsoftware.data.Contracts.DepartmentContract;
import com.example.android.employeesmanagementsoftware.data.DBHelpers.EmployeesManagementDbHelper;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

public class TaskCreation extends AppCompatActivity {

    private static final String TAG = "spinner";
    private final EmployeesManagementDbHelper employeeDBHelper = new EmployeesManagementDbHelper(this);
    private Set<Long> employees;
    private Spinner spinner;
    private TaskCreationAdapterPool adapterPool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_creation);

        employeeDBHelper.addDepartment("engineering", "en");
        employeeDBHelper.addDepartment("marketing", "mk");
        employeeDBHelper.addDepartment("accounting", "ac");
        employeeDBHelper.addDepartment("medical", "md");

        employeeDBHelper.addEmployee("aly", "55", 1,
                "engineer", "bvfs", 555, null);
        employeeDBHelper.addEmployee("omar", "55", 1,
                "engineer", "bvfg", 565, null);
        employeeDBHelper.addEmployee("ahmad", "55", 1,
                "engineer", "bvfg", 565, null);
        employeeDBHelper.addEmployee("youssef", "55", 1,
                "engineer", "bvfg", 565, null);
        employeeDBHelper.addEmployee("yassin", "55", 1,
                "engineer", "bvfg", 565, null);
        employeeDBHelper.addEmployee("mohamed", "55", 1,
                "engineer", "bvfg", 565, null);
        employeeDBHelper.addEmployee("hassan", "55", 1,
                "engineer", "bvfg", 565, null);




            employees = new TreeSet<>();
            adapterPool = new TaskCreationAdapterPool(employeeDBHelper, this, employees);
            initSpinner();


    }

    public void initSpinner() {

        //object of drop down menu
        spinner = findViewById(R.id.departmentDropDown);
        final Cursor cursor = employeeDBHelper.getAllDepartments();

        //an adapter to handle data viewed by the spinner
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this, android.R.layout.simple_spinner_item, cursor,
                new String[]{DepartmentContract.DepartmentEntry.COLUMN_DEPARTMENT_NAME},
                new int[]{android.R.id.text1}, 0);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //when a department is chosen a list of its employees would appear under the spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                initListView(cursor.getLong(cursor.getColumnIndex(DepartmentContract.
                                DepartmentEntry._ID))
                        , employeeDBHelper);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    //method to bind the list view of the employees with a cursor adapter
    public void initListView(final long depID, EmployeesManagementDbHelper employeeDBHelper) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ListView employeesList = (ListView) findViewById(R.id.employees_List);
                Parcelable state = employeesList.onSaveInstanceState();

                //set the adapter that handles the contents of the employees list view
                employeesList.setAdapter(adapterPool.getAdapter((int) depID));
                employeesList.onRestoreInstanceState(state);
            }
        });


    }

    //method to inflate the view of the save button in the action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_task_creation, menu);
        return true;
    }

    //method to handle the save button click
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //get refrences to all edit texts
        EditText taskName = findViewById(R.id.task_name_edit);
        EditText taskDescp = findViewById(R.id.department_description_edit_text);
        EditText taskDeadline = findViewById(R.id.task_deadline_edit);
        Log.i(TAG, "size=: " + employees.size());
        for (long l :
                employees) {
            Log.i(TAG, "elements: " + l + "  ");
        }

        ArrayList<Long> emp = new ArrayList<>();
        emp.addAll(employees);

        if (item.getItemId() == R.id.save_task_creation_button) {
            //add a new task with the extracted data
            employeeDBHelper.addTask(taskName.getText().toString(), 5, taskDescp.getText().toString(),
                    taskDeadline.getText().toString(), emp);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("spinner", spinner.onSaveInstanceState());
        spinner.getSelectedItem();
    }
}