package com.example.android.employeesmanagementsoftware.data.Contracts;

import android.provider.BaseColumns;


public class EmployeeContract {

    public static final String TABLE_NAME = "employee" ;

    public static final class EmployeeEntry implements BaseColumns{
        //EmployeeEntry class for deifning column names of Employee table
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_EMPLOYEE_NAME = "name";
        public static final String COLUMN_EMPLOYEE_BIRTHDATE = "birthdate";
        public static final String COLUMN_EMPLOYEE_PHONE = "phone";
        public static final String COLUMN_EMPLOYEE_EMAIL = "email";
        public static final String COLUMN_EMPLOYEE_DEPARTMENT_ID = "department_id";

    }
}
