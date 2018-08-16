package com.example.android.employeesmanagementsoftware.EmployeeDB;

import android.support.annotation.NonNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by User on 8/16/2018.
 */

public class Employee {

   private String name,phone,mail,birthdate,job,image;
   private boolean isImgChanged;
   private long id;

   private Map<Long,String> notes;

   public Employee(){
       notes = new HashMap<Long, String>() ;

   }


}
