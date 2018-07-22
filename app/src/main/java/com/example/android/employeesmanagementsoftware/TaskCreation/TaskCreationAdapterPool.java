package com.example.android.employeesmanagementsoftware.TaskCreation;


import android.content.Context;

import com.example.android.employeesmanagementsoftware.data.DBHelpers.EmployeesManagementDbHelper;

import java.util.ArrayList;
import java.util.Set;

class TaskCreationAdapterPool {

    private ArrayList<TaskCreationAdapter> pool;
    private EmployeesManagementDbHelper dbHelper;
    private Context context;
    private Set<String> employees;

    TaskCreationAdapterPool(EmployeesManagementDbHelper dbHelper, Context context, Set<String> employees) {
        this.dbHelper=dbHelper;
        this.context=context;
        this.employees=employees;
        pool=new ArrayList<>();
    }
    TaskCreationAdapter getAdapter(int depID){
        if (pool.get(depID)==null)
            pool.add(depID,new TaskCreationAdapter(context,dbHelper.getEmployessOfDepartment(depID),employees));
        return pool.get(depID);
    }



}
