package com.example.android.employeesmanagementsoftware.data.Contracts;

import android.provider.BaseColumns;


public class DepartmentContract {

    public static final String TABLE_NAME = "department";

    public static final class DepartmentEntry implements BaseColumns{
        //DepartmentEntry class for deifning column names of Department table
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_DEPARTMENT_NAME = "name";
        public static final String COLUMN_DEPARTMENT_DESCRIPTION = "description" ;

    }
}
