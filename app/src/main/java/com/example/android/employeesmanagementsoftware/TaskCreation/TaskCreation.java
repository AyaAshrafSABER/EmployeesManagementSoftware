package com.example.android.employeesmanagementsoftware.TaskCreation;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.android.employeesmanagementsoftware.R;
import com.example.android.employeesmanagementsoftware.data.DBHelpers.EmployeesManagementDbHelper;

import java.util.List;

public class TaskCreation extends AppCompatActivity {

    private static final String TAG="spinner";
    private TaskCreationAdapter listViewAdapter;
    private List<String> employees;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_creation);

        //object of drop down menu
        Spinner spinner=(Spinner) findViewById(R.id.departmentDropDown);

        //adapter to hold values in the menu
        //takes as input the an array defined in the resource strings.xml
        //and a default spinner defined by the platform
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.departments_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //when a department is chosen a list of its employees would appear under the spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                initListView(parent.getSelectedItem().toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    public void initListView(String dep){

        ListView employeesList=(ListView) findViewById(R.id.employees_List);


        EmployeesManagementDbHelper employeeDBHelper=new EmployeesManagementDbHelper(this);

        //TODO initialize cursor with the required query using the dbhelper methods
        Cursor cursor;
         //TODO send the cursor to the adapter's constructor

        listViewAdapter = new TaskCreationAdapter(this,null);

        //set the adapter that handles the contents of the employees list view
        employeesList.setAdapter(listViewAdapter);


    }
    //method to handle the add button click
    public void OnAddButtonClick(View view){
        //append any new employees chosen to the employees list
        employees.addAll(listViewAdapter.getEmployees());


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
