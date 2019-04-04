package com.example.mobifood;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobifood.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {

private EditText Email,Pass;
private  Button SignIn;
private TextView NewAccount;

    private DatabaseReference myDatabase;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Email = (EditText) findViewById(R.id.yourEmail);
        Pass = (EditText) findViewById(R.id.yourPassword);
        SignIn = (Button) findViewById(R.id.register);

        checkBox= (CheckBox) findViewById(R.id.remember_me);
        Paper.init(this);
        firebaseAuth = FirebaseAuth.getInstance();
        myDatabase = FirebaseDatabase.getInstance().getReference().child("Customers");
        progressDialog = new ProgressDialog(this);

        NewAccount = (TextView) findViewById(R.id.dontHaveAccount);

        NewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoRgistrationActivity = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(gotoRgistrationActivity);
            }
        });


        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInUser();
            }
        });
    }

    private void signInUser()
    {
        final String email = Email.getText().toString().trim();
        final String password = Pass.getText().toString().trim();

        if(TextUtils.isEmpty(email))
        {
            Email.setError("Please enter your email");
            Email.requestFocus();
            return;
        }

        else if(!email.contains("@"))
        {
            Email.setError(" @ is missing");
            Email.requestFocus();
            return;
        }

        else if(!email.contains("gmail.com") && !email.contains("htu.gh") && !email.contains("yahoomail.com") && !email.contains("icloud.com"))
        {
            Email.setError(" Invalid emil ");
            Email.requestFocus();
            return;
        }

        else
        {
            progressDialog.setTitle("LogIn");
            progressDialog.setMessage(" Checking credentials");
            progressDialog.show();
            progressDialog.setCanceledOnTouchOutside(false);


            firebaseAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful())
                            {
                                if(checkBox.isChecked())
                                {
                                    Paper.book().write(Prevalent.UserEmailKey,email);
                                    Paper.book().write(Prevalent.UserPasswordKey,password);
                                }
                                final String user_id = firebaseAuth.getCurrentUser().getUid();
                                myDatabase.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if(dataSnapshot.hasChild(user_id))
                                        {
                                            progressDialog.dismiss();

                                            Intent homeIntent = new Intent(LoginActivity.this,HomeActivity.class);
                                            homeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            Prevalent.currentOnLineUser = user_id;
                                            startActivity(homeIntent);
                                            finish();

                                            Toast.makeText(LoginActivity.this," Successfully logged In",Toast.LENGTH_SHORT).show();

                                        }
                                        else if (!dataSnapshot.exists())
                                        {

                                            Toast.makeText(LoginActivity.this,"You must register before you login",Toast.LENGTH_LONG).show();

                                            Intent loginIntent = new Intent(LoginActivity.this,LoginActivity.class);
                                            loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(loginIntent);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                            }
                            else
                            {
                                String errorMessage = task.getException().getMessage();
                                Toast.makeText(LoginActivity.this,"An error Occurred",Toast.LENGTH_LONG).show();

                                Intent loginIntent = new Intent(LoginActivity.this,LoginActivity.class);
                                loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(loginIntent);
                            }
                        }
                    });

        }
    }

}
