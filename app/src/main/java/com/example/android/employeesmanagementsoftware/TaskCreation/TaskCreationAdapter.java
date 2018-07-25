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

import java.util.Set;


public class TaskCreationAdapter extends CursorAdapter{

    private Set<Long> employees;
    private final String TAG="adapter";

    TaskCreationAdapter(Context context, Cursor c, Set<Long> employees) {
        super(context, c, 0);
        this.employees=employees;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.task_creation_row, parent, false);
    }
    //method to handle the list view
    @Override
    public void bindView(final View view, final Context context, final Cursor cursor) {

        final TextView employeeText=(TextView) view.findViewById(R.id.employee_name_text);
        final CheckBox employeeCheckBox=(CheckBox) view.findViewById(R.id.employee_check_box);

        //get the employee names from the cursor
        String employeeName=cursor.getString(cursor.getColumnIndexOrThrow(EmployeeContract.EmployeeEntry.COLUMN_EMPLOYEE_NAME));

        employeeText.setText(employeeName);//set the text view with the employee names
        //handle the changing in the check box
        employeeCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()){
                    employees.add(cursor.getLong(cursor.getColumnIndex(EmployeeContract.EmployeeEntry._ID)));
                }else{
                    employees.remove(cursor.getLong(cursor.getColumnIndex(EmployeeContract.EmployeeEntry._ID)));
                }
            }
        });

    }



}
