package com.example.android.employeesmanagementsoftware.EmployeeDB;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.EditText;

import com.example.android.employeesmanagementsoftware.R;

/**
 * Created by User on 8/15/2018.
 */

public class NoteAdapter extends CursorAdapter {


    public NoteAdapter(Context context, Cursor cursor){
        super(context,cursor,0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return  LayoutInflater.from(context).inflate(R.layout.note, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        EditText note = (EditText)view.findViewById(R.id.note_edit_text);

      //TODO Note table  note.setText(cursor.getString(cursor.getColumnIndex()));

    }
}
