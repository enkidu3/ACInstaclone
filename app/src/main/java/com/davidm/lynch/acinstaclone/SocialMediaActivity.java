package com.davidm.lynch.acinstaclone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
//import android.support.v7.widget.Toolbar;  doesn't work

import android.os.Bundle;


import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

public class SocialMediaActivity extends AppCompatActivity {


//    private android.support.v7.widget.Toolbar toolbar;  doesn't work
    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TabAdapter tabAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_media);

        setTitle("Social Media App!!");
//        toolbar =findViewById(R.id.myToolbar);
//        setSupportActionBar(toolbar);

        viewPager=findViewById(R.id.viewPager);
        tabAdapter = new TabAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabAdapter);

        tabLayout=findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager, false);


    }
}
