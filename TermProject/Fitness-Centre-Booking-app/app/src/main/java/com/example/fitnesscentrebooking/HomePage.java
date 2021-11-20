package com.example.fitnesscentrebooking;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HomePage extends AppCompatActivity {
    List<Course> courseList;
    ListView listView;
    int[] todayDate;
    int[] lastWeekDate;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.home_page_activity);
        Navigation navi = new Navigation();
        navi.setNavigationView(this);
        todayDate = new int[3];
        lastWeekDate = new int[3];
        SimpleDateFormat df = new SimpleDateFormat("MMMM dd, yyyy");
        String formatted = df.format(new Date());
        System.out.println(formatted);

/*https://stackoverflow.com/questions/31606760/find-start-end-date-of-a-week-from-week-number*/
        LocalDate today = LocalDate.now();
        LocalDate lastDayOfWeek = today.with(DayOfWeek.SUNDAY);
        System.out.println("This year: " + today + " - " + lastDayOfWeek);
        String[] tmpToday = today.toString().split("-");
        String[] tmpLast =lastDayOfWeek.toString().split("-");
        for (int i = 0; i < 3; i++) {
            todayDate[i] = Integer.parseInt(tmpToday[i]);
            lastWeekDate[i] = Integer.parseInt(tmpLast[i]);
        }

        courseList = new ArrayList<>();
        listView = findViewById(R.id.listview_homepage);
        UpdateUI();
    }



    public void UpdateUI(){
        if(LoginPage.getUser().getroleNum().equals("1")){
            FirebaseDatabase.getInstance().getReference("scheduledClass").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    courseList.clear();
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                        Course course = postSnapshot.child("class").getValue(Course.class);
                        DateFormat Format = new SimpleDateFormat("yyyy-MM-dd");
                        Date dateformated = null;
                        try {
                            dateformated = (new SimpleDateFormat("MMMM dd, yyyy")).parse((course.getDate()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        String[] date = Format.format(dateformated).split("-");
                        int[] date_ = new int[3];
                        for (int i = 0; i < 2; i++) {
                            date_[i] = Integer.parseInt(date[i]);
                        }
                        System.out.println(date_[1]+" year"+todayDate[1] +" "+(date_[2]+" "+todayDate[2]));
                        if (course.getUserName().toLowerCase().contains(LoginPage.getUser().getUsername().toLowerCase()) && date_[1]==todayDate[1] && ((date_[2]>= todayDate[2]) || date_[2]<= lastWeekDate[2])) {
                            courseList.add(course);
                        }
                    }
                    scheduleList productAdapter = new scheduleList(HomePage.this, courseList);
                    listView.setAdapter(productAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

}
