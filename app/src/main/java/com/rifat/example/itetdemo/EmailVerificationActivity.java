package com.rifat.example.itetdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.rifat.example.itetdemo.databinding.ActivityEmailVerificationBinding;

import java.util.regex.Pattern;

public class EmailVerificationActivity extends AppCompatActivity {
    private ActivityEmailVerificationBinding binding;
    private FirebaseAuth firebaseAuth;
    String email,firstPassword,repeatPassword,name,institute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_email_verification);
        init();
        binding.loginTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EmailVerificationActivity.this,LoginActivity.class));

            }
        });
        binding.signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = binding.emailET.getText().toString().trim();
                firstPassword = binding.passwordET.getText().toString().trim();
                repeatPassword = binding.passwordET.getText().toString();
                if (validate()==true){
                    register(email,firstPassword);
                }
                else {
                    Toast.makeText(EmailVerificationActivity.this, "Please Try Again", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }



    private void init() {
        firebaseAuth= FirebaseAuth.getInstance();
        name = binding.nameET.getText().toString();
        institute = binding.instituteET.getText().toString();


    }
    public final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9+._%-+]{1,256}" +
                    "@" +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" +
                    "(" +
                    "." +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" +
                    ")+"
    );

    public boolean validate(){
        boolean temp = true;
        if (!EMAIL_ADDRESS_PATTERN.matcher(email).matches()){
            Toast.makeText(this, "Invalid Email Address", Toast.LENGTH_SHORT).show();
            binding.emailET.setError("Please enter valid Email");
            binding.emailET.requestFocus();
            temp = false;
        }else if (!firstPassword.equalsIgnoreCase(repeatPassword)){
            Toast.makeText(this, "Password Don't match", Toast.LENGTH_SHORT).show();
            binding.passwordET.setError("Please Enter Password");
            binding.passwordET.requestFocus();
            temp = false;
        }
        return temp;


    }

    private void register(String email, String firstPassword) {
        firebaseAuth.createUserWithEmailAndPassword(email,firstPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                startActivity(new Intent(EmailVerificationActivity.this,MainActivity.class));
                finish();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EmailVerificationActivity.this, ""+e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void back(View view) {
        onBackPressed();
    }
}
