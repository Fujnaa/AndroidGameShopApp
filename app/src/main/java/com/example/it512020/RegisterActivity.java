package com.example.it512020;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private Button PledgeButton;
    private EditText InputUsername, InputEmail, InputPassword, InputConfirmPassword;
    private ProgressBar LoadingBar;

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

        if(TextUtils.isEmpty(username) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)){

            Toast.makeText(this, "Please fill in all the necessary information.", Toast.LENGTH_SHORT).show();

        }
        else{

            LoadingBar.setIndeterminate(true);

        }

    }
}