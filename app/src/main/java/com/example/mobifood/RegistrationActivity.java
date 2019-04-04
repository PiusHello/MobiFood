package com.example.mobifood;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {

    private EditText FullName,Email,Pass,Phone;
    private Button Register;
    private TextView HasAccount;

    private FirebaseAuth mAuth;
    private DatabaseReference myDatabase;
    private ProgressDialog loadingBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        FullName = (EditText) findViewById(R.id.fullName);
        Email = (EditText) findViewById(R.id.yourEmail);
        Pass = (EditText) findViewById(R.id.yourPassword);
        Phone = (EditText) findViewById(R.id.yourPhone);

        Register = (Button) findViewById(R.id.registerUser);
        HasAccount = (TextView) findViewById(R.id.alreadyHasAccount);

        //Getting FireBase Authentication instance
        mAuth = FirebaseAuth.getInstance();
        //Getting FireBase Database instance and creating table as well call Customers
        myDatabase = FirebaseDatabase.getInstance().getReference().child("Customers");

        //Creating and instance from the ProgressDialog
        loadingBox = new ProgressDialog(this);


        HasAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoLoginActivity = new Intent(RegistrationActivity.this,LoginActivity.class);
                startActivity(gotoLoginActivity);
            }
        });


        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser()
    {
        final String name = FullName.getText().toString().trim();
        final String email = Email.getText().toString().trim();
        final String password = Pass.getText().toString().trim();
        final String phone = Phone.getText().toString().trim();

        if(TextUtils.isEmpty(name))
        {
            FullName.setError("Please enter your name");
            FullName.requestFocus();
            return;
        }

        else if(name.length()<3)
        {
            FullName.setError("Name too short");
            FullName.requestFocus();
            return;
        }

        else if(name.contains("¬!£$%^&&*()_+=-<>:@~/.,0123456789/*-+"))
        {
            FullName.setError("Special characters and numbers are not allow");
            FullName.requestFocus();
            return;
        }

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

        else if(!email.contains("gmail.com") && !email.contains("yahoomail.com") && !email.contains("icloud.com"))
        {
            Email.setError(" Bad format please check your email ");
            Email.requestFocus();
            return;
        }

        else if(TextUtils.isEmpty(password))
        {
            Pass.setError(" You are require to set password ");
            Pass.requestFocus();
            return;
        }

        else if(password.length()<6)
        {
            Pass.setError(" Minimum password require is 6 characters ");
            Pass.requestFocus();
            return;
        }

        else
        {

            loadingBox.setTitle("Just a moment");
            loadingBox.setMessage("Creating account");
            loadingBox.show();
            loadingBox.setCanceledOnTouchOutside(false);

            mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {



                            if(task.isSuccessful())
                            {
                                String user_id = mAuth.getCurrentUser().getUid();
                                DatabaseReference currentUser = myDatabase.child(user_id);

                                currentUser.child("Username").setValue(name);
                                currentUser.child("Email").setValue(email);
                                currentUser.child("Password").setValue(password);
                                currentUser.child("Phone").setValue(phone);

                                loadingBox.dismiss();
                                Toast.makeText(RegistrationActivity.this,"Registration Was Successful",Toast.LENGTH_SHORT).show();

                                loadingBox.dismiss();
                                Intent homeIntent = new Intent(RegistrationActivity.this,HomeActivity.class);
                                homeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(homeIntent);
                                finish();

                            }


                            else
                            {
                                loadingBox.dismiss();
                                String errorMessage = task.getException().getMessage();
                                Toast.makeText(RegistrationActivity.this," Error Occurred " + errorMessage ,Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        }



    }
}
