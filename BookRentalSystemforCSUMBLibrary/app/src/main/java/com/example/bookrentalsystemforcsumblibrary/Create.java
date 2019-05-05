package com.example.bookrentalsystemforcsumblibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Create extends AppCompatActivity {

    private Button createAccountButton;
    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        createAccountButton = (Button) findViewById(R.id.CreateAccountButton);

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
