package com.example.android.employeesmanagementsoftware.TaskCreation;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.android.employeesmanagementsoftware.R;
import com.example.android.employeesmanagementsoftware.data.Contracts.EmployeeContract;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Set;


public class TaskCreationAdapter extends CursorAdapter{

    private Set<String> employees;

    TaskCreationAdapter(Context context, Cursor c, Set<String> employees) {
        super(context, c, 0);
        this.employees=employees;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.task_creation_row, parent, false);
    }

    @Override
    public void bindView(final View view, final Context context, final Cursor cursor) {

        final TextView employeeText=(TextView) view.findViewById(R.id.employee_name_text);
        final CheckBox employeeCheckBox=(CheckBox) view.findViewById(R.id.employee_check_box);

        String employeeName=cursor.getString(cursor.getColumnIndexOrThrow(EmployeeContract.EmployeeEntry.COLUMN_EMPLOYEE_NAME));
        employeeText.setText(employeeName);
        employeeCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    //TODO get index from cursor. conflict??
                    employees.add(employeeText.getText().toString());
                }else{
                    employees.remove(employeeText.getText().toString());
                }
            }
        });

    }


    // method to add an employee checked to the list at specific index
    public void addEmployee(String employeeName){

    employees.add(employeeName);

    }
    // remove an employee from the list with its index to prevent searching in the list
    public void removeEmployee(String employeeName){

        employees.remove(employeeName);

    }

}
