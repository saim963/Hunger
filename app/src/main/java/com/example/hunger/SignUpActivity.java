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

import com.example.hunger.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {

    //ViewBinding
    private ActivitySignUpBinding binding;
    private FirebaseAuth firebaseAuth;

    //progress dialog
    private ProgressDialog progressDialog;

    //actionbar
    private ActionBar actionBar;

    private String email ="", password = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home_screen);
        getSupportActionBar().hide();
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //configure action bar, title, back button
        actionBar = getSupportActionBar();
        actionBar.setTitle("SignUp");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        //initiate firebase
        firebaseAuth = FirebaseAuth.getInstance();

        //configure progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Creating account...");
        progressDialog.setCanceledOnTouchOutside(false);

        binding.signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });

    }

    @Override
    public boolean onSearchRequested() {
        onBackPressed(); //got an previous activity when back button of actionbar clicked
        return super.onSearchRequested();
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
        else if (password.length()<6){
            //password length less than 6
            binding.emailEt.setError("Password must be atleast 6 characters long");
        }
        else {
            //data validity checked now proceed
            firebaseSignUp();
        }
    }

    private void firebaseSignUp() {
        //show progress
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        //signUp success
                        progressDialog.dismiss();
                        //get user info
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        String email = firebaseUser.getEmail();
                        Toast.makeText(SignUpActivity.this, "Account created\n"+email, Toast.LENGTH_SHORT).show();

                        //Open profile activity
                        startActivity(new Intent(SignUpActivity.this, profileActivity.class));
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //signUp failure
                        progressDialog.dismiss();
                        Toast.makeText(SignUpActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}