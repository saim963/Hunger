package com.example.hunger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.hunger.databinding.ActivityLoginPageBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginPage extends AppCompatActivity {

    //viewBinding
    private ActivityLoginPageBinding binding;

    //actionbar
    private ActionBar actionBar;

    //progress dialog
    private ProgressDialog progressDialog;

    //firebase auth
    private FirebaseAuth firebaseAuth;

    private String email ="", password ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home_screen);
        getSupportActionBar().hide();
        binding = ActivityLoginPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //configure action bar, title
        actionBar = getSupportActionBar();
        actionBar.setTitle("LogIn");

        //configure progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Logging In");
        progressDialog.setCanceledOnTouchOutside(false);

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance();
        checkUser();

        //if already have an account click signUp
        binding.haveAccountTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginPage.this, SignUpActivity.class));
            }
        });

        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //validate data
                validateData();
            }
        });

    }

    private void checkUser() {
        //check if user is already logged in
        //if already logged in then open profile activity

        //get current user
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null){
            //user is already logged in
            startActivity(new Intent(this,profileActivity.class));
            finish();
        }
    }

    private void validateData() {
        //get data
        email = binding.emailEt.getText().toString().trim();
        password = binding.passwordEt.getText().toString().trim();

        //validate data
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            //email format is invalid,don't give access
            binding.emailEt.setError("Invalid email format");
        }
        else if (TextUtils.isEmpty(password)){
            //no password is entered
            binding.passwordEt.setError("Enter password");
        }
        else {
            //data validity checked now proceed
            firebaseLogin();
        }
    }

    private void firebaseLogin() {
        //show progress
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        //login success
                        //get user info
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        String email = firebaseUser.getEmail();
                        Toast.makeText(LoginPage.this, "LoggedIn\n"+email, Toast.LENGTH_SHORT).show();

                        //Open profile activity
                        startActivity(new Intent(LoginPage.this, profileActivity.class));
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //login failed, get and show error message
                        progressDialog.dismiss();
                        Toast.makeText(LoginPage.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}