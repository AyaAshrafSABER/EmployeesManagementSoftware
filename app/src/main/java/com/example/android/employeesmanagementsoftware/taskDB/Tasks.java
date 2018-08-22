package com.example.android.employeesmanagementsoftware.taskDB;

import java.util.ArrayList;

/*
made by menna
 */
// you need to call database helper here
// you will find your table in SQL_CREATE_EMPLOYEE_TASK_TABLE in EmployeesManagementDbHelper
public  class Tasks
{

    private Long id;
    private String taskName;
    private ArrayList<Long> employees_id;
    private String taskDetails;
    private String taskDate;
    private String taskDeadLine;
    private boolean done;
    private int  evaluation;
    public Tasks (){
        employees_id = new ArrayList<>();
        done = false;
    }

    public void addEmployee(Long id) {
            employees_id.add(id);
    }
    public Long getEmployee (int position) {
            return  employees_id.get(position);
    }
    public int getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(int evaluation) {
        this.evaluation = evaluation;
    }

    public Long getId() {
        return id;
    }

    public void setId(String id) {
        this.id = Long.parseLong(id);
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskDeadline(String taskdeadline) {
        this.taskDeadLine=taskdeadline;}

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }


    public String getTaskDetails() {
        return taskDetails;
    }

    public String getTaskDeadline() {
        return taskDeadLine;
    }

    public void setTaskDetails(String taskDetails) {
        this.taskDetails = taskDetails;
    }

    public String getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(String taskDate) {
        this.taskDate = taskDate;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public boolean  isDone() {
        return done;
    }
}