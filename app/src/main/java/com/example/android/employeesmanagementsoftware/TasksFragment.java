package com.example.android.employeesmanagementsoftware;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.employeesmanagementsoftware.data.DBHelpers.EmployeesManagementDbHelper;

import java.util.ArrayList;

/*
made by menna
 */

public class TasksFragment extends Fragment {
    private EmployeesManagementDbHelper employeeDBHelper;
    public TasksFragment() {

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        employeeDBHelper = new EmployeesManagementDbHelper(this.getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tasks, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.eventlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ArrayList<Tasks> tasks = new ArrayList<Tasks>();
        Cursor cursor =employeeDBHelper.getAllTasksCursor();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Tasks task = new Tasks();
                task.setId(new Long(cursor.getString(0)));
                task.setTaskName(cursor.getString(1));
                task.setTaskDetails(cursor.getString(2));
                task.setTaskDeadline(cursor.getString(3));
                task.setTaskDate(cursor.getString(4));
                task.setTaskInstractor(cursor.getString(5));
                tasks.add(task);
                cursor.moveToNext();}
        }
        cursor.close();
        recyclerView.setAdapter(new TasksAdapter(getActivity(),tasks));
    }


}
