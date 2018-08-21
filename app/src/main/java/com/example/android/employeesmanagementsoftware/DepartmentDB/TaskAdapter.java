package com.example.android.employeesmanagementsoftware.DepartmentDB;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.android.employeesmanagementsoftware.R;
import com.example.android.employeesmanagementsoftware.data.Contracts.TaskContract;
import com.example.android.employeesmanagementsoftware.data.Contracts.TaskContract.TaskEntry;


/**
 * Created by Monica on 8/6/2018.
 * diplays title and ratingBar of each task in a specific department
 */

public class TaskAdapter extends CursorAdapter {

    public TaskAdapter(Context context, Cursor cursor){
        super(context,cursor,0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return  LayoutInflater.from(context).inflate(R.layout.task, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView name = (TextView)view.findViewById(R.id.taskName);
        name.setText(cursor.getString(0));
        RatingBar ratingBar = (RatingBar)view.findViewById(R.id.ratingBar_task);
        ratingBar.setRating(cursor.getInt(cursor.getColumnIndex(TaskEntry.COLUMN_TASK_EVALUATION)));

    }
}
