package com.example.android.employeesmanagementsoftware.taskDB;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.employeesmanagementsoftware.R;

import java.util.ArrayList;


/*
made by menna
 */
public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TasksViewHolder> {

    private Context context;
    private ArrayList<Tasks> data;
    private String title;
    private String date;
    private String trainer;
    private String description;
    private  String deadline;
    private  Long id;


    public TasksAdapter(Context context, ArrayList<Tasks> data)
    {
        this.context=context;
        this.data=data;
    }



    @Override
    public TasksViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.tasks_layout,parent,false);
        return new TasksViewHolder(view);
    }

    //show each task in recycle list
    @Override
    public void onBindViewHolder(TasksViewHolder holder, int position)
    {
        final Tasks tasks = data.get(position);
        holder.titletask.setText(tasks.getTaskName());
        holder.disctask.setText(tasks.getTaskDetails());
        holder.datetask.setText(tasks.getTaskDate());
        holder.deadlinetask.setText(tasks.getTaskDeadline());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                id = tasks.getId();
                Intent in = new Intent(context, TaskActivity.class);
                in.putExtra("task_id",id);
                context.startActivity(in);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }



    public class TasksViewHolder extends RecyclerView.ViewHolder {
        TextView titletask;
        TextView disctask;
        TextView datetask;
        TextView deadlinetask;
        public TasksViewHolder(View itemView)
        {
            super(itemView);
            titletask = (TextView)itemView.findViewById(R.id.title_card);
            disctask = (TextView)itemView.findViewById(R.id.disc_card);
            datetask=(TextView)itemView.findViewById(R.id.date);
            deadlinetask=(TextView)itemView.findViewById(R.id.deadline);
        }
    }


}
