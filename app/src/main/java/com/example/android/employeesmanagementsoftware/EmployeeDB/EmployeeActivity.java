package com.example.android.employeesmanagementsoftware.EmployeeDB;


import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.android.employeesmanagementsoftware.DepartmentDB.TaskAdapter;
import com.example.android.employeesmanagementsoftware.R;
import com.example.android.employeesmanagementsoftware.data.Contracts.EmployeeContract;
import com.example.android.employeesmanagementsoftware.data.Contracts.EmployeeContract.EmployeeEntry;
import com.example.android.employeesmanagementsoftware.data.Contracts.TaskContract;
import com.example.android.employeesmanagementsoftware.data.DBHelpers.EmployeesManagementDbHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created  by Monica on on 7/10/2018.
 */
//TODO notify,date mn calendar,validation
    //TODO  fragment tas0k
    //TODO evaluation float msh int

    //delete ,scroll,tasks,performance

public class EmployeeActivity  extends AppCompatActivity {
    private EmployeesManagementDbHelper helper;
    private EditText name,email,phone,birthday,job;
    private CustomEditTextWithBullets notes;
    private ImageView image;
    private RatingBar performanceRatBar;
    private ListView tasksList;
    private long employeeId;
    private String picturePath;
    private static int RESULT_LOAD_IMAGE = 1;
    private boolean imgChanged = false;
    private static final int PICK_FROM_GALLERY = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee);

        name = (EditText)findViewById(R.id.employee_name);
        email = (EditText)findViewById(R.id.email);
        phone = (EditText)findViewById(R.id.phone);
        birthday = (EditText)findViewById(R.id.birthday);
        job = (EditText)findViewById(R.id.post);
        notes = (CustomEditTextWithBullets)findViewById(R.id.notes);
        image = (ImageView)findViewById(R.id.employee_icon);
        image.setTag("");
        picturePath = new String();
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    if (ActivityCompat.checkSelfPermission(EmployeeActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(EmployeeActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FROM_GALLERY);
                    } else {
                        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(galleryIntent, PICK_FROM_GALLERY);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });



        helper = new EmployeesManagementDbHelper(this);

       Intent intent = getIntent();
        employeeId = intent.getExtras().getLong("employeeId");
        Log.i("id"," id = "+employeeId);
       setEmployee();
       setEmployeeTasks();
    }

    private void setEmployeeTasks(){
        tasksList = (ListView)findViewById(R.id.tasks_list);
       Cursor cursor = helper.getTasksOfEmployee(employeeId);
        CursorAdapter tasksAdapter = new TaskAdapter(this,cursor);
        tasksList.setAdapter(tasksAdapter);
       setPerformance(cursor);

    //    cursor.close();


    }

    private void setPerformance(Cursor cursor){

        int performance = 0;

        if(cursor.moveToFirst()&&cursor.getCount()>0){
            for(int i = 1;i<= cursor.getCount();i++){
                performance += cursor.getInt(cursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_TASK_EVALUATION));
            }


float res = (float)performance/cursor.getCount();

            performanceRatBar = (RatingBar)findViewById(R.id.ratingBar_employee);
            performanceRatBar.setRating(res);
        }

      // cursor.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_employee, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
              editEmployee();
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
              showDeleteConfirmationDialog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults)
    {
        switch (requestCode) {
            case PICK_FROM_GALLERY:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, PICK_FROM_GALLERY);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {

            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            cursor.close();
            image.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            imgChanged = true;
            image.setTag(picturePath);
        }
    }

    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to fire this employee ?");
        builder.setPositiveButton("Fire", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
             if( helper.deleteEmployee(employeeId))
                finish();
             else
                 Toast.makeText(getApplicationContext(),"Can't fire this employee",Toast.LENGTH_LONG).show();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void setEmployee(){
        Cursor cursor = helper.getEmployee(employeeId);

        //setting data of employee
        if(cursor.moveToFirst()){
            name.setText(cursor.getString(cursor.getColumnIndex(EmployeeEntry.COLUMN_EMPLOYEE_NAME)));
            email.setText(cursor.getString(cursor.getColumnIndex(EmployeeEntry.COLUMN_EMPLOYEE_EMAIL)));
            phone.setText(cursor.getString(cursor.getColumnIndex(EmployeeEntry.COLUMN_EMPLOYEE_PHONE)));
            birthday.setText(cursor.getString(cursor.getColumnIndex(EmployeeEntry.COLUMN_EMPLOYEE_BIRTHDATE)));
            job.setText(cursor.getString(cursor.getColumnIndex(EmployeeEntry.COLUMN_EMPLOYEE_JOB)));
            notes.setText(cursor.getString(cursor.getColumnIndex(EmployeeEntry.COLUMN_EMPLOYEE_NOTES)));
            String path = cursor.getString(cursor.getColumnIndex(EmployeeEntry.COLUMN_EMPLOYEE_PHOTO));
            if(!TextUtils.isEmpty(path) && (new File(path)).exists()){
                image.setImageBitmap(BitmapFactory.decodeFile(path));
            }else{
                image.setImageResource(R.drawable.unknown);
            }
        }
        cursor.close();
    }


    private void editEmployee(){

        ContentValues values = new ContentValues();

        values.put(EmployeeEntry.COLUMN_EMPLOYEE_NAME,name.getText().toString().trim());
        values.put(EmployeeEntry.COLUMN_EMPLOYEE_BIRTHDATE,birthday.getText().toString().trim());
        values.put(EmployeeEntry.COLUMN_EMPLOYEE_JOB,job.getText().toString().trim());
        values.put(EmployeeEntry.COLUMN_EMPLOYEE_EMAIL,email.getText().toString().trim());
        values.put(EmployeeEntry.COLUMN_EMPLOYEE_NOTES,notes.getText().toString().trim());
        values.put(EmployeeEntry.COLUMN_EMPLOYEE_PHONE,phone.getText().toString().trim());
        if(imgChanged){
            values.put(EmployeeEntry.COLUMN_EMPLOYEE_PHOTO,image.getTag().toString());
        }

      if( helper.updateEmployee(employeeId,values)){
            finish();
       }


    }



}
