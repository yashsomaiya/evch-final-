package com.example.dothis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText memail;
    private EditText mpassword;

    private Button Login;

    //firebaseauth
    private FirebaseAuth mAuth;

    //progressbar
    private ProgressDialog mLoginprogress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLoginprogress= new ProgressDialog(this);

        mAuth= FirebaseAuth.getInstance();

        memail= findViewById(R.id.hospital_email);
        mpassword= findViewById(R.id.hospital_pass);
        Login= findViewById(R.id.login_btn);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = memail.getText().toString();
                String password = mpassword.getText().toString();

                if (!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password))
                {
                    mLoginprogress.setTitle("Logging in");
                    mLoginprogress.setMessage("please wait while we check your credentials");
                    mLoginprogress.setCanceledOnTouchOutside(false);
                    mLoginprogress.show();
                    loginuser(email,password);
                }


            }


        });


    }

    private void loginuser(String email, String password) {

            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful())
                    {
                        mLoginprogress.dismiss();
                        Intent mainintent= new Intent(MainActivity.this,select_case.class);
                        startActivity(mainintent);
                        finish();
                    }
                    else
                    {
                        mLoginprogress.hide();
                        Toast.makeText(MainActivity.this,"Sorry couldnt log you in", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
}

