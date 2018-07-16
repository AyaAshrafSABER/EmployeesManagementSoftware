package com.example.android.employeesmanagementsoftware.data.DBHelpers;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import com.example.android.employeesmanagementsoftware.data.Contracts.DepartmentContract;
import com.example.android.employeesmanagementsoftware.data.Contracts.EmployeeContract;
import com.example.android.employeesmanagementsoftware.data.Contracts.EmployeeContract.EmployeeEntry;
import com.example.android.employeesmanagementsoftware.data.Contracts.TaskContract;
import com.example.android.employeesmanagementsoftware.data.Contracts.TaskContract.TaskEntry;
import com.example.android.employeesmanagementsoftware.data.Contracts.DepartmentContract.DepartmentEntry;

public class EmployeesManagementDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "employees_management.db";

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;

    public EmployeesManagementDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //This is called when the database is created for the first time
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the employee table
        String SQL_CREATE_EMPLOYEE_TABLE = "CREATE TABLE " + EmployeeContract.TABLE_NAME + "("
                + EmployeeEntry._ID + "INTEGER PRIMARY KEY AUTOINCREMENT,"
                + EmployeeEntry.COLUMN_EMPLOYEE_NAME + " VARCHAR(70) NOT NULL, "
                + EmployeeEntry.COLUMN_EMPLOYEE_PHONE + " INTEGER,"
                + EmployeeEntry.COLUMN_EMPLOYEE_EMAIL + " VARCHAR(255),"
                + EmployeeEntry.COLUMN_EMPLOYEE_BIRTHDATE + " DATE NOT NULL,"
                + EmployeeEntry.COLUMN_EMPLOYEE_DEPARTMENT_ID + " INTEGER NOT NULL,"
                + EmployeeEntry.COLUMN_EMPLOYEE_JOB + " VARCHAR(50) /*NOT NULL*/,"
                + EmployeeEntry.COLUMN_EMPLOYEE_PHOTO + " VARCHAR(255), "
                + "FOREIGN KEY(" + EmployeeEntry.COLUMN_EMPLOYEE_DEPARTMENT_ID + ") REFERENCES " + DepartmentContract.TABLE_NAME + "(" + DepartmentEntry._ID + ")"
                + ");";

        // Create a String that contains the SQL statement to create the department table
        String SQL_CREATE_DEPARTMENT_TABLE = "CREATE TABLE " + DepartmentContract.TABLE_NAME+"("
                +DepartmentEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +DepartmentEntry.COLUMN_DEPARTMENT_NAME + " VARCHAR(255) NOT NULL, "
                +DepartmentEntry.COLUMN_DEPARTMENT_DESCRIPTION + "  VARCHAR(300) "
                +");";

        // Create a String that contains the SQL statement to create the task table
        String SQL_CREATE_TASK_TABLE = "CREATE TABLE " + TaskContract.TABLE_NAME + "("
                +TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +TaskEntry.COLUMN_TASK_NAME + " VARCHAR(70) NOT NULL, "
                +TaskEntry.COLUMN_TASK_DESCRIPTION + " VARCHAR(300), "
                +TaskEntry.COLUMN_TASK_DEADLINE + " DATETIME ,"
                +TaskEntry.COLUMN_TASK_EVALUATION + "INTEGER NOT NULL"
                +");"
                ;
        // Create a String that contains the SQL statement to create the employee_task table
        String SQL_CREATE_EMPLOYEE_TASK_TABLE = "CREATE TABLE " + "employee_department " + "( "
                + EmployeeEntry._ID + " INTEGER NOT NULL, "
                + TaskEntry._ID+ " INTEGER NOT NULL, "
                + "FOREIGN KEY (" + EmployeeEntry._ID + ") REFERENCES " + EmployeeContract.TABLE_NAME + "(" + EmployeeEntry._ID + "), "
                + "FOREIGN KEY (" + TaskEntry._ID + ") REFERENCES " + TaskContract.TABLE_NAME +"(" + TaskEntry._ID + ") "
                +");"
                ;


        //executes SQL create statements
        db.execSQL(SQL_CREATE_DEPARTMENT_TABLE);
        db.execSQL(SQL_CREATE_EMPLOYEE_TABLE);
        db.execSQL(SQL_CREATE_TASK_TABLE);
        db.execSQL(SQL_CREATE_EMPLOYEE_TASK_TABLE);


    }


    //This is called when the database needs to be upgraded.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here.
        // DATABASE_VERSION ++;
    }


}