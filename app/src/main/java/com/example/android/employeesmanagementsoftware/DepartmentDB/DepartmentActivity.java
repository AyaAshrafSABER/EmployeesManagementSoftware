package com.example.android.employeesmanagementsoftware.DepartmentDB;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.employeesmanagementsoftware.DepartmentCreation;
import com.example.android.employeesmanagementsoftware.EmployeeCreation;
import com.example.android.employeesmanagementsoftware.EmployeeDB.EmployeeActivity;
import com.example.android.employeesmanagementsoftware.EmployeeDB.EmployeeAdapter;
import com.example.android.employeesmanagementsoftware.R;
import com.example.android.employeesmanagementsoftware.data.Contracts.DepartmentContract.DepartmentEntry;
import com.example.android.employeesmanagementsoftware.data.DBHelpers.EmployeesManagementDbHelper;
/**
 * Created by Monica  on 7/11/2018.
 */
//need to attach her job with database
// convert actvity to fregment

// /notes and performance of each employee,description edittext,Notify adapter to change
public class DepartmentActivity extends AppCompatActivity {
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
    private final int EMP_REQUEST = 1;
    private EmployeesManagementDbHelper helper;
    private TextView description;
    private ListView employees;
    private EmployeeAdapter adapterEmp;
    private long departmentId;
    private String depName;
    private DepFragment depFragment = DepFragment.newInstance(0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.department);
        helper = new EmployeesManagementDbHelper(this);
        setDepatementParameter();
        setEmployeeList();

        employees.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(DepartmentActivity.this, EmployeeActivity.class);
                intent.putExtra("employeeId", id);
                startActivityForResult(intent, EMP_REQUEST);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DepartmentActivity.this, EmployeeCreation.class);
                intent.putExtra("departmentId", departmentId);
                startActivityForResult(intent, EMP_REQUEST);
            }
        });

        displayTaskList();

    }

    private void setDepatementParameter() {
        description =  findViewById(R.id.description);
        Intent intent = getIntent();
        departmentId = intent.getExtras().getLong("departmentId");
        //setting name,description of department
        Cursor cursorDep = helper.getDepartment(departmentId);
        if (cursorDep.moveToFirst()) {
            description.setText(cursorDep.getString(cursorDep.getColumnIndex(DepartmentEntry.COLUMN_DEPARTMENT_DESCRIPTION)));
            depName = cursorDep.getString(cursorDep.getColumnIndex(DepartmentEntry.COLUMN_DEPARTMENT_NAME));
            setTitle(depName);
        }
        cursorDep.close();
    }


    private void setEmployeeList() {
        //setting list of employees in this department
        employees = findViewById(R.id.employees_list);
        Cursor cursorEmp = helper.getEmployessOfDepartment(departmentId);

        adapterEmp = new EmployeeAdapter(this, cursorEmp);
        employees.setAdapter(adapterEmp);

        RelativeLayout emptyView = (RelativeLayout) findViewById(R.id.empty_employees);
        employees.setEmptyView(emptyView);
    }

    private void displayTaskList() {
        Cursor cursorTask = helper.getTasksOfDepartment(departmentId);
        ListView tasksList = findViewById(R.id.tasks_list);
        TaskAdapter adapterTask = new TaskAdapter(this, cursorTask);
        tasksList.setAdapter(adapterTask);

        RelativeLayout emptyTasks = (RelativeLayout) findViewById(R.id.empty_tasks_dep);
        tasksList.setEmptyView(emptyTasks);
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
            showDeleteConfirmationDialog();
        }
        if (id == R.id.action_update) {
            Intent intent = new Intent(DepartmentActivity.this, DepartmentCreation.class);
            intent.putExtra("depatmentID", departmentId);
            intent.putExtra("IsEdit", true);
            startActivity(intent);
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.deleteDep);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (helper.deleteDepartment(departmentId)){
                    depFragment.updateDepartmentList(helper);
                    finish();
                }else
                    Toast.makeText(getApplicationContext(), R.string.not_deleteDep, Toast.LENGTH_LONG).show();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
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