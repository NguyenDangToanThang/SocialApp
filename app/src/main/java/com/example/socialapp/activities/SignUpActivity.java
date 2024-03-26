package com.example.socialapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.socialapp.R;
import com.example.socialapp.data.RoomDatabase;
import com.example.socialapp.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private EditText signupEmail , signupPassword;
    private Button signupButton;
    private TextView loginRedirectText;
    private RoomDatabase roomDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        auth = FirebaseAuth.getInstance();
        roomDatabase = RoomDatabase.getInstance(this);

        signupEmail = (EditText) findViewById(R.id.signup_email);
        signupPassword = (EditText) findViewById(R.id.signup_password);
        signupButton = (Button) findViewById(R.id.signup_button);
        loginRedirectText = (TextView) findViewById(R.id.loginRedirectText);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = signupEmail.getText().toString().trim();
                String password = signupPassword.getText().toString().trim();

                if(email.isEmpty()) {
                    signupEmail.setError("Email can not be empty");
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    signupEmail.setError("Invalid Email");
                }
                else if(password.isEmpty()) {
                    signupPassword.setError("Password can not be empty");
                }
                else {
                    auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(SignUpActivity.this, "SignUp Successful", Toast.LENGTH_SHORT).show();
                                User user = new User();
                                user.setEmail(email);
                                user.setPassword(password);
                                roomDatabase.getUserDAO().insertUser(user);
                                Intent i = new Intent(SignUpActivity.this , LoginActivity.class);
                                startActivity(i);
                            } else
                                Toast.makeText(SignUpActivity.this, "SignUp Failed " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });

        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignUpActivity.this , LoginActivity.class);
                startActivity(i);
            }
        });

    }


}