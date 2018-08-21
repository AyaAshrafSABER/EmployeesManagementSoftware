package com.example.android.employeesmanagementsoftware;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.app.DatePickerDialog;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.*;

import java.util.Calendar;

import com.example.android.employeesmanagementsoftware.DepartmentDB.DepartmentActivity;
import com.example.android.employeesmanagementsoftware.EmployeeDB.EmployeeAdapter;
import com.example.android.employeesmanagementsoftware.data.Contracts.EmployeeContract;
import com.example.android.employeesmanagementsoftware.data.DBHelpers.EmployeesManagementDbHelper;

public class EmployeeCreation extends AppCompatActivity {
    DatePickerDialog picker;
    Button submit;
    TextView date_select;
    EditText employee_name , employee_email, employee_phone, employee_job ; //to be read from input fields
    private static final String name_regex = "^([A-Za-z]{3,40})([ \\t][A-Za-z]{3,40})*$";
    private static final String email_regex = "^[-a-z0-9~!$%^&*_=+}{\\'?]+(\\.[-a-z0-9~!$%^&*_=+}{\\'?]+)*@([a-z0-9_][-a-z0-9_]*(\\.[-a-z0-9_]+)*\\.(aero|arpa|biz|com|coop|edu|gov|info|int|mil|museum|name|net|org|pro|travel|mobi|[a-z][a-z])|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,5})?$";
    private static final String phone_regex = "^[0-9]{7,}$";
    private  static  final  String birth_regex = "^[0-9]{1,2}-[0-9]{1,2}-[0-9]{4}$";




    EmployeesManagementDbHelper emdb ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       emdb  = new EmployeesManagementDbHelper(this); //dbhelper to use for connecting to db
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_employee_creation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//added by Monica
        Intent intent = getIntent();
        final long departmentId = intent.getExtras().getLong("departmentId");

        setSupportActionBar(toolbar);
        date_select = findViewById(R.id.editBirth);
       // if(date_select==null) Log.i("take ---", "onCreate: " + "error null ... ");
        submit=findViewById(R.id.submitButton);
        employee_name=findViewById(R.id.editName);
        employee_email=findViewById(R.id.editEmail);
        employee_phone=findViewById(R.id.editPhone);
        employee_job=findViewById(R.id.editJob);

        //date_select.setInputType(InputType.TYPE_CLASS_DATETIME);
        date_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);

                // date picker dialog
                picker = new DatePickerDialog(EmployeeCreation.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                date_select.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                        }, day, month, year);
                picker.show();
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (matchFields()!= 5)
                    Snackbar.make(v, "CHECK ENTERED VALUES AND TRY AGAIN.", Snackbar.LENGTH_LONG).setAction("", null).show();
                else {
                    //changed id to long Monica
                    if (emdb.addEmployee(employee_name.getText().toString(), date_select.getText().toString(), departmentId, employee_job.getText().toString(), employee_email.getText().toString(), employee_phone.getText().toString(), null)) {
                        Snackbar.make(v, "CURRENT EMPLOYEE ENTERED SUCCESSFULLY.", Snackbar.LENGTH_LONG).setAction("", null).show();
                        Intent returnIntent = new Intent();
                        setResult(Activity.RESULT_OK, returnIntent);
                        finish();
                    } else {
                        Snackbar.make(v, "FAILED TO ENTER CURRENT EMPLOYEE. TRY AGAIN LATER.", Snackbar.LENGTH_LONG).setAction("", null).show();
                    }
                }
            }
        });


    }

public int matchFields(){
    int number_of_matches=0;
    if(Pattern.matches(name_regex,employee_name.getText().toString()))
        number_of_matches++;
    if(Pattern.matches(email_regex,employee_email.getText().toString()))
        number_of_matches++;
    if(Pattern.matches(phone_regex,employee_phone.getText().toString()))
        number_of_matches++;
    if(Pattern.matches(birth_regex,date_select.getText().toString()))
        number_of_matches++;
    if(employee_job.getText().toString()!=null)
        number_of_matches++;
    Log.i("opps ---", "done bro :)");
    return number_of_matches;
}
}


