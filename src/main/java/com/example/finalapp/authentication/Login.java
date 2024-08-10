package com.example.finalapp.authentication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalapp.R;
import com.example.finalapp.menuitems.PlayersChoice;
import com.example.finalapp.services.InteractionWithDB;
import com.google.android.material.textfield.TextInputEditText;

public class Login extends AppCompatActivity {
    Button loginButton;
    TextInputEditText username, password;
    String TAG = Login.class.getSimpleName();
    InteractionWithDB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        db = new InteractionWithDB(this);

        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = findViewById(R.id.username);
                password = findViewById(R.id.password);

                String providedUsername = username.getText().toString();
                String providedPassword = password.getText().toString();

                if(!(providedPassword.isEmpty()) || !(providedUsername.isEmpty())) {

                    if (db.validateLogin(providedUsername, providedPassword)) {
                        Intent intent = new Intent(Login.this, PlayersChoice.class);
                        startActivity(intent);

                    } else {
                        Toast.makeText(Login.this, "Wrong username or password", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Login.this, LoginSignup.class);
                        startActivity(intent);
                    }
                }
                else{
                    Toast.makeText(Login.this, "Username and password cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
