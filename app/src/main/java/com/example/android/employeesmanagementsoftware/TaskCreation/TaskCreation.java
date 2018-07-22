package com.example.android.employeesmanagementsoftware.TaskCreation;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import com.example.android.employeesmanagementsoftware.R;
import com.example.android.employeesmanagementsoftware.data.Contracts.DepartmentContract;
import com.example.android.employeesmanagementsoftware.data.DBHelpers.EmployeesManagementDbHelper;

import java.util.Set;
import java.util.TreeSet;

public class TaskCreation extends AppCompatActivity  {

    private static final String TAG="spinner";
    private TaskCreationAdapter listViewAdapter;
    private Set<String> employees;
    private EmployeesManagementDbHelper employeeDBHelper;
    private TaskCreationAdapterPool adapterPool;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_creation);
        employees=new TreeSet<>();

        //object of drop down menu
        Spinner spinner=(Spinner) findViewById(R.id.departmentDropDown);

        employeeDBHelper = new EmployeesManagementDbHelper(this);

        adapterPool=new TaskCreationAdapterPool(employeeDBHelper,this,employees);

        final Cursor cursor=employeeDBHelper.getAllDepartments();

        //an adapter to handle data viewed by the spinner
         SimpleCursorAdapter adapter=new SimpleCursorAdapter(
                this,android.R.layout.simple_spinner_item,cursor,
               new String[]{ DepartmentContract.DepartmentEntry.COLUMN_DEPARTMENT_NAME},
                new int[]{android.R.id.text1},0);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //when a department is chosen a list of its employees would appear under the spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                initListView(cursor.getLong(cursor.getColumnIndex(DepartmentContract.
                                DepartmentEntry._ID))
                        ,employeeDBHelper);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    //method to bind the list view of the employees with a cursor adapter
    public void initListView(long depID,EmployeesManagementDbHelper employeeDBHelper){

        ListView employeesList=(ListView) findViewById(R.id.employees_List);

        //set the adapter that handles the contents of the employees list view
        employeesList.setAdapter(adapterPool.getAdapter((int)depID));


    }

    //method to inflate the view of the save button in the action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu_task_creation,menu);
        return true;
    }
    //method to handle the save button click
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.save_task_creation_button){
            //TODO call the method to create a new task with the selected employees
        }
        return super.onOptionsItemSelected(item);
    }


    //TODO add a new method to return a new task with the available data

}
