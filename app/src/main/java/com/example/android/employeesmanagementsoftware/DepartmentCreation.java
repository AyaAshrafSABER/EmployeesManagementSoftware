package com.example.android.employeesmanagementsoftware;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.employeesmanagementsoftware.DepartmentDB.DepFragment;
import com.example.android.employeesmanagementsoftware.data.DBHelpers.EmployeesManagementDbHelper;
import com.example.android.employeesmanagementsoftware.data.Contracts.DepartmentContract.DepartmentEntry;
import com.example.android.employeesmanagementsoftware.DepartmentDB.DepartementRowData.DepartmentData.DepartmentItem;

public class DepartmentCreation extends AppCompatActivity {
    private EditText description;
    private EditText nameOfDepartment;
    private EmployeesManagementDbHelper emdb ;
    private DepFragment depFragment = DepFragment.newInstance(0);
    private Button save;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depaetment_creation);

        intent = getIntent();
        boolean IsEditable = intent.getExtras().getBoolean("IsEdit");
        emdb =  new EmployeesManagementDbHelper(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        description= findViewById(R.id.department_description);
        nameOfDepartment= findViewById(R.id.department_name);
        save= findViewById(R.id.save);
        if (IsEditable) {
            updateAction();
        } else {
            AddNewDepartemnt();
        }
        //TODO: what is the importance of the  fab button



    }
    private void AddNewDepartemnt(){
        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if((nameOfDepartment.getText().toString()).matches("[A-Za-z0-9$%#@*!]{1,50}") || (description.getText().toString()).matches("^[\\s\\S]{2,200}$"))
                    Snackbar.make(v, "SOME OR ALL INPUTS ARE INVALID. PLEASE ENTER VALID VALUES.", Snackbar.LENGTH_LONG).setAction("", null).show();
                else{
                    boolean flag =   emdb.addDepartment( nameOfDepartment.getText().toString(),description.getText().toString());
                    actionSave(flag, v);
                }
            }
        });

    }

    private void updateAction() {
        final long departmentId = intent.getExtras().getLong("depatmentID");
        Cursor cursorDep = emdb.getDepartment(departmentId);
        Log.v("Dep cre cur" , ""+departmentId);
        if (cursorDep.moveToFirst()) {
            description.setText(cursorDep.getString(cursorDep.getColumnIndex(DepartmentEntry.COLUMN_DEPARTMENT_DESCRIPTION)));
            nameOfDepartment.setText(cursorDep.getString(cursorDep.getColumnIndex(DepartmentEntry.COLUMN_DEPARTMENT_NAME)));
        }
        cursorDep.close();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((nameOfDepartment.getText().toString()).matches("^[A-Za-z0-9$%#@*!]{1,50}") || (description.getText().toString()).matches("^[\\s\\S]{2,200}$")) {
                    Snackbar.make(v, "SOME OR ALL INPUTS ARE INVALID. PLEASE ENTER VALID VALUES.", Snackbar.LENGTH_LONG).setAction("", null).show();
                } else {
                    boolean correct = emdb.updateDepartment(new DepartmentItem(departmentId,nameOfDepartment.getText().toString(),description.getText().toString()));
                    actionSave(correct, v);
                }
            }
        });

    }
    private  void actionSave(boolean flag,View v) {
        if(flag){
            Snackbar.make(v, "ENTERED SUCCESSFULLY", Snackbar.LENGTH_LONG).setAction("", null).show();
            description.setText("",TextView.BufferType.EDITABLE);
            nameOfDepartment.setText("",TextView.BufferType.EDITABLE);
            depFragment.updateDepartmentList(emdb);
        }
        else
            Snackbar.make(v, "FAILED TO ENTER CURRENT DEPARTMENT. TRY AGAIN LATER.", Snackbar.LENGTH_LONG).setAction("", null).show();
    }
}



