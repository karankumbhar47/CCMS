package com.example.androidapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.androidapp.MainFragment.CompleteCompliantFragment;
import com.example.androidapp.MainFragment.NewComplaintFragment;
import com.google.android.material.tabs.TabLayout;


public class HomePage extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        {
            fragmentManager = getSupportFragmentManager();
            tabLayout = findViewById(R.id.tabs);
        }

        load(fragmentManager,new NewComplaintFragment());
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        load(fragmentManager,new NewComplaintFragment());
                        break;
                    case 1:
                        load(fragmentManager,new CompleteCompliantFragment());
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });

    }

    public void load(FragmentManager fragmentManager,Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.listOfOrders, fragment);
        fragmentTransaction.commit();
    }

}
