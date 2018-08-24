package com.example.android.employeesmanagementsoftware.taskDB;

import java.io.Serializable;
import java.util.ArrayList;

/*
made by menna
 */
// you need to call database helper here
// you will find your table in SQL_CREATE_EMPLOYEE_TASK_TABLE in EmployeesManagementDbHelper
public  class Task implements Serializable
{

    private Long id;
    private String taskName;
    private ArrayList<Long> employees_id;
    private String taskDetails;
    private String taskDate;
    private String taskDeadLine;
    private boolean done;
    private int  evaluation;
    public Task (){
        employees_id = new ArrayList<>();
        done = false;
    }
      public void setEmployees_id(ArrayList<Long> ids){
        employees_id = ids;
      }

    public ArrayList<Long> getEmployees_id() {
        return employees_id;
    }

    public int getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(int evaluation) {
        this.evaluation = evaluation;
    }

    public long getId() {
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