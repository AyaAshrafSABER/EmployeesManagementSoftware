package com.example.android.employeesmanagementsoftware.TaskCreation;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.android.employeesmanagementsoftware.R;
import com.example.android.employeesmanagementsoftware.data.Contracts.EmployeeContract;
import com.example.android.employeesmanagementsoftware.data.Contracts.TaskContract;
import com.example.android.employeesmanagementsoftware.data.DBHelpers.EmployeesManagementDbHelper;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;


public class EditCommand implements TaskCreationCommand {
    private Activity activity;
    private  EmployeesManagementDbHelper employeeDBHelper;
    private long task_id;
    private final String TAG="edit";

    EditCommand(Activity activity, EmployeesManagementDbHelper employeeDBHelper, long task_id) {
    this.activity=activity;
    this.employeeDBHelper=employeeDBHelper;
    this.task_id=task_id;
    }

    @Override
    public Set<Long> execute() {

        //get the refrences to all the edit texts
        EditText taskName=activity.findViewById(R.id.task_name_edit);
        EditText taskDescription=activity.findViewById(R.id.department_description_edit_text);
        EditText taskDeadline=activity.findViewById(R.id.task_deadline_edit);

        //get a cursor for the task's data for its id
        Cursor textCursor=employeeDBHelper.getSpecifiTaskCursor(task_id);

        textCursor.moveToNext();
        //set the edit texts with the data from the cursor
        taskName.setText(textCursor.getString(textCursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_TASK_NAME)));
        taskDeadline.setText(textCursor.getString(textCursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_TASK_DEADLINE)));
        taskDescription.setText(textCursor.getString(textCursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_TASK_DESCRIPTION)));

        textCursor.close();

        //get a cursor for the employees of a specific task based on the id
        Cursor cursor=employeeDBHelper.getEmployeesOfTask(task_id);
        cursor.moveToNext();

        Set<Long> selectedEmp = new TreeSet<>();
        //add the employees ids to a set and return it
        while (!cursor.isAfterLast()) {
            selectedEmp.add(cursor.getLong(
                    cursor.getColumnIndex
                            (EmployeeContract.TABLE_NAME + EmployeeContract.EmployeeEntry._ID)));
            cursor.moveToNext();
        }
        cursor.close();
        return selectedEmp;
    }

    @Override
    public boolean saveData(String task_name, int task_evaluation, String task_description, String task_deadline, ArrayList<Long> employees_ids) {
        return employeeDBHelper.updateTask((int)task_id,task_name,task_evaluation,task_description,task_deadline,employees_ids);

    }
}
