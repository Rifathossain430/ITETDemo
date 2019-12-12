package com.rifat.example.itetdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        replaceFragment(new HomeFragment());
        bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemReselectedListener(onNavigationItemReselectedListener);


    }


    private void replaceFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameL, fragment);
        ft.commit();
    }

    private BottomNavigationView.OnNavigationItemReselectedListener onNavigationItemReselectedListener = new BottomNavigationView.OnNavigationItemReselectedListener() {
        @Override
        public void onNavigationItemReselected(@NonNull MenuItem menuItem) {
           switch (menuItem.getItemId()){
               case R.id.navigation_home:
                   replaceFragment(new HomeFragment());
                   break;
                   case R.id.navigation_dashboard:
                   replaceFragment(new DashboardFragment());
                   break;
                   case R.id.navigation_profile:
                   replaceFragment(new ProfileFragment());
                   break;
                   default:
                       Toast.makeText(MainActivity.this, "Please Try again", Toast.LENGTH_SHORT).show();
                       break;

           }
        }
    };
}
