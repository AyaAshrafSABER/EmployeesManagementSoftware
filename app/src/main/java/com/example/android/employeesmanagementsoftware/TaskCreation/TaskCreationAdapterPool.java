package com.example.android.employeesmanagementsoftware.TaskCreation;


import android.content.Context;
import android.database.Cursor;
import android.util.SparseArray;

import com.example.android.employeesmanagementsoftware.data.DBHelpers.EmployeesManagementDbHelper;

import java.util.Set;

class TaskCreationAdapterPool {
    public final String TAG = "pool";
    private SparseArray<TaskCreationAdapter> pool;
    private EmployeesManagementDbHelper dbHelper;
    private Context context;
    private Set<Long> employees;

    TaskCreationAdapterPool(EmployeesManagementDbHelper dbHelper, Context context, Set<Long> employees) {
        this.dbHelper = dbHelper;
        this.context = context;
        this.employees = employees;
        pool = new SparseArray<>();
    }

    TaskCreationAdapter getAdapter(final int depID) {

        if (pool.get(depID) == null) {
            Cursor cursor = dbHelper.getEmployessOfDepartment(depID);
            pool.append(depID, new TaskCreationAdapter(context, cursor, employees));

        }
        return pool.get(depID);
    }


}
