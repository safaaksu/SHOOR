package com.shoor.shoor;


import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class AddDepartment extends AppCompatActivity {
    EditText department_name ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_department);
        department_name =(EditText)findViewById(R.id.add_department);
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void Send(View view) {
        //Validate inputs
        String DepName = department_name.getText().toString();

        if (isValid(DepName)) {
            //VERY IMPORTANT LINES
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //SETUP CONNECTION
            Connection conn = null;
            Statement stmt = null;
            try{
                //STEP 2: Register JDBC driver
                Class.forName("com.mysql.jdbc.Driver");

                //STEP 3: Open a connection
                conn = DriverManager.getConnection(DB_Info.DB_URL,DB_Info.USER,DB_Info.PASS);

                //STEP 4: Execute a query
                stmt = conn.createStatement();
                String sql;
                sql = "INSERT INTO specialties (SpecialtiesName) Values('" + DepName + "')";
                int rs = stmt.executeUpdate(sql);

              if(rs==1){
              Toast done = Toast.makeText(AddDepartment.this, "تمت الإضافة", Toast.LENGTH_SHORT);
                done.show();
                department_name.setText("");
               }
              else
               {
                Toast done = Toast.makeText(AddDepartment.this, "حدثت مشكلة أثناء الاضافة", Toast.LENGTH_SHORT);
                 done.show();
               }

                //STEP 6: Clean-up environment

                stmt.close();
                conn.close();
            }catch(SQLException se){
                //SHOW SERVER FAILED MESSAGE
                Toast errorToast = Toast.makeText(AddDepartment.this, "يجب أن تكزن متصلاً بالانترنت"+se.getMessage(), Toast.LENGTH_SHORT);
                errorToast.show();
            }catch(Exception e){
                //SHOW SERVER FAILED MESSAGE
                Toast errorToast = Toast.makeText(AddDepartment.this, ""+e.getMessage(), Toast.LENGTH_SHORT);
                errorToast.show();
            }
        }
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public boolean isValid(String DepartmentName){
        //validate all inputs
        if (DepartmentName.equals("")  ) {
            department_name.setError("يجب ملء الخانة");
            return false;
        }
        if(DepartmentName.length() >20)
        {
            department_name.setError("يجب أن لا يتجاوز الاسم 20 حرفاً");
            return false;
        }
        return true;
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void back(View view) {
        startActivity(new Intent(AddDepartment.this, ManageContentActivity.class));

    }
}//END class
