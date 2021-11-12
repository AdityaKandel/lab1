package com.example.fitnesscentrebooking;

import android.app.Activity;
import android.content.Intent;
import android.opengl.Visibility;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class Navigation  implements NavigationView.OnNavigationItemSelectedListener {
    Activity activity;
    View headerView;
    NavigationView navigationView;
    public void setNavigationView(Activity activity){
        this.activity = activity;
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);

        DrawerLayout  drawerLayout = (DrawerLayout) activity.findViewById(R.id.drawer_layout); //<<-- need to rename the layout
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                activity, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(activity.getResources().getColor(R.color.white));

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) activity.findViewById(R.id.navigation);//<<-- need to rename the layout
        navigationView.setNavigationItemSelectedListener(this);
        headerView = navigationView.getHeaderView(0);

       setUserInfo();
        updateUI();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.classes_menuItem){
            Intent intent = new Intent(activity.getApplicationContext(), CoursePage.class);
            activity.startActivity(intent);
        }else  if (id == R.id.UserList_item){
            Intent intent = new Intent(activity.getApplicationContext(), UserControlPage.class);
           activity.startActivity(intent);
        }  if (id == R.id.Homepage_menuItem){
            Intent intent = new Intent(activity.getApplicationContext(), HomePage.class);
            activity.startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) activity.findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setUserInfo(){
      TextView username= headerView.findViewById(R.id.UserName_header);
      TextView role = headerView.findViewById(R.id.Role_Header);
      username.setText("Username: "+ LoginPage.getUser().getUsername());
      role.setText("Role: "+ LoginPage.getUser().getRoleName());
    }

    public void updateUI(){

        switch (LoginPage.getUser().getroleNum()){
            case "0":
                break;
            case "1":
                navigationView.getMenu().findItem(R.id.UserList_item).setVisible(false);
                break;
            case "2":
                navigationView.getMenu().findItem(R.id.UserList_item).setVisible(false);
                break;
        }
    }
}
