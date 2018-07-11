package com.example.android.employeesmanagementsoftware;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

public class taskCreation extends AppCompatActivity {
    private static final String TAG="spinner";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_creation);

        //object of drop down menu
        Spinner spinner=(Spinner) findViewById(R.id.departmentDropDown);

        //adapter to hold values in the menu
        //takes as input the an array defined in the resource strings.xml
        //and a default spinner defined by the platform
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.departments_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //when a department is chosen a list of its employees would appear under the spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG, "onItemSelected: "+parent.getSelectedItem().toString());
                //TODO pass name of department to send it to the adapter inorder to get the coressponding employees
                //TODO retrieve chosen employees from the checkboxes

                initListView(parent.getSelectedItem().toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                System.out.println("nothing");

            }
        });
    }


    public void initListView(String dep){
        ListView employeesList=(ListView) findViewById(R.id.employees_List);
        int res =dep.compareTo("engineering")==0?R.array.engineering_employees
                :R.array.accounting_employees;
        ArrayAdapter<String> adapter= new CustomAdapter(this,getResources().
                getStringArray(res));


        employeesList.setAdapter(adapter);


    }

}
