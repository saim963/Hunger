package com.example.hunger;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.hunger.databinding.ActivityProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class profileActivity extends AppCompatActivity implements View.OnClickListener {

    //view binding
    private ActivityProfileBinding binding;

    //actionbar
    private ActionBar actionBar;

    //firebase Auth
    private FirebaseAuth firebaseAuth;

//card view click may get deleted
    private CardView card1,card2,card3,card4,card5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home_screen);
        getSupportActionBar().hide();
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//card view finding by id may get deleted
        card1 = (CardView) findViewById(R.id.idc1);
        card2 = (CardView) findViewById(R.id.idc2);
        card3 = (CardView) findViewById(R.id.idc3);
        card4 = (CardView) findViewById(R.id.idc4);
        card5 = (CardView) findViewById(R.id.idc5);

//card view on clickListener may get deleted
        card1.setOnClickListener(this);
        card2.setOnClickListener(this);
        card3.setOnClickListener(this);
        card4.setOnClickListener(this);
        card5.setOnClickListener(this);

        //configure action bar, title
        actionBar = getSupportActionBar();
        actionBar.setTitle("LogIn");

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance();
        checkUser();

        //logout user by clicking
        binding.logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //intent thing is extra added may be deleted
                firebaseAuth.signOut();
                checkUser();

            }
        });

    }

    private void checkUser() {
        //check if user is not logged in then move to login activity

        //get current user
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser == null){
            //user not logged in, move ton logIn screen
            startActivity(new Intent(this, LoginPage.class));
            finish();
        }
        else {
            //user logged in, get info
            String email = firebaseUser.getEmail();
            //set to email Tv
            binding.emailTv.setText(email);
        }
    }

    @Override
    public void onClick(View v) {
//from here
        Intent i;
        switch (v.getId()) {
            case R.id.idc1:
                i = new Intent(this,one.class);
                startActivity(i);
                break;

            case R.id.idc2:
                i = new Intent(this,two.class);
                startActivity(i);
                break;

            case R.id.idc3:
                i = new Intent(this,three.class);
                startActivity(i);
                break;

            case R.id.idc4:
                i = new Intent(this,four.class);
                startActivity(i);
                break;

            case R.id.idc5:
                i = new Intent(this,five.class);
                startActivity(i);
                break;

        }
//till here

    }
}