package com.example.hunger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;
import java.util.List;

public class HomeScreen extends AppCompatActivity {

    private ViewPager screenPager;
    IntroViewPagerAdapter introViewPagerAdapter;
    TabLayout tabIndicator;
    Button btnNext;
    Button btnGetStarted;
    Animation btnAnim;
    int position = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home_screen);
        getSupportActionBar().hide();

        //initiate view
        btnNext = findViewById(R.id.btn_next);
        btnGetStarted = findViewById(R.id.btn_get_started);
        tabIndicator = findViewById(R.id.tab_indicator);
        btnAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.btn_animation);

        //fill list screen

        List<ScreenItem> mList = new ArrayList<>();
        mList.add(new ScreenItem("Fresh Food","Get healthy meal delivery featuring fresh, organic produce and clean ingredients delivered to your door",R.drawable.fullplate));
        mList.add(new ScreenItem("Vast Variety","Confused what to eat, we have vast variety of dishes. Be sure to try all out",R.drawable.soup));
        mList.add(new ScreenItem("Fast Delivery","We provide the fastest delivery services. We work with professionals",R.drawable.fastdelivery));

        // setup viewpager
        screenPager = findViewById(R.id.screen_viewpager);
        introViewPagerAdapter = new IntroViewPagerAdapter(this,mList);
        screenPager.setAdapter(introViewPagerAdapter);

        //setup tab_layout with viewpager
        tabIndicator.setupWithViewPager(screenPager);

        //button on click listener setup
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                position = screenPager.getCurrentItem();
                if (position < mList.size()) {
                    position++;
                    screenPager.setCurrentItem(position);
                }
                if (position == mList.size()-1) {

                    //when reach last intro screen then
                    //TODO: show get started button and hide indicators and next button
                    loadLastScreen();
                }
            }
        });
        //tab_layout add change listener

        tabIndicator.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getPosition()==mList.size()-1){
                    loadLastScreen();
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //get started button click listener
        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open Login page
                Intent intent1 = new Intent(getApplicationContext(),LoginPage.class);
                startActivity(intent1);

            }
        });

    }

    //show get started button and hide indicators and next button
    private void loadLastScreen() {
        btnGetStarted.setVisibility(View.VISIBLE);
        btnNext.setVisibility(View.INVISIBLE);
        tabIndicator.setVisibility(View.INVISIBLE);

        //TODO : adding animation
        //setup and initiate animation
        btnGetStarted.setAnimation(btnAnim);
    }
}