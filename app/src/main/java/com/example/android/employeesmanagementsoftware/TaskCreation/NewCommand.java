package com.example.android.employeesmanagementsoftware.TaskCreation;

import android.app.Fragment;
import android.util.Log;

import com.example.android.employeesmanagementsoftware.data.DBHelpers.EmployeesManagementDbHelper;
import com.example.android.employeesmanagementsoftware.taskDB.Task;
import com.example.android.employeesmanagementsoftware.taskDB.TasksFragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.Set;
import java.util.Calendar;

public class NewCommand implements TaskCreationCommand {

    private EmployeesManagementDbHelper employeeDBHelper;
    private static final String TAG="new";
    public NewCommand(EmployeesManagementDbHelper employeeDBHelper) {
        this.employeeDBHelper = employeeDBHelper;
    }

    @Override
    public Set<Long> execute() {
        //Any extra functionality can be added to the new task

        return null;
    }

    @Override
    public boolean saveData(String task_name, int task_evaluation, String task_description, String task_deadline, ArrayList<Long> emplyee_id) {
        //call the db helper's method to add a new task
        Calendar calendar = Calendar.getInstance();
        boolean notDone = false;
        Task task = new Task();
        task.setEvaluation(task_evaluation);
        task.setDone(notDone);
        task.setEmployees_id(emplyee_id);
        task.setTaskDeadline(task_deadline);
        task.setTaskDetails(task_description);
        task.setTaskName(task_name);
        task.setTaskDate(calendar.get(Calendar.DAY_OF_MONTH)+"/"+calendar.get(Calendar.MONTH)+"/"
                +calendar.get(Calendar.YEAR));
        return TasksFragment.newInstance().addTaskToView(task);

    }

}
