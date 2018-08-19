package com.example.android.employeesmanagementsoftware.data.DBHelpers;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.util.Log;

import com.example.android.employeesmanagementsoftware.DepartmentDB.DepartementRowData.DepartmentData;
import com.example.android.employeesmanagementsoftware.data.Contracts.DepartmentContract;
import com.example.android.employeesmanagementsoftware.data.Contracts.EmployeeContract;
import com.example.android.employeesmanagementsoftware.data.Contracts.EmployeeContract.EmployeeEntry;
import com.example.android.employeesmanagementsoftware.data.Contracts.TaskContract;
import com.example.android.employeesmanagementsoftware.data.Contracts.TaskContract.TaskEntry;
import com.example.android.employeesmanagementsoftware.data.Contracts.DepartmentContract.DepartmentEntry;
import com.example.android.employeesmanagementsoftware.data.DBHelpers.EmployeesManagementDbHelper;
import com.example.android.employeesmanagementsoftware.data.Contracts.TaskContract;
import com.example.android.employeesmanagementsoftware.data.Contracts.TaskContract.TaskEntry;
import com.example.android.employeesmanagementsoftware.DepartmentDB.DepartementRowData.DepartmentData.DepartmentItem;


import java.util.ArrayList;
import java.util.*;

// to use insert or get methods
// Create  EmployeesManagementDbHelper instance first

public class EmployeesManagementDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "employees_management.db";
    private static final String TAG="helper";

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
                + EmployeeEntry.COLUMN_EMPLOYEE_NOTES + " VARCHAR(1024), "
                + "FOREIGN KEY(" + EmployeeEntry.COLUMN_EMPLOYEE_DEPARTMENT_ID + ") REFERENCES " + DepartmentContract.TABLE_NAME + "(" + DepartmentEntry._ID + ")"
                + ");";

        // Create a String that contains the SQL statement to create the department table
        String SQL_CREATE_DEPARTMENT_TABLE = "CREATE TABLE " + DepartmentContract.TABLE_NAME+"("
                +DepartmentEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +DepartmentEntry.COLUMN_DEPARTMENT_NAME + " VARCHAR(255) NOT NULL UNIQUE, "
                +DepartmentEntry.COLUMN_DEPARTMENT_DESCRIPTION + "  VARCHAR(300) "
                +");";

        // Create a String that contains the SQL statement to create the task table
        String SQL_CREATE_TASK_TABLE = "CREATE TABLE " + TaskContract.TABLE_NAME + "("
                +TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +TaskEntry.COLUMN_TASK_NAME + " VARCHAR(70) NOT NULL, "
                +TaskEntry.COLUMN_TASK_DESCRIPTION + " VARCHAR(300), "
                +TaskEntry.COLUMN_TASK_DEADLINE + " DATETIME ,"
                +TaskEntry.COLUMN_TASK_DATE + " DATETIME ,"
                +TaskEntry.COLUMN_TASK_INSTRUCTOR + " VARCHAR(300),"
                +TaskEntry.COLUMN_TASK_EVALUATION+" INTEGER"
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



    public Cursor getEmployeesOfTask(long task_id){
        SQLiteDatabase db  = this.getReadableDatabase();

        String [] columns = {
                EmployeeContract.TABLE_NAME+EmployeeEntry._ID
        };
        String selection = TaskContract.TABLE_NAME+TaskEntry._ID + " =?"; //where TaskEntry._ID=task_id
        String selectionArgs[] = { String.valueOf(task_id)  };


        //cursor is a table containing the rows returned form the query

        return db.query("employee_task",columns,selection,selectionArgs,null,null,null); //don't forget to close the cursor after usage

    }

    public Cursor getTasksOfDepartment(long department_id){
        //gets tasks of a specific deparrtment
        SQLiteDatabase db  = this.getReadableDatabase(); //get readable instance of the db

        String select = "SELECT " +
                TaskContract.TABLE_NAME+"."+TaskEntry.COLUMN_TASK_NAME + " , "+
                EmployeeContract.TABLE_NAME+"."+EmployeeEntry.COLUMN_EMPLOYEE_NAME +" , "+
                TaskContract.TABLE_NAME+"."+ TaskEntry.COLUMN_TASK_DESCRIPTION +" , "+
                TaskContract.TABLE_NAME+"."+TaskEntry.COLUMN_TASK_DEADLINE +" , "+
                TaskContract.TABLE_NAME+"."+TaskEntry.COLUMN_TASK_DATE +" , "+
                TaskContract.TABLE_NAME+"."+TaskEntry.COLUMN_TASK_EVALUATION +" , "+
                TaskContract.TABLE_NAME+"."+TaskEntry.COLUMN_TASK_INSTRUCTOR +" , "+
                TaskContract.TABLE_NAME+TaskEntry._ID +" , "+
                EmployeeContract.TABLE_NAME+"."+EmployeeEntry._ID +" , "+
                EmployeeContract.TABLE_NAME+"."+EmployeeEntry.COLUMN_EMPLOYEE_DEPARTMENT_ID +" , "+
                DepartmentContract.TABLE_NAME+"."+DepartmentEntry._ID +" , "+
                "employee_task."+EmployeeContract.TABLE_NAME+EmployeeEntry._ID+" , "+
                "employee_task."+TaskContract.TABLE_NAME+TaskEntry._ID
                ;


        String from = " from "+ DepartmentContract.TABLE_NAME + " INNER JOIN " + EmployeeContract.TABLE_NAME
                + " ON " +EmployeeContract.TABLE_NAME+"."+EmployeeEntry.COLUMN_EMPLOYEE_DEPARTMENT_ID+ " = "
                + DepartmentContract.TABLE_NAME+"."+DepartmentEntry._ID
                + " INNER JOIN " + " employee_task "
                + " ON " +EmployeeContract.TABLE_NAME+"."+EmployeeEntry._ID+ " = "
                + " employee_task."+EmployeeContract.TABLE_NAME+EmployeeEntry._ID
                + " INNER JOIN " + TaskContract.TABLE_NAME
                + " ON " +TaskContract.TABLE_NAME+"."+TaskEntry._ID+ " = "
                + " employee_task."+TaskContract.TABLE_NAME+TaskEntry._ID
                ;

        String where = " WHERE "+DepartmentContract.TABLE_NAME+"."+DepartmentEntry._ID + " = " + String.valueOf(department_id);

        String query = select+from+where;

        Cursor cursor  =db.rawQuery(query,null);
        return cursor; //don't forget to close the cursor after usage
/*
String query
        //specify the columns to be read
        String [] columns = {
                TaskContract.TABLE_NAME+"."+TaskEntry.COLUMN_TASK_NAME,
                EmployeeContract.TABLE_NAME+"."+EmployeeEntry.COLUMN_EMPLOYEE_NAME,
                TaskEntry.COLUMN_TASK_DESCRIPTION,
                TaskEntry.COLUMN_TASK_DEADLINE,
                TaskEntry.COLUMN_TASK_DATE,
                TaskEntry.COLUMN_TASK_EVALUATION,
                TaskEntry.COLUMN_TASK_INSTRUCTOR,
                TaskContract.TABLE_NAME+TaskEntry._ID,
                EmployeeContract.TABLE_NAME+"."+EmployeeEntry._ID,
                EmployeeContract.TABLE_NAME+"."+EmployeeEntry.COLUMN_EMPLOYEE_DEPARTMENT_ID,
                DepartmentContract.TABLE_NAME+"."+DepartmentEntry._ID
        };
        String selection = DepartmentContract.TABLE_NAME+DepartmentEntry._ID + " =?"; //where statement
        String selectionArgs[] = { String.valueOf(department_id)  };
        String orderBy = TaskEntry.COLUMN_TASK_NAME+ " ASC";
*/
        //    Cursor cursor = db.query(DepartmentContract.TABLE_NAME+ " , "+EmployeeContract.TABLE_NAME + " , employee_task , "+TaskContract.TABLE_NAME ,columns,selection,selectionArgs,null,null,orderBy); //don't forget to close the cursor after usage



        //cursor is a table containing the rows returned form the query



    }



    public Cursor getAllTasksCursor(){
        //gets all tasks
        SQLiteDatabase db  = this.getReadableDatabase(); //get readable instance of the db

        //specify the columns to be read
        String [] columns = {
                TaskEntry._ID,
                TaskEntry.COLUMN_TASK_NAME,
                TaskEntry.COLUMN_TASK_DESCRIPTION,
                TaskEntry.COLUMN_TASK_DEADLINE,
                TaskEntry.COLUMN_TASK_DATE,
                TaskEntry.COLUMN_TASK_EVALUATION,
                TaskEntry.COLUMN_TASK_INSTRUCTOR
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
                TaskEntry._ID,
                TaskEntry.COLUMN_TASK_NAME,
                TaskEntry.COLUMN_TASK_DESCRIPTION,
                TaskEntry.COLUMN_TASK_DEADLINE,
                TaskEntry.COLUMN_TASK_DATE,
                TaskEntry.COLUMN_TASK_EVALUATION,
                TaskEntry.COLUMN_TASK_INSTRUCTOR
        };

        //where statement to filter quere
        String selection = TaskEntry._ID + " =?"; //where TaskEntry._ID=task_id
        String selectionArgs[] = { String.valueOf(task_id)  };


        //cursor is a table containing the rows returned form the query
        Cursor cursor =  db.query(TaskContract.TABLE_NAME,columns,selection,selectionArgs,null,null,null);

        return cursor; //don't forget to close the cursor after usage

    }

    public Cursor getAllDepartments()
    {
        //gets all departments
        SQLiteDatabase db  = this.getReadableDatabase(); //get readable instance of the db

        //specify the columns to be read
        String [] columns = {
                DepartmentEntry._ID,
                DepartmentEntry.COLUMN_DEPARTMENT_NAME,
                DepartmentEntry.COLUMN_DEPARTMENT_DESCRIPTION
        };

        String orderBy = DepartmentEntry.COLUMN_DEPARTMENT_NAME + " ASC "; //order by statement

        //cursor is a table containing the rows returned form the query
        Cursor cursor =  db.query(DepartmentContract.TABLE_NAME,columns,null,null,null,null,orderBy);

        return cursor; //don't forget to close the cursor after usage

    }
    public Cursor getDepartment(Long departmentId){
        SQLiteDatabase db  = this.getReadableDatabase(); //get readable instance of the db
        //specify the columns to be read
        String [] columns = {
                DepartmentEntry._ID,
                DepartmentEntry.COLUMN_DEPARTMENT_NAME,
                DepartmentEntry.COLUMN_DEPARTMENT_DESCRIPTION
        };
        String selection = DepartmentEntry._ID + " =?"; //where statement
        String selectionArgs[] = { String.valueOf(departmentId)  };

        return  db.query(DepartmentContract.TABLE_NAME,columns,selection,selectionArgs,null,null,null);//don't forget to close the cursor after usage

    }

    public Cursor getEmployee(Long employeeId){
        SQLiteDatabase db  = this.getReadableDatabase(); //get readable instance of the db
        //specify the columns to be read
        String [] columns = {
                EmployeeEntry._ID,
                EmployeeEntry.COLUMN_EMPLOYEE_NAME,
                EmployeeEntry.COLUMN_EMPLOYEE_PHONE,
                EmployeeEntry.COLUMN_EMPLOYEE_BIRTHDATE,
                EmployeeEntry.COLUMN_EMPLOYEE_EMAIL,
                EmployeeEntry.COLUMN_EMPLOYEE_JOB,
                EmployeeEntry.COLUMN_EMPLOYEE_PHOTO,
                EmployeeEntry.COLUMN_EMPLOYEE_NOTES
        };
        String selection = EmployeeEntry._ID + " =?"; //where statement
        String selectionArgs[] = { String.valueOf(employeeId)  };

        return  db.query(EmployeeContract.TABLE_NAME,columns,selection,selectionArgs,null,null,null);//don't forget to close the cursor after usage

    }

    public Cursor getEmployessOfDepartment(long department_id)
    {
        //gets all employees of a given department
        SQLiteDatabase db  = this.getReadableDatabase(); //get readable instance of the db

        //specify the columns to be read
        String [] columns = {
                EmployeeEntry._ID,
                EmployeeEntry.COLUMN_EMPLOYEE_NAME,
                EmployeeEntry.COLUMN_EMPLOYEE_JOB,
                EmployeeEntry.COLUMN_EMPLOYEE_DEPARTMENT_ID,
                EmployeeEntry.COLUMN_EMPLOYEE_PHOTO
        };

        String selection = EmployeeEntry.COLUMN_EMPLOYEE_DEPARTMENT_ID + " =?"; //where statement
        String selectionArgs[] = { String.valueOf(department_id)  };
        String orderBy = EmployeeEntry.COLUMN_EMPLOYEE_NAME + " ASC";


        //cursor is a table containing the rows returned form the query
        Cursor cursor = db.query(EmployeeContract.TABLE_NAME,columns,selection,selectionArgs,null,null,orderBy); //don't forget to close the cursor after usage

        return  cursor; }



    public boolean addDepartment(String department_name , String department_description)
    {
        SQLiteDatabase db = this.getWritableDatabase(); //gets writeable instance of database
        ContentValues cv  = new ContentValues(); //used for inserting an entry


        cv.put(TaskEntry.COLUMN_TASK_NAME,department_name);

        if(department_description!=null && !department_description.isEmpty()&&!department_description.trim().isEmpty()) // to be edited
            cv.put(TaskEntry.COLUMN_TASK_DESCRIPTION,department_description);

        long flag = db.insert(DepartmentContract.TABLE_NAME,null,cv); //reutrns a flag to indicate succes of insertion

        if(flag==-1) return false; //-1 if insert fails

        return true;
    }




    public boolean addEmployee(String employee_name, String employee_birthdate ,long department_id,String employee_job,String employee_email,String employee_phone,String employee_photo){
        //adds an employee entry to employee table

        SQLiteDatabase db = this.getWritableDatabase(); //gets writeable instance of database
        ContentValues cv  = new ContentValues(); //used for inserting an entry


        // no need to check for null as it is required to be provided
        cv.put(EmployeeEntry.COLUMN_EMPLOYEE_NAME,employee_name);
        cv.put(EmployeeEntry.COLUMN_EMPLOYEE_BIRTHDATE,employee_birthdate);
        cv.put(EmployeeEntry.COLUMN_EMPLOYEE_DEPARTMENT_ID,department_id);
        cv.put(EmployeeEntry.COLUMN_EMPLOYEE_JOB,employee_job);

        if(employee_email!=null && !employee_email.isEmpty()&&!employee_email.trim().isEmpty()) //checks if field is provided if not it is not added in the query
            cv.put(EmployeeEntry.COLUMN_EMPLOYEE_EMAIL,employee_email);
        if(employee_phone!=null && !employee_phone.isEmpty()&&!employee_phone.trim().isEmpty()) // to be edited
            cv.put(EmployeeEntry.COLUMN_EMPLOYEE_PHONE,employee_phone);
        if(employee_photo!=null && !employee_photo.isEmpty()&&!employee_photo.trim().isEmpty())
            cv.put(EmployeeEntry.COLUMN_EMPLOYEE_PHOTO,employee_photo);


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

        if(task_description!=null && !task_description.isEmpty()&&!task_description.trim().isEmpty())
            cv.put(TaskEntry.COLUMN_TASK_DESCRIPTION,task_description);

        if(task_deadline!=null && !task_deadline.isEmpty()&&!task_deadline.trim().isEmpty())
            cv.put(TaskEntry.COLUMN_TASK_DEADLINE,task_deadline);

        long task_id = db.insert(TaskContract.TABLE_NAME,null,cv); //reutrns a flag to indicate succes of insertion

        if(task_id==-1) return false; //-1 if insert fails

        cv = new ContentValues();
        if (emplyee_ids!=null)
        {
            for(long emp_id:emplyee_ids){
                cv.put(EmployeeContract.TABLE_NAME+EmployeeEntry._ID,emp_id);
                cv.put(TaskContract.TABLE_NAME+TaskEntry._ID,task_id);
                long flag = db.insert("employee_task",null,cv); //reutrns a flag to indicate succes of insertion
                if(flag==-1) return false;
            }
        }
        else return false;
        return true;
    }

    public boolean deleteEmployee(long employee_id){
        SQLiteDatabase db = this.getWritableDatabase(); //gets writeable instance of database

        db.delete("employee_task",EmployeeContract.TABLE_NAME+EmployeeEntry._ID+ "="+employee_id,null);
        int flag =  db.delete(EmployeeContract.TABLE_NAME,EmployeeEntry._ID + "=" + employee_id,null) ;

        //resets autoincrement
        Cursor c = db.rawQuery("SELECT MAX("+EmployeeEntry._ID+") from "+EmployeeContract.TABLE_NAME,null);
        c.moveToFirst();
        long max_id = c.getLong(0);
        db.execSQL("ALTER TABLE "+EmployeeContract.TABLE_NAME +" AUTOINCREMENT = " + String.valueOf(max_id+1)  );
        c.close();


        return flag>0;


    }

    public boolean deleteTask(long task_id){
        SQLiteDatabase db = this.getWritableDatabase(); //gets writeable instance of database
        db.delete("employee_task",TaskContract.TABLE_NAME+TaskEntry._ID+ "="+task_id,null);
        int flag = db.delete(TaskContract.TABLE_NAME,TaskEntry._ID + "=" + task_id,null);

        //resets autoincrement
        Cursor c = db.rawQuery("SELECT MAX("+TaskEntry._ID+") from "+TaskContract.TABLE_NAME,null);
        c.moveToFirst();
        long max_id = c.getLong(0);
        db.execSQL("ALTER TABLE "+TaskContract.TABLE_NAME +" AUTO_INCREMENT  = " + String.valueOf(max_id+1)  );
        c.close();


        return flag>0;

    }


    public boolean deleteDepartment(long department_id){
        SQLiteDatabase db = this.getWritableDatabase(); //gets writeable instance of database

        Cursor c = getEmployessOfDepartment(department_id);
        while(c.moveToNext()) {
            db.delete(EmployeeContract.TABLE_NAME,EmployeeEntry._ID + "  = "+c.getString(0),null);
        }
        c.close();

        //resets autoincrement for employess table
        Cursor c2 = db.rawQuery("SELECT MAX("+EmployeeEntry._ID+") from "+EmployeeContract.TABLE_NAME,null);
        c2.moveToFirst();
        long max_id = c2.getLong(0);
        db.execSQL("ALTER TABLE "+EmployeeContract.TABLE_NAME +" AUTO_INCREMENT = " + String.valueOf(max_id+1)  );
        c2.close();




        db.delete(EmployeeContract.TABLE_NAME,DepartmentEntry._ID + "=" + department_id,null);
        int flag = db.delete(DepartmentContract.TABLE_NAME,DepartmentEntry._ID + "=" + department_id,null) ;

        //resets autoincrement
        Cursor c3 = db.rawQuery("SELECT MAX("+DepartmentEntry._ID+") from "+DepartmentContract.TABLE_NAME,null);
        c3.moveToFirst();
        max_id = c2.getLong(0);
        db.execSQL("ALTER TABLE "+DepartmentContract.TABLE_NAME +" AUTO_INCREMENT = " + String.valueOf(max_id+1)  );
        c3.close();

        return flag>0;
    }

    public boolean deleteAllDepartments()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        int f2 = db.delete("employee_task",null,null);
        int f1 = db.delete(TaskContract.TABLE_NAME,null,null);
        int f3 = db.delete(EmployeeContract.TABLE_NAME,null,null);
        int f4 = db.delete(DepartmentContract.TABLE_NAME,null,null);

        return f1>0 && f2>0 && f3>0 && f4>0 ;
    }

    public boolean deleteAllTasks()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        int f2 = db.delete("employee_task",null,null);
        int f1 = db.delete(TaskContract.TABLE_NAME,null,null);

        return f1>0 && f2>0 ;
    }

    //    public boolean updateEmployee() {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(COL_1,id);
//        contentValues.put(COL_2,name);
//        contentValues.put(COL_3,surname);
//        contentValues.put(COL_4,marks);
//        db.update(EmployeeContract.TABLE_NAME, contentValues, "ID = ?",new String[] { id });
//        return true;
//    }
    public boolean updateDepartment(DepartmentItem departmentItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DepartmentEntry._ID, departmentItem.id);
        contentValues.put(DepartmentEntry.COLUMN_DEPARTMENT_NAME, departmentItem.name);
        contentValues.put(DepartmentEntry.COLUMN_DEPARTMENT_DESCRIPTION, departmentItem.details);
        db.update(DepartmentContract.TABLE_NAME, contentValues, DepartmentEntry._ID + "=" + departmentItem.id,null);
        return true;
    }
//    public boolean updateTask() {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(COL_1,id);
//        contentValues.put(COL_2,name);
//        contentValues.put(COL_3,surname);
//        contentValues.put(COL_4,marks);
//        db.update(TABLE_NAME, contentValues, "ID = ?",new String[] { id });
//        return true;
//    }


    // >>>>>>>>>>>>>>>>>>>>ZYAD<<<<<<<<<<: NEEDS TESTING BY OMAR
    public boolean updateTask(int task_id, String task_name, int task_evaluation , String task_description, String task_deadline, ArrayList<Long> employee_ids){

        SQLiteDatabase db = this.getWritableDatabase();

        SQLiteDatabase db_ = this.getReadableDatabase();
        ContentValues cv = new ContentValues();
        ArrayList <Long>list_of_current_ids= new ArrayList<>();
        ArrayList <Long>list1= new ArrayList<>();
        ArrayList <Long>list2= new ArrayList<>();

        cv.put(TaskEntry.COLUMN_TASK_NAME,task_name);
        cv.put(TaskEntry.COLUMN_TASK_EVALUATION, task_evaluation);
        cv.put(TaskEntry.COLUMN_TASK_DESCRIPTION,task_description);
        cv.put(TaskEntry.COLUMN_TASK_DEADLINE,task_deadline);
        db.update(TaskContract.TABLE_NAME, cv, TaskEntry._ID + "=" + task_id,null);


        Cursor c= db_.rawQuery("SELECT "+EmployeeContract.TABLE_NAME+EmployeeEntry._ID+" from employee_task where "+ TaskContract.TABLE_NAME+TaskEntry._ID+ "= "+ task_id, null);
        if (c.moveToFirst()){
            while(!c.isAfterLast()){
                String data = c.getString(c.getColumnIndex(EmployeeContract.TABLE_NAME+EmployeeEntry._ID));
                list_of_current_ids.add(Long.parseLong(data));
                c.moveToNext();
            }
        }
        c.close();

        list1.addAll(employee_ids);
        list2.addAll(list_of_current_ids);
        list1.removeAll(list_of_current_ids);
        list2.removeAll(employee_ids);

        if(list2.size()>0){
            for(long emp_id:list2){
                long flag= db.delete("employee_task",EmployeeContract.TABLE_NAME+EmployeeEntry._ID+ "="+emp_id,null);
                if(flag==-1) return false;
            }
        }

        if(list1.size()>0){
            cv=new ContentValues();
            for(long emp_id:list1){
                cv.put(EmployeeContract.TABLE_NAME+EmployeeEntry._ID,emp_id);
                cv.put(TaskContract.TABLE_NAME+TaskEntry._ID,task_id);
                long flag = db.insert("employee_task",null,cv); //reutrns a flag to indicate succes of insertion
                if(flag==-1) return false;
            }
        }

        return true;

    }

}