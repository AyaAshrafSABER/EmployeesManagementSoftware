package com.example.employeesmanagementsoftware.test;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.app.DatePickerDialog;
import android.text.InputType;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.employeesmanagementsoftware.test.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import com.example.android.employeesmanagementsoftware.data.DBHelpers.EmployeesManagementDbHelper;
import com.example.android.employeesmanagementsoftware.data.Contracts.EmployeeContract.EmployeeEntry ;
import com.example.android.employeesmanagementsoftware.data.Contracts.EmployeeContract;

public class MainActivity extends AppCompatActivity {
    DatePickerDialog picker;
    EditText date_select;
    Button submit;
    String employee_name , employee_email ; //to be read from input fields


    EmployeesManagementDbHelper emdb = new EmployeesManagementDbHelper(this); //dbhelper to use for connecting to db

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        date_select =findViewById(R.id.editText4);
        submit=findViewById(R.id.button);
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "robotomono_medium.otf");
        submit.setTypeface(custom_font);
        date_select.setInputType(InputType.TYPE_NULL);
        date_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);

                // date picker dialog
                picker = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                date_select.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean addEmployeeToDb(){
        //adds an employee entry to employee table

        SQLiteDatabase db = emdb.getWritableDatabase(); //gets writeable instance of database
        ContentValues cv  = new ContentValues(); //used for inserting an entry
        cv.put(EmployeeEntry.COLUMN_EMPLOYEE_BIRTHDATE,date_select.toString());
        cv.put(EmployeeEntry.COLUMN_EMPLOYEE_NAME,employee_name);
        cv.put(EmployeeEntry.COLUMN_EMPLOYEE_EMAIL,employee_email);
        long flag = db.insert(EmployeeContract.TABLE_NAME,null,cv); //reutrns a flag to indicate succes of insertion

        if(flag==-1) return false; //-1 if insert fails

        employee_name=null; //resets the variables
        employee_email=null;
        date_select = null;
        return true;

    }
}
