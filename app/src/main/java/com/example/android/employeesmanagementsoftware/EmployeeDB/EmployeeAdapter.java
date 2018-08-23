package com.example.android.employeesmanagementsoftware.EmployeeDB;

import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.employeesmanagementsoftware.R;
import com.example.android.employeesmanagementsoftware.data.Contracts.EmployeeContract.EmployeeEntry;

import java.io.File;

/**
 * Created by Monica on 8/6/2018.
 * displays name,post and image of each employee in a specific department
 */

public class EmployeeAdapter extends CursorAdapter {

    public EmployeeAdapter(Context context,Cursor cursor){
        super(context,cursor,0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return  LayoutInflater.from(context).inflate(R.layout.employee_item, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView name = (TextView)view.findViewById(R.id.name);
        name.setText(cursor.getString(1));

        TextView job = (TextView)view.findViewById(R.id.post);
        String post = cursor.getString(2).trim();
        if(post.isEmpty() || post == null){
            job.setText("Unknown Post");
        }else{
            job.setText(post);
        }


        ImageView icon = (ImageView)view.findViewById(R.id.icon);

        //saving img path in database as string
        //in case no img was chosen or chosen img is deleted , employee's img is set to default one "unknown"
        String path = cursor.getString(3);
        if(!TextUtils.isEmpty(path) && (new File(path)).exists()){
            icon.setImageBitmap(BitmapFactory.decodeFile(path));
        }else{
            icon.setImageResource(R.drawable.unknown);
        }



    }
}
