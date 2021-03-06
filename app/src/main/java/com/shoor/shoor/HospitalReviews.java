package com.shoor.shoor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class HospitalReviews extends AppCompatActivity {
    ArrayList<Review> HospitalReviews = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_reviews);
        AllHospitalReviews();
        ListView List = (ListView) findViewById(R.id.AllHospitalReviews);
        AllHospitalReviews AdapterList = new AllHospitalReviews(getApplicationContext(), HospitalReviews);
        if(HospitalReviews.size()!=0)
        List.setAdapter(AdapterList);

    }

    public void AllHospitalReviews(){
        HospitalReviews.clear();
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_Info.DB_URL,DB_Info.USER,DB_Info.PASS);
            Statement stmt = conn.createStatement();
            String sql  = "SELECT * FROM hospitalreview where Hospital_ID="+DoctorProfileActivity.Hospital_ID;
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next())
            {
                String user_name="";
                Statement stmt2 = conn.createStatement();
                String sql2  = "SELECT * FROM User where User_ID="+rs.getInt("User_ID");
                ResultSet rs2 = stmt2.executeQuery(sql2);
                String comments=rs.getString("HospitalComment");
                while (rs2.next()) {
                    user_name = rs2.getString("UserName");
                }
                float ratingscore = rs.getFloat("HospitalRate");
                String b = rs.getString("HospitalPicture");

                Review HospitalReview = new Review(user_name, comments, ratingscore, b);
                HospitalReviews.add(HospitalReview);
                rs2.close();
                stmt2.close();
            }
            rs.close();
            stmt.close();
            conn.close();

        }//end try
        catch (SQLException sqle){
            Toast error = Toast.makeText(this,sqle.getMessage(),Toast.LENGTH_LONG);
            error.show();
        }
        catch (Exception e){Toast error = Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG);
            error.show();}
    }

    public void back(View view) {
        this.finish();
        startActivity(new Intent(HospitalReviews.this, DoctorProfileActivity.class));
    }

}
