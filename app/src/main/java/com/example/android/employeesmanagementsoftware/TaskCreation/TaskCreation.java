package com.example.android.employeesmanagementsoftware.TaskCreation;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
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
    private Set<Long> employees;
    private TaskCreationCommand commander;
    private TaskCreationUtil util;

    public TaskCreation() {
        employees = new TreeSet<>();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_creation);


        final EmployeesManagementDbHelper employeeDBHelper= new EmployeesManagementDbHelper(this); ;

        Bundle taskData=getIntent().getExtras();
        long task_id=-1;
        if (taskData!=null)
            task_id = taskData.getLong("task_id");
        Log.d(TAG, "onCreate: "+task_id);
        util=new TaskCreationUtil(this,employeeDBHelper);
        commander=util.getCommander(task_id);
        TaskCreationAdapterPool adapterPool = new TaskCreationAdapterPool(employeeDBHelper, this, employees,
                commander.execute());



           /* employeeDBHelper.addDepartment("engineering", "en");

            employeeDBHelper.addDepartment("marketing", "mk");
            employeeDBHelper.addDepartment("accounting", "ac");
            employeeDBHelper.addDepartment("medical", "md");

            employeeDBHelper.addEmployee("aly", "55", 1,
                    "engineer", "bvfs", "555", null);
            employeeDBHelper.addEmployee("omar", "55", 1,
                    "engineer", "bvfg", "565", null);
            employeeDBHelper.addEmployee("ahmad", "55", 2,
                    "engineer", "bvfg", "565", null);
            employeeDBHelper.addEmployee("youssef", "55", 2,
                    "engineer", "bvfg","565", null);
            employeeDBHelper.addEmployee("yassin", "55", 3,
                    "engineer", "bvfg", "565", null);
            employeeDBHelper.addEmployee("mohamed", "55", 3,
                    "engineer", "bvfg", "565", null);
            employeeDBHelper.addEmployee("hassan", "55", 1,
                    "engineer", "bvfg", "565", null);*/



                    initSpinner(adapterPool,employeeDBHelper);




    }
    //class extending async task to do the query operation in the background
    class SpinnerInitTask extends AsyncTask<SpinnerTaskParams,Void,SpinnerTaskParams>{

        @Override
        protected SpinnerTaskParams doInBackground(SpinnerTaskParams... spinnerTaskParams) {
            //return the the parameters after setting the cursor
            return spinnerTaskParams[0].setCursor
                    (spinnerTaskParams[0].getEmployeeDBHelper().getAllDepartments());
        }
        //after execution of doInBackground, initialize the spinner with the cursor
        @Override
        protected void onPostExecute(final SpinnerTaskParams spinnerTaskParams) {
            super.onPostExecute(spinnerTaskParams);
            //object of drop down menu
            Spinner spinner = findViewById(R.id.departmentDropDown);

            //an adapter to handle data viewed by the spinner
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                    TaskCreation.this, android.R.layout.simple_spinner_item, spinnerTaskParams.getCursor(),
                    new String[]{DepartmentContract.DepartmentEntry.COLUMN_DEPARTMENT_NAME},
                    new int[]{android.R.id.text1}, 0);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);

            //when a department is chosen a list of its employees would appear under the spinner
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    //call the method to initialize the list view of the employees of this specific department chosen
                    initListView(spinnerTaskParams.getCursor().getLong(spinnerTaskParams.getCursor().getColumnIndex(DepartmentContract.
                                    DepartmentEntry._ID))
                            , spinnerTaskParams.getAdapterPool());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }


    }

    private void initSpinner(final TaskCreationAdapterPool adapterPool,EmployeesManagementDbHelper employeeDBHelper) {

       new SpinnerInitTask().execute(new SpinnerTaskParams(employeeDBHelper,adapterPool));

    }

    //method to bind the list view of the employees with a cursor adapter
    private void initListView(final long depID, TaskCreationAdapterPool adapterPool) {

        ListView employeesList = findViewById(R.id.employees_List);

        //set the adapter that handles the contents of the employees list view
        employeesList.setAdapter(adapterPool.getAdapter((int) depID));


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
        //get references to all edit texts
        EditText taskName = findViewById(R.id.task_name_edit);
        EditText taskDescp = findViewById(R.id.department_description_edit_text);
        EditText taskDeadline = findViewById(R.id.task_deadline_edit);

        if (item.getItemId() == R.id.save_task_creation_button) {

           if(util.isEmpty(taskName.getText().toString(), taskDescp.getText().toString(),
                   taskDeadline.getText().toString())){
               Snackbar.make(taskDeadline.getRootView(), "All fields must be filled", Snackbar.LENGTH_LONG).setAction("", null).show();
               return super.onOptionsItemSelected(item);
                }

            //add a new task or update an existing one with the extracted data
            commander.saveData(taskName.getText().toString(), 5, taskDescp.getText().toString(),
                    taskDeadline.getText().toString(), new ArrayList<>(employees));


        }
        finish();
        return super.onOptionsItemSelected(item);
    }


}
