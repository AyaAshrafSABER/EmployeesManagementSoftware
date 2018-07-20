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


public class TaskCreationAdapter extends CursorAdapter{

    private List<String> employees;
    public TaskCreationAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        employees=new ArrayList<>();
        return LayoutInflater.from(context).inflate(R.layout.task_creation_row, parent, false);
    }

    @Override
    public void bindView(final View view, final Context context, Cursor cursor) {

        final TextView employeeText=(TextView) view.findViewById(R.id.employee_name_text);
        final CheckBox employeeCheckBox=(CheckBox) view.findViewById(R.id.employee_check_box);

        String employeeName=cursor.getString(cursor.getColumnIndexOrThrow(EmployeeContract.EmployeeEntry.COLUMN_EMPLOYEE_NAME));
        employeeText.setText(employeeName);
        employeeCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){

                    addEmployee(employeeText.getVerticalScrollbarPosition(),employeeText.getText().toString());
                }else{
                    removeEmployee(employeeText.getVerticalScrollbarPosition());
                }
            }
        });

    }


    // method to add an employee checked to the list at specific index
    public void addEmployee(int index,String employee_name){

    employees.add(index,employee_name);

    }
    // remove an employee from the list with its index to prevent searching in the list
    public void removeEmployee(int index){

        employees.remove(index);

    }
    //called when the add button is clicked to return the checked employees
    public List<String> getEmployees(){
        return employees;


    }
}
