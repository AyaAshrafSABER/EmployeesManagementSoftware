package com.example.android.employeesmanagementsoftware.EmployeeDB;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.android.employeesmanagementsoftware.R;
import com.example.android.employeesmanagementsoftware.data.Contracts.EmployeeContract;
import com.example.android.employeesmanagementsoftware.data.Contracts.EmployeeContract.EmployeeEntry;
import com.example.android.employeesmanagementsoftware.data.DBHelpers.EmployeesManagementDbHelper;

import java.io.File;

/**
 * Created  by Monica on on 7/10/2018.
 */

public class EmployeeActivity  extends AppCompatActivity {
    private LinearLayout parentLinearLayout;
    private EmployeesManagementDbHelper helper;
    private EditText name,email,phone,birthday;
    private ImageView image;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee);

        name = (EditText)findViewById(R.id.employee_name);
        email = (EditText)findViewById(R.id.email);
        phone = (EditText)findViewById(R.id.phone);
        birthday = (EditText)findViewById(R.id.birthday);
        image = (ImageView)findViewById(R.id.employee_icon);


        helper = new EmployeesManagementDbHelper(this);
       Intent intent = getIntent();
        long employeeId = intent.getExtras().getInt("employeeId");
        Cursor cursor = helper.getEmployessOfDepartment(employeeId);

        //setting data of employee

        name.setText(cursor.getString(cursor.getColumnIndex(EmployeeEntry.COLUMN_EMPLOYEE_NAME)));
        email.setText(cursor.getString(cursor.getColumnIndex(EmployeeEntry.COLUMN_EMPLOYEE_EMAIL)));
        //TODO INT not String
        phone.setText(cursor.getInt(cursor.getColumnIndex(EmployeeEntry.COLUMN_EMPLOYEE_PHONE)));
        //TODO date not string
        birthday.setText(cursor.getString(cursor.getColumnIndex(EmployeeEntry.COLUMN_EMPLOYEE_BIRTHDATE)));
        String path = cursor.getString(cursor.getColumnIndex(EmployeeEntry.COLUMN_EMPLOYEE_PHOTO));
        //TODO mmkn n5ali default l photo "" ?
        if(!TextUtils.isEmpty(path) && (new File(path)).exists()){
            image.setImageBitmap(BitmapFactory.decodeFile(path));
        }else{
            image.setImageResource(R.drawable.unknown);
        }

        cursor.close();
        parentLinearLayout = (LinearLayout) findViewById(R.id.parent_linear_layout);
    }
    public void onAddField(View v) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.note, null);
        // Add the new row before the add field button.
        parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount() - 1);
    }

    public void onDelete(View v) {
        parentLinearLayout.removeView((View) v.getParent());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_employee, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
              //TODO edit and save
                finish();
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
              //TODO delete selected employee
                return true;


        }
        return super.onOptionsItemSelected(item);
    }



}
