package com.example.android.employeesmanagementsoftware.TaskCreation;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import com.example.android.employeesmanagementsoftware.R;
import com.example.android.employeesmanagementsoftware.data.Contracts.DepartmentContract;
import com.example.android.employeesmanagementsoftware.data.DBHelpers.EmployeesManagementDbHelper;

import java.util.ArrayList;
import java.util.List;

public class TaskCreation extends AppCompatActivity {

    private static final String TAG="spinner";
    private TaskCreationAdapter listViewAdapter;
    private List<String> employees;
    private EmployeesManagementDbHelper employeeDBHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_creation);
        employees=new ArrayList<>();
        //object of drop down menu
        Spinner spinner=(Spinner) findViewById(R.id.departmentDropDown);

        employeeDBHelper = new EmployeesManagementDbHelper(this);
      // employeeDBHelper.addEmployee("omar","123",1,"engineer",
        //       "aa",5,"fds");
        final Cursor cursor=employeeDBHelper.getAllDepartments();


        //adapter to hold values in the menu
        //takes as input the cursor,a default spinner defined by the platform
        // the column needed o be displayed from the cursor to the UI
        //the view of each row "a text view",a flag to determine the brhaviour of the adapter
        SimpleCursorAdapter adapter=new SimpleCursorAdapter(
                this,android.R.layout.simple_spinner_item,cursor,
               new String[]{ DepartmentContract.DepartmentEntry.COLUMN_DEPARTMENT_NAME},
                new int[]{android.R.id.text1},0);
        //TODO use cursor.getPosition() to check if at each spinner choice the position is correct
        //TODO otherwise you'll have to loop through the data. Costly ??
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        Log.i(TAG, "Cursor position:"+cursor.getPosition());



        //when a department is chosen a list of its employees would appear under the spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {



                initListView(cursor.getLong(cursor.getColumnIndex(DepartmentContract.DepartmentEntry._ID))
                        ,employeeDBHelper);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    public void initListView(long depID,EmployeesManagementDbHelper employeeDBHelper){

        ListView employeesList=(ListView) findViewById(R.id.employees_List);



        //TODO initialize cursor with the required query using the dbhelper methods
        Cursor cursor=employeeDBHelper.getEmployessOfDepartment(depID);
         //TODO send the cursor to the adapter's constructor

        listViewAdapter = new TaskCreationAdapter(this,cursor);

        //set the adapter that handles the contents of the employees list view
        employeesList.setAdapter(listViewAdapter);


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
