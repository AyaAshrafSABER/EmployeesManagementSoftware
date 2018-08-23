package com.example.android.employeesmanagementsoftware.taskDB;

import android.annotation.SuppressLint;
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
    private static TasksFragment fragment ;
    private EmployeesManagementDbHelper employeeDBHelper;
    private ArrayList<Task> mValues;
    private RecyclerView recyclerView;
    private TasksAdapter mAdapter;
    @SuppressLint("ValidFragment")
    private TasksFragment() {

    }
    public static TasksFragment newInstance() {
        if (fragment == null){
             fragment = new TasksFragment();
        }
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        employeeDBHelper = new EmployeesManagementDbHelper(this.getContext());
        Cursor cursor =employeeDBHelper.getAllTasksCursor();
        mValues = new ArrayList<Task>();
        setmValues(cursor);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tasks, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView =  view.findViewById(R.id.task_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new TasksAdapter(getActivity(),mValues);
        recyclerView.setAdapter(mAdapter);
    }

    private void setmValues (Cursor cursor){
        mValues = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Task task = new Task();
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
                mValues.add(task);
                cursor.moveToNext();}
        }
        cursor.close();

    }
    public boolean updateTasksList(Task updatedTask){
        mAdapter.notifyItemChanged(mValues.indexOf(updatedTask), updatedTask);
        return employeeDBHelper.updateTask(updatedTask);
    }
    public boolean deleteTaskFromList(int id){
        mValues.remove(id);
        mAdapter.notifyItemRemoved(id);

        return employeeDBHelper.deleteDepartment(id);
    }
    public boolean addTaskToView(Task mTask){
        mValues.add(mTask);
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
        return employeeDBHelper.addTask(mTask);

    }


}
