package com.example.android.employeesmanagementsoftware.data.DBHelpers;

import android.content.ContentValues;
import android.database.Cursor;
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
import com.example.android.employeesmanagementsoftware.data.DBHelpers.EmployeesManagementDbHelper;
import com.example.android.employeesmanagementsoftware.data.Contracts.TaskContract;
import com.example.android.employeesmanagementsoftware.data.Contracts.TaskContract.TaskEntry;

import java.util.ArrayList;


// to use insert or get methods
// Create  EmployeesManagementDbHelper instance first

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
                + EmployeeEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + EmployeeEntry.COLUMN_EMPLOYEE_NAME + " VARCHAR(70) NOT NULL, "
                + EmployeeEntry.COLUMN_EMPLOYEE_BIRTHDATE + " DATE NOT NULL,"
                +EmployeeEntry.COLUMN_EMPLOYEE_DEPARTMENT_ID + " INTEGER NOT NULL,"
                + EmployeeEntry.COLUMN_EMPLOYEE_JOB + " VARCHAR(50) NOT NULL,"
                + EmployeeEntry.COLUMN_EMPLOYEE_PHONE + " VARCHAR(20),"
                + EmployeeEntry.COLUMN_EMPLOYEE_EMAIL + " VARCHAR(255),"
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
        String SQL_CREATE_EMPLOYEE_TASK_TABLE = "CREATE TABLE " + "employee_task " + "( "
                + EmployeeContract.TABLE_NAME+EmployeeEntry._ID + " INTEGER NOT NULL, "
                + TaskContract.TABLE_NAME+TaskEntry._ID+ " INTEGER NOT NULL, "
                + "FOREIGN KEY (" + EmployeeContract.TABLE_NAME+EmployeeEntry._ID + ") REFERENCES " + EmployeeContract.TABLE_NAME + "(" + EmployeeEntry._ID + "), "
                + "FOREIGN KEY (" + TaskContract.TABLE_NAME+TaskEntry._ID + ") REFERENCES " + TaskContract.TABLE_NAME +"(" + TaskEntry._ID + ") "
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

    public Cursor getAllTasksCursor(){
        //gets all tasks
        SQLiteDatabase db  = this.getReadableDatabase(); //get readable instance of the db

        //specify the columns to be read
        String [] columns = {
                TaskEntry.COLUMN_TASK_NAME,
                TaskEntry.COLUMN_TASK_DESCRIPTION,
                TaskEntry.COLUMN_TASK_EVALUATION,
                TaskEntry.COLUMN_TASK_DEADLINE
        };

        //cursor is a table containing the rows returned form the query
        Cursor cursor =  db.query(TaskContract.TABLE_NAME,columns,null,null,null,null,null);

        return cursor; //don't forget to close the cursor after usage

    }

    public Cursor getSpecifiTaskCursor(long task_id){

        //gets specific task by its id
        SQLiteDatabase db  = this.getReadableDatabase(); //get readable instance of the db

        //specify the columns to be read
        String [] columns = {
                TaskEntry.COLUMN_TASK_NAME,
                TaskEntry.COLUMN_TASK_DESCRIPTION,
                TaskEntry.COLUMN_TASK_EVALUATION,
                TaskEntry.COLUMN_TASK_DEADLINE
        };

        //where statement to filter quere
        String selection = TaskEntry._ID + " =?"; //where TaskEntry._ID=task_id
        String selectionArgs[] = { String.valueOf(task_id)  };


        //cursor is a table containing the rows returned form the query
        Cursor cursor =  db.query(TaskContract.TABLE_NAME,columns,null,null,null,null,null);

        return cursor; //don't forget to close the cursor after usage

    }

    public Cursor getAllDepartments()
    {
        //gets all departments
        SQLiteDatabase db  = this.getReadableDatabase(); //get readable instance of the db

        //specify the columns to be read
        String [] columns = {
                DepartmentEntry._ID,
                DepartmentEntry.COLUMN_DEPARTMENT_NAME
        };

        String orderBy = DepartmentEntry.COLUMN_DEPARTMENT_NAME + " ASC "; //order by statement

        //cursor is a table containing the rows returned form the query
        Cursor cursor =  db.query(DepartmentContract.TABLE_NAME,columns,null,null,null,null,orderBy);

        return cursor; //don't forget to close the cursor after usage

    }

    public Cursor getEmployessOfDepartment(long department_id)
    {
        //gets all employees of a given department
        SQLiteDatabase db  = this.getReadableDatabase(); //get readable instance of the db

        //specify the columns to be read
        String [] columns = {
                EmployeeEntry._ID,
                EmployeeEntry.COLUMN_EMPLOYEE_NAME,
                EmployeeEntry.COLUMN_EMPLOYEE_DEPARTMENT_ID
        };

        String selection = EmployeeEntry.COLUMN_EMPLOYEE_DEPARTMENT_ID + " =?"; //where statement
        String selectionArgs[] = { String.valueOf(department_id)  };
        String orderBy = EmployeeEntry.COLUMN_EMPLOYEE_NAME + " ASC";


        //cursor is a table containing the rows returned form the query
        Cursor cursor =  db.query(EmployeeContract.TABLE_NAME,columns,selection,selectionArgs,null,null,orderBy);
        //removed db.close()
        return cursor; //don't forget to close the cursor after usage
    }



    public boolean addDepartment(String department_name , String department_description)
    {
        SQLiteDatabase db = this.getWritableDatabase(); //gets writeable instance of database
        ContentValues cv  = new ContentValues(); //used for inserting an entry

        if(department_name!=null && department_name!="") // to be edited
            cv.put(TaskEntry.COLUMN_TASK_NAME,department_name);
        cv.put(TaskEntry.COLUMN_TASK_DESCRIPTION,department_description);

        long flag = db.insert(DepartmentContract.TABLE_NAME,null,cv); //reutrns a flag to indicate succes of insertion

        if(flag==-1) return false; //-1 if insert fails

        return true;
    }



    public boolean addEmployee(String employee_name, String employee_birthdate ,int department_id,String employee_job,String employee_email,String employee_phone,String employee_photo){
        //adds an employee entry to employee table

        SQLiteDatabase db = this.getWritableDatabase(); //gets writeable instance of database
        ContentValues cv  = new ContentValues(); //used for inserting an entry


        // no need to check for null as it is required to be provided
        cv.put(EmployeeEntry.COLUMN_EMPLOYEE_NAME,employee_name);
        cv.put(EmployeeEntry.COLUMN_EMPLOYEE_BIRTHDATE,employee_birthdate);
        cv.put(EmployeeEntry.COLUMN_EMPLOYEE_DEPARTMENT_ID,department_id);
        cv.put(EmployeeEntry.COLUMN_EMPLOYEE_JOB,employee_job);

        if (!employee_email.isEmpty() && employee_email!= null) //checks if field is provided if not it is not added in the query
            cv.put(EmployeeEntry.COLUMN_EMPLOYEE_EMAIL,employee_email);
        if (!employee_phone.isEmpty() && employee_phone != null)
            cv.put(EmployeeEntry.COLUMN_EMPLOYEE_PHONE,employee_phone);
//        if (!employee_photo.isEmpty() && employee_photo != null)
//            cv.put(EmployeeEntry.COLUMN_EMPLOYEE_PHOTO,employee_photo);


        long flag = db.insert(EmployeeContract.TABLE_NAME,null,cv); //reutrns a flag to indicate succes of insertion

        if(flag==-1) return false; //-1 if insert fails

        return true;

    }

    public boolean addTask(String task_name, int task_evaluation , String task_description, String task_deadline, ArrayList<Long> emplyee_ids)
    {
        //adds task to db
        SQLiteDatabase db = this.getWritableDatabase(); //gets writeable instance of database
        ContentValues cv  = new ContentValues(); //used for inserting an entry

        cv.put(TaskEntry.COLUMN_TASK_NAME,task_name);
        cv.put(TaskEntry.COLUMN_TASK_EVALUATION, task_evaluation);

        if(task_deadline!=null)
            cv.put(TaskEntry.COLUMN_TASK_DESCRIPTION,task_description);

        if(task_deadline!=null)
            cv.put(TaskEntry.COLUMN_TASK_DEADLINE,task_deadline);

        long task_id = db.insert(TaskContract.TABLE_NAME,null,cv); //reutrns a flag to indicate succes of insertion

        if(task_id==-1) return false; //-1 if insert fails

        cv = new ContentValues();
        if (emplyee_ids!=null)
        {
            for(long emp_id:emplyee_ids){
                cv.put(EmployeeEntry._ID,emp_id);
                cv.put(TaskEntry._ID,task_id);
                long flag = db.insert("employee_task",null,cv); //reutrns a flag to indicate succes of insertion
                if(flag==-1) return false;
            }
        }
        else return false;
        return true;
    }
}