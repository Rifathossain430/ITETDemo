package com.rifat.example.itetdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.rifat.example.itetdemo.databinding.ActivityPhoneVerificationBinding;

import java.util.concurrent.TimeUnit;

public class PhoneVerificationActivity extends AppCompatActivity {
    private ActivityPhoneVerificationBinding binding;
    String phone,phoneNumber;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_phone_verification);
        phone=binding.loginphoneET.getText().toString();
        spinner = findViewById(R.id.spinnerCountries);
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, CountryData.countryNames));



        binding.nextBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String code = CountryData.countryAreaCodes[spinner.getSelectedItemPosition()];

               String number = binding.loginphoneET.getText().toString().trim();

               if (number.isEmpty() || number.length() < 10) {
                   binding.loginphoneET.setError("Valid number is required");
                   binding.loginphoneET.requestFocus();
                   return;
               }

               String phonenumber = "+" + code + number;

               Intent intent = new Intent(PhoneVerificationActivity.this, VerifyPhoneActivity.class);
               intent.putExtra("phonenumber", phonenumber);
               startActivity(intent);

           }
       });
    }




    public void loginactivity(View view) {
        startActivity(new Intent(PhoneVerificationActivity.this,LoginActivity.class));
        finish();

    }

    public void back(View view) {
        onBackPressed();
    }
}
