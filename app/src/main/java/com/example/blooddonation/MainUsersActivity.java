package com.example.blooddonation;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainUsersActivity extends AppCompatActivity {
    TextView txtViewName,txtViewMail;


    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_users);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        View headerView = navigationView.getHeaderView(0);
        txtViewName = headerView.findViewById(R.id.txt_view_name);
        txtViewMail = headerView.findViewById(R.id.txt_view_mail);
        txtViewName.setText(LoginActivity.name);
        txtViewMail.setText(LoginActivity.mail);


        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.my_profile, R.id.nav_gallery, R.id.contact_us,R.id.bloodTypeCompatibilityFragment)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_users, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.logout:
                startActivity(new Intent(MainUsersActivity.this,LoginActivity.class));
                getSharedPreferences("remmeber me",MODE_PRIVATE).edit()
                        .clear().commit();
                return true;
            case R.id.deleteAccount:
                AlertDialog.Builder alert = new AlertDialog.Builder(MainUsersActivity.this)
                        .setTitle("Deleting Account!")
                        .setMessage("Hi " + LoginActivity.name +"\nYou Sure Delete your account?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Database db = new Database();
                                db.ConnectDB();
                                db.RunDML("delete from Users where from Users where (Email='"+LoginActivity.mail.toString().toLowerCase()+"' or Phone='"+LoginActivity.mail.toString()+"')");
                                Toast.makeText(MainUsersActivity.this,"Account Has been Deleted Successfully",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(MainUsersActivity.this,LoginActivity.class));
                                getSharedPreferences("remmeber me",MODE_PRIVATE).edit()
                                        .clear().commit();
                            }
                        })
                        .setNegativeButton("No",null);
                alert.create().show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

}