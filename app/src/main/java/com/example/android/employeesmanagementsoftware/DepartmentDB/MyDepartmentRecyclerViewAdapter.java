package com.example.android.employeesmanagementsoftware.DepartmentDB;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.employeesmanagementsoftware.DepartmentDB.DepFragment.OnListFragmentInteractionListener;
import com.example.android.employeesmanagementsoftware.DepartmentDB.DepartementRowData.DepartmentData;
import com.example.android.employeesmanagementsoftware.DepartmentDB.DepartementRowData.DepartmentData.DepartmentItem;
import com.example.android.employeesmanagementsoftware.R;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link com.example.android.employeesmanagementsoftware.DepartmentDB.DepartementRowData.DepartmentData.DepartmentItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyDepartmentRecyclerViewAdapter extends RecyclerView.Adapter<MyDepartmentRecyclerViewAdapter.ViewHolder> {

    private final List<DepartmentItem> mValues;
    private final OnListFragmentInteractionListener mListener;
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
    public MyDepartmentRecyclerViewAdapter(List<DepartmentItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_department, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.mName.setText(mValues.get(position).name);
        holder.mdescriptionView.setText(mValues.get(position).details);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
                Context context = v.getContext();
                Intent intent = new Intent(context, DepartmentActivity.class);
                intent.putExtra("departmentId",mValues.get(position).id); // set id to Department activity
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mName;
        public final TextView mdescriptionView;
        public DepartmentItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mName = (TextView) view.findViewById(R.id.department_name);
            mdescriptionView = (TextView) view.findViewById(R.id.department_description);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mdescriptionView.getText() + "'";
        }
    }
}
