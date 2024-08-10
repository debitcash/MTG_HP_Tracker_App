package com.example.finalapp.authentication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalapp.R;
import com.example.finalapp.services.InteractionWithDB;
import com.google.android.material.textfield.TextInputEditText;

public class Signup extends AppCompatActivity {
    Button btn_createPassword;
    TextInputEditText userName, password;
    InteractionWithDB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        btn_createPassword = findViewById(R.id.createPwd);
        userName = findViewById(R.id.username_1);
        password = findViewById(R.id.password_1);

        db = new InteractionWithDB(this);

        btn_createPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nameText = userName.getText().toString();
                String passwordText = password.getText().toString();

                db.addUser(nameText, passwordText);
            }
        });
    }
}