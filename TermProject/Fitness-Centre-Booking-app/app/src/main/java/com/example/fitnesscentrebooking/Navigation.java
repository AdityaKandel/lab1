package com.example.fitnesscentrebooking;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class Navigation  implements NavigationView.OnNavigationItemSelectedListener {
    Activity activity;
    View headerView;
    public void setNavigationView(Activity activity){
        this.activity = activity;
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);

        DrawerLayout  drawerLayout = (DrawerLayout) activity.findViewById(R.id.drawer_layout_UserControl);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                activity, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(activity.getResources().getColor(R.color.white));

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) activity.findViewById(R.id.navigation_UserControl);
        navigationView.setNavigationItemSelectedListener(this);
        headerView = navigationView.getHeaderView(0);

       setUserInfo();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.classes_menuItem){
            Intent intent = new Intent(activity.getApplicationContext(), main_page.class);
            activity.startActivity(intent);
        }else  if (id == R.id.UserList_item){
            Intent intent = new Intent(activity.getApplicationContext(), UserControlPage.class);
           activity.startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) activity.findViewById(R.id.drawer_layout_UserControl);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setUserInfo(){
      TextView username= headerView.findViewById(R.id.UserName_header); //<<<<----NULL
      TextView role = headerView.findViewById(R.id.Role_Header); //<<<<----NULL

      username.setText(MainActivity.getUser().getUsername());
      role.setText(MainActivity.getUser().getRole());
    }
}
