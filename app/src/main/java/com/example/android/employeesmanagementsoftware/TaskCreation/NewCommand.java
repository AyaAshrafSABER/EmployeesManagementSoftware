package com.example.android.employeesmanagementsoftware.TaskCreation;

import com.example.android.employeesmanagementsoftware.data.DBHelpers.EmployeesManagementDbHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.Set;
import java.util.Calendar;

public class NewCommand implements TaskCreationCommand {

    private EmployeesManagementDbHelper employeeDBHelper;

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
        Date currentTime = Calendar.getInstance().getTime();
        boolean notDone = false;
        return employeeDBHelper.addTask(task_name,task_evaluation,task_description,currentTime.toString(),task_deadline,notDone,emplyee_id);
    }

}
