package com.example.android.employeesmanagementsoftware.data.Contracts;

import android.provider.BaseColumns;


public class TaskContract {

    public static final String TABLE_NAME = "task";

    public static final class TaskEntry implements BaseColumns{
        //TaskEntry class for deifning column names of Task table
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_TASK_NAME = "name";
        public static final String COLUMN_TASK_DESCRIPTION = "description";
        public static final String COLUMN_TASK_DEADLINE = "deadline";
        public static final String COLUMN_TASK_EVALUATION = "evaluation" ;

    }

}

