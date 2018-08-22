package com.example.android.employeesmanagementsoftware.TaskCreation;


import android.database.Cursor;

import com.example.android.employeesmanagementsoftware.data.DBHelpers.EmployeesManagementDbHelper;

//A wrapper class to wrap the needed parameters
class SpinnerTaskParams {
    private EmployeesManagementDbHelper employeeDBHelper;
    private TaskCreationAdapterPool adapterPool;
    private Cursor cursor;


    SpinnerTaskParams(EmployeesManagementDbHelper employeeDBHelper, TaskCreationAdapterPool adapterPool) {
        this.employeeDBHelper = employeeDBHelper;
        this.adapterPool = adapterPool;
    }

    EmployeesManagementDbHelper getEmployeeDBHelper() {
        return employeeDBHelper;
    }

    TaskCreationAdapterPool getAdapterPool() {
        return adapterPool;
    }
    SpinnerTaskParams setCursor(Cursor cursor) {
        this.cursor = cursor;
        return this;
    }

    Cursor getCursor() {
        return cursor;
    }
}
