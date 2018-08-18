package com.example.android.employeesmanagementsoftware.TaskCreation;


import java.util.ArrayList;
import java.util.Set;

interface TaskCreationCommand {

    Set<Long> execute();
    boolean saveData(String task_name, int task_evaluation , String task_description, String task_deadline, ArrayList<Long> emplyee_id);

}
