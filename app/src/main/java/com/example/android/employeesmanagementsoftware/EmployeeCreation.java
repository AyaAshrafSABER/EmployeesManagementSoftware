package com.example.android.employeesmanagementsoftware;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.app.DatePickerDialog;
import android.text.InputType;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import java.util.regex.*;

import java.util.Calendar;
import com.example.android.employeesmanagementsoftware.data.DBHelpers.EmployeesManagementDbHelper;

public class EmployeeCreation extends AppCompatActivity {
    DatePickerDialog picker;
    Button submit;
    EditText date_select,employee_name , employee_email, employee_phone ; //to be read from input fields
    private static final String name_regex = "^([A-Za-z]{3,40})([ \\t][A-Za-z]{3,40})*$";
    private static final String email_regex = "^[-a-z0-9~!$%^&*_=+}{\\'?]+(\\.[-a-z0-9~!$%^&*_=+}{\\'?]+)*@([a-z0-9_][-a-z0-9_]*(\\.[-a-z0-9_]+)*\\.(aero|arpa|biz|com|coop|edu|gov|info|int|mil|museum|name|net|org|pro|travel|mobi|[a-z][a-z])|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,5})?$";
    private static final String phone_regex = "^[0-9]{7,}$";



    EmployeesManagementDbHelper emdb = new EmployeesManagementDbHelper(this); //dbhelper to use for connecting to db

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_employee_creation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        date_select =findViewById(R.id.editDate);
        submit=findViewById(R.id.submitButton);
        employee_name=findViewById(R.id.editName);
        employee_email=findViewById(R.id.editEmail);
        employee_phone=findViewById(R.id.editPhone);
        date_select.setInputType(InputType.TYPE_NULL);
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
                        }, year, month, day);
                picker.show();
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (matchFields()!= 4)
                    Snackbar.make(v, "FAILED TO ENTER CURRENT EMPLOYEE. TRY AGAIN LATER.", Snackbar.LENGTH_LONG).setAction("", null).show();
                else {
                    if (emdb.addEmployee(employee_name.getText().toString(), date_select.getText().toString(), 0, null, employee_email.getText().toString(), Integer.valueOf(employee_phone.getText().toString()), null)) {
                        Snackbar.make(v, "CURRENT EMPLOYEE ENTERED SUCCESSFULLY.", Snackbar.LENGTH_LONG).setAction("", null).show();
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
    if(date_select.getText().toString()!=null)
        number_of_matches++;
    return number_of_matches;
}
}


