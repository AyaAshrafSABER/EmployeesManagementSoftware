package com.example.android.employeesmanagementsoftware.TaskCreation;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.android.employeesmanagementsoftware.R;

class CustomAdapter extends ArrayAdapter<String> {


    public CustomAdapter(@NonNull Context context, String []strings) {
        super(context, R.layout.task_creation_row,strings);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater=LayoutInflater.from(getContext());//get context of adapter
        View customView= inflater.inflate(R.layout.task_creation_row,parent,false);

        //getting references to widgets
        TextView employeeText=(TextView) customView.findViewById(R.id.employee_name_text);
        CheckBox employeeCheckBox=(CheckBox) customView.findViewById(R.id.employee_check_box);
        //setting the the text of each row in the list according to its position
        employeeText.setText(getItem(position));
        return customView;

    }

}
