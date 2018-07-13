package com.example.android.employeesmanagementsoftware;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class TasksFragment extends Fragment {

    public TasksFragment() {

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
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
        Tasks [] tasks=new Tasks[10]; //hmlaha b ede
        for(int i=0;i<10;i++)
        {Tasks task = new Tasks();
            task.setId(i);
            task.setTaskDate((i+1)+"/10/2018");
            task.setTaskDetails("Our job description directory contains job description examples covering all the most popular roles. We have examples of job descriptions you can quickly download and modify to suit your unique business requirements. You'll find a job description example for most common jobs. Starting with a sample job description will make sure you do not miss any of the key requirements for a role and new hires will have a better understanding of what their role is" +
                    "What Is the Difference Between a Job Specification and a Job Description?\n" +
                    "A job specification could be considered a more precise job description that details the exact educational degrees, experience, skills, and requirements for a role. In most cases, these terms are used interchangeably and nearly always describe the same document.\n" +
                    "\n" +
                    "Generic Job Description Template:\n" +
                    "We also have a general job description template you can download that will cover any role.\n" +
                    "\n" +
                    "What are some tips on how to write a professional job description?\n" +
                    "Make sure the title of the job position and description match. Do your research. If you're not familiar with the job, talk to someone who is and have them help with the description. Make sure it clearly defines the goals of the position and a timeline for reaching them.\n" +
                    "\n" +
                    "Do you have an example of a good job description I can check out?\n" +
                    "Yes! Check out our sample job descriptions below, as well as our guide on how to write a job description. You should find everything you need to model your description on.\n" +
                    "\n" +
                    "Do your job descriptions mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmcontain work duties?\n" +
                    "Our job descriptions contain the most common job duties list for each position. This should help you get your description written really quickly, although you may need to add job duty kkkkkkkkkkkkkkkjkhkhkhkhjhjhkjhjhjkhjhkjhkhk"+"\n");
            task.setTaskInstractor("Ahmed Mohamed");
            task.setTaskName("software development");
            task.setTaskDeadline((i+1)+"/12/2018");
            tasks[i]=task;
        }
        recyclerView.setAdapter(new TasksAdapter(getActivity(),tasks));
    }


}
