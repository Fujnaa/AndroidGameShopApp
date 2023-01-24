package com.example.it512020;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private Button PledgeButton;
    private EditText InputUsername, InputEmail, InputPassword, InputConfirmPassword;
    private ProgressBar LoadingBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        PledgeButton = (Button) findViewById(R.id.register_pledge_btn);
        InputUsername = (EditText) findViewById(R.id.register_username_input);
        InputEmail = (EditText) findViewById(R.id.register_email_input);
        InputPassword = (EditText) findViewById(R.id.register_password_input);
        InputConfirmPassword = (EditText) findViewById(R.id.register_confirmPassword_input);
        LoadingBar = (ProgressBar) findViewById(R.id.register_progress_bar);
        mAuth = FirebaseAuth.getInstance();

        PledgeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateAccount();
            }
        });
    }

    private void CreateAccount() {

        String username = InputUsername.getText().toString();
        String email = InputEmail.getText().toString();
        String password = InputPassword.getText().toString();
        String confirmPassword = InputConfirmPassword.getText().toString();


        if(TextUtils.isEmpty(username)){

            InputUsername.setError("Username is required!");
            InputUsername.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(email)){

            InputEmail.setError("Email is required!");
            InputEmail.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(password)){

            InputPassword.setError("Password is required!");
            InputPassword.requestFocus();
            return;
        }
        else if(password.length() < 6){

            InputPassword.setError("You password has to be longer than 6 characters!");
            InputPassword.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(confirmPassword)){

            InputConfirmPassword.setError("Confirm your password!");
            InputConfirmPassword.requestFocus();
            return;
        }
        else if(!password.equals(confirmPassword)){

            InputConfirmPassword.setError("The passwords do not match!");
            InputConfirmPassword.requestFocus();
            return;
        }
        else{
            LoadingBar.setVisibility(View.VISIBLE);

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()) {
                                User user = new User(username, email);

                                FirebaseDatabase.getInstance("https://clutchcamp-7a010-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()) {
                                                    Toast.makeText(RegisterActivity.this, "You have successfully joined the community!", Toast.LENGTH_SHORT).show();
                                                    LoadingBar.setVisibility(View.GONE);
                                                }
                                                else {
                                                    Toast.makeText(RegisterActivity.this, "Network error, please try again later.", Toast.LENGTH_SHORT).show();
                                                    LoadingBar.setVisibility(View.GONE);
                                                }
                                            }

                                        });
                            }
                            else {
                                Toast.makeText(RegisterActivity.this, "Network error, please try again later.", Toast.LENGTH_SHORT).show();
                                LoadingBar.setVisibility(View.GONE);
                            }
                        }
                    });

        }


    }
}