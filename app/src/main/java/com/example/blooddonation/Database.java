package com.example.blooddonation;

import android.os.StrictMode;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    Connection con=null;
   public Connection ConnectDB()  {

       try {
           StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
           StrictMode.setThreadPolicy(policy);
           Class.forName("net.sourceforge.jtds.jdbc.Driver");
           con= DriverManager.getConnection("jdbc:jtds:sqlserver://SQL5078.site4now.net", "DB_A704BD_Medicalservices_admin", "YAT123456");
   } catch (ClassNotFoundException e) {
     e.printStackTrace();
       } catch (SQLException e) {
           e.printStackTrace();
       } return con;
   }

   public  String RunDML(String st)
   {
       try {
           Statement stat= con.createStatement();
           stat.executeUpdate(st);
           return "done";
       } catch (SQLException e) {
           e.printStackTrace();
           return  e.getMessage();
       }
   }
   public ResultSet ResultSearch(String st)
   {
       try {
           Statement statm = con.createStatement();
           ResultSet rs;
           rs =  statm.executeQuery(st);
           return rs;
       } catch (SQLException throwables) {
           throwables.printStackTrace();
           return null;
       }
   }
}
