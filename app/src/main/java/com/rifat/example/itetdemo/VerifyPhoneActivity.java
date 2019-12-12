package com.rifat.example.itetdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.accessibilityservice.GestureDescription;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.rifat.example.itetdemo.databinding.ActivityVerifyPhoneBinding;

import java.util.concurrent.TimeUnit;

public class VerifyPhoneActivity extends AppCompatActivity {
    private ActivityVerifyPhoneBinding binding;
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;
    String phonenumber,codesent,verificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_verify_phone);
        progressBar = binding.progressbar;
        if (getIntent().getExtras()!= null){
            phonenumber = getIntent().getStringExtra("phonenumber");
            sendOtp();

        }
        binding.signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String codesent=binding.verificationCodeET.getText().toString();
                if (codesent!=null){
                    binding.verificationCodeET.setError("Enter code...");
                    binding.verificationCodeET.requestFocus();
                    return;
                }else {
                    verify(codesent);
                }
            }
        });
        binding.tryAgainTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VerifyPhoneActivity.this,PhoneVerificationActivity.class));
            }
        });


    }

    private void sendOtp() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            phonenumber,        // Phone number to verify
            60,                 // Timeout duration
            TimeUnit.SECONDS,   // Unit of timeout
            this,               // Activity (for callback binding)
            mCallbacks);        // OnVerificationStateChangedCallbacks
    }
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            codesent=phoneAuthCredential.getSmsCode();
            if (codesent.length()==6){
                progressBar.setVisibility(View.VISIBLE);
                binding.verificationCodeET.setText(codesent);
                verify(codesent);
            }

        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(VerifyPhoneActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId=s;

        }
    };

    private void verify(String codesent) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, codesent);
        signInWithCredential(credential);

    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Intent intent = new Intent(VerifyPhoneActivity.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(VerifyPhoneActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void phonenumberActivity(View view) {
        startActivity(new Intent(VerifyPhoneActivity.this,PhoneVerificationActivity.class));
    }
}
