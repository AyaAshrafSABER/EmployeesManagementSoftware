package com.example.android.employeesmanagementsoftware;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import com.example.android.employeesmanagementsoftware.data.Contracts.DepartmentContract;
import com.example.android.employeesmanagementsoftware.data.DBHelpers.EmployeesManagementDbHelper;

public class DepartmentCreation extends AppCompatActivity {
    EditText Description;
    EditText Name_of_Department;
    EmployeesManagementDbHelper emdb ;
    private  DepFragment depFragment = DepFragment.newInstance(5);
    Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
      emdb =  new EmployeesManagementDbHelper(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Description= findViewById(R.id.editText);
        Name_of_Department= findViewById(R.id.editText3);
        save= findViewById(R.id.save);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "NO ACTION YET", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(Name_of_Department.getText().toString()=="" || Description.getText().toString()== "")
                    Snackbar.make(v, "SOME OR ALL INPUTS ARE EMPTY. PLEASE ENTER VALID VALUES.", Snackbar.LENGTH_LONG).setAction("", null).show();
                else{
              boolean flag =   emdb.addDepartment( Name_of_Department.getText().toString(),Description.getText().toString());
              if(flag){
                  Snackbar.make(v, "ENTERED SUCCESSFULLY", Snackbar.LENGTH_LONG).setAction("", null).show();
                  Description.setText("",TextView.BufferType.EDITABLE);
                  Name_of_Department.setText("",TextView.BufferType.EDITABLE);
                 // depFragment.updateDepartmentList(emdb);
              }
            else
                  Snackbar.make(v, "FAILED TO ENTER CURRENT DEPARTMENT. TRY AGAIN LATER.", Snackbar.LENGTH_LONG).setAction("", null).show();
            }}
        });
    }

}
