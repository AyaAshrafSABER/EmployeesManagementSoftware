package com.example.android.employeesmanagementsoftware;

public  class Tasks
{

        private Integer id;
        private String taskName;
        private String taskInstractor;
        private String taskDetails;
        private String taskDate;
        private String taskdeadline;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskDeadline(String taskdeadline) {
        this.taskdeadline=taskdeadline;}

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskInstractor() {
        return taskInstractor;
    }

    public void setTaskInstractor(String taskInstractor) {
        this.taskInstractor = taskInstractor;
    }

    public String getTaskDetails() {
        return taskDetails;
    }

    public String getTaskDeadline() {
        return taskdeadline;
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
}