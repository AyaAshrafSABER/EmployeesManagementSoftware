package com.example.android.employeesmanagementsoftware.taskDB;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.android.employeesmanagementsoftware.R;
import com.example.android.employeesmanagementsoftware.data.Contracts.TaskContract.TaskEntry;
import com.example.android.employeesmanagementsoftware.data.DBHelpers.EmployeesManagementDbHelper;
import java.util.ArrayList;

/*
made by menna
 */

public class TasksFragment extends Fragment {
    private static final String ARG_COLUMN_COUNT = "column-count";
    private EmployeesManagementDbHelper employeeDBHelper;
    private ArrayList<Tasks> mValues;
    private Cursor cursor;
    private static RecyclerView recyclerView;
    private static TasksAdapter mAdapter;
    public TasksFragment() {

    }
    public static TasksFragment newInstance(int columnCount) {
        TasksFragment fragment = new TasksFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
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
        recyclerView =  view.findViewById(R.id.eventlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ArrayList<Tasks> tasks = new ArrayList<Tasks>();
        Cursor cursor =employeeDBHelper.getAllTasksCursor();
       if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Tasks task = new Tasks();
                task.setId(cursor.getString(cursor.getColumnIndex(TaskEntry._ID)));
                task.setTaskName(cursor.getString(cursor.getColumnIndex(TaskEntry.COLUMN_TASK_NAME)));
                task.setTaskDetails(cursor.getString(cursor.getColumnIndex(TaskEntry.COLUMN_TASK_DESCRIPTION)));
                task.setTaskDeadline(cursor.getString(cursor.getColumnIndex(TaskEntry.COLUMN_TASK_DEADLINE)));
                task.setTaskDate(cursor.getString(cursor.getColumnIndex(TaskEntry.COLUMN_TASK_DATE)));
                task.setEvaluation(cursor.getInt(cursor.getColumnIndex(TaskEntry.COLUMN_TASK_EVALUATION)));
                if (cursor.getInt(cursor.getColumnIndex(TaskEntry.COLUMN_TASK_COMPLETED)) == 0){
                    task.setDone(false);
                }else {
                    task.setDone(true);
                }
                tasks.add(task);
                cursor.moveToNext();}
        }
        cursor.close();
         mAdapter = new TasksAdapter(getActivity(),tasks);
        recyclerView.setAdapter(mAdapter);
    }
    public void updateTasksList(EmployeesManagementDbHelper mDataBase){
        mValues = new ArrayList<>();
        cursor =  mDataBase.getAllTasksCursor();
        if (cursor.moveToFirst()) {
            do {
                Tasks task = new Tasks();
                task.setId(cursor.getString(cursor.getColumnIndex(TaskEntry._ID)));
                task.setTaskName(cursor.getString(cursor.getColumnIndex(TaskEntry.COLUMN_TASK_NAME)));
                task.setTaskDetails(cursor.getString(cursor.getColumnIndex(TaskEntry.COLUMN_TASK_DESCRIPTION)));
                task.setTaskDeadline(cursor.getString(cursor.getColumnIndex(TaskEntry.COLUMN_TASK_DEADLINE)));
                task.setTaskDate(cursor.getString(cursor.getColumnIndex(TaskEntry.COLUMN_TASK_DATE)));
                task.setEvaluation(cursor.getInt(cursor.getColumnIndex(TaskEntry.COLUMN_TASK_EVALUATION)));
                mValues.add(task);
            } while (cursor.moveToNext());
        }
        if (mAdapter == null) {
            mAdapter = new TasksAdapter(getActivity(),mValues);
            recyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
        cursor.close();
    }


}
