package com.example.android.employeesmanagementsoftware.TaskCreation;


import android.app.Activity;

import com.example.android.employeesmanagementsoftware.data.DBHelpers.EmployeesManagementDbHelper;

class TaskCreationCommandUtil {

    private Activity activity;
    private EmployeesManagementDbHelper employeeDBHelper;

    TaskCreationCommandUtil(Activity activity, EmployeesManagementDbHelper employeeDBHelper) {
        this.activity=activity;

        this.employeeDBHelper = employeeDBHelper;
    }

    //return a suitable TaskCreationCommandObject based on the task's id
    TaskCreationCommand getCommander(long task_id){

        return task_id==-1?new NewCommand():new EditCommand(activity,employeeDBHelper,task_id);

    }
}
