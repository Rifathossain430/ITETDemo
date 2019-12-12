package com.rifat.example.itetdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PhoneOrEmailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_or_email);
    }

    public void back(View view) {
        startActivity(new Intent(PhoneOrEmailActivity.this,LoginActivity.class));
    }

    public void phoneVerification(View view) {
        startActivity(new Intent(PhoneOrEmailActivity.this,PhoneVerificationActivity.class));
    }

    public void emailVerififcation(View view) {
        startActivity(new Intent(PhoneOrEmailActivity.this,EmailVerificationActivity.class));
    }
}
