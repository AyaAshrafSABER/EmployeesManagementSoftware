package com.example.android.employeesmanagementsoftware.taskDB;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RatingBar;

import com.example.android.employeesmanagementsoftware.R;

public class Evaluation extends AppCompatDialogFragment {
    private RatingBar ratingBar;
    private EvaluationListner mListner;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_evaluation, null);
        builder.setView(view)
                .setTitle("Evaluate Task")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListner.applyingRating((int) ratingBar.getRating());
                    }
                });
        ratingBar = (RatingBar) view.findViewById(R.id.ratingBar_task);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListner = (EvaluationListner) context;
        } catch (ClassCastException e) {
             throw new ClassCastException(context.toString() + " must implement EvaluationListner ");
        }
    }

    public interface EvaluationListner{
       public void applyingRating(int rate);
    }
}
