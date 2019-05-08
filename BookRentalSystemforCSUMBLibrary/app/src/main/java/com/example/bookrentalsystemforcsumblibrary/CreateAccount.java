package com.example.bookrentalsystemforcsumblibrary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateAccount extends AppCompatActivity {

    private Database myDB;

    private Button createAccountButton;
    private EditText editTextUsername;
    private EditText editTextPassword;

    private boolean firstWrong;
    private boolean firstTacken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        myDB = new Database(this);
        createAccountButton = (Button) findViewById(R.id.createAccountButton);
        editTextUsername = (EditText) findViewById(R.id.username);
        editTextPassword = (EditText) findViewById(R.id.password);

        firstWrong = false;
        firstTacken = false;

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();

                StringBuilder m = new StringBuilder();

                if( wrongFormat(username) )
                    m.append("Username Invalid\n");

                if( wrongFormat(password))
                    m.append("Password Invalid");

                if( m.length() != 0 ){

                    if(firstWrong)
                        startActivity(new Intent(CreateAccount.this, ErrorMessage.class));
                    else
                        firstWrong = true;

                    Toast.makeText(getApplication(), m.toString(), Toast.LENGTH_LONG).show();
                    Log.d("Account Create", "Fail");
                    return;
                }

                if( myDB.insertUser(username,password) ) {

                    Toast.makeText(getApplication(), "Account was created", Toast.LENGTH_LONG).show();

                    startActivity(new Intent(CreateAccount.this, MainActivity.class));
                }
                else {

                    if(firstTacken)
                        startActivity(new Intent(CreateAccount.this, ErrorMessage.class));
                    else
                        firstTacken = true;

                    Toast.makeText(getApplication(), "User Name is taken", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    boolean wrongFormat(String str){

        if( str.length() > 8)
            return true;

        int n = 0, c = 0;
        for(int i=0; i<str.length(); i++){

            if( Character.isDigit(str.charAt(i)) )
                n++;
            else if( Character.isLetter(str.charAt(i)) )
                c++;
            else
                return true;
        }

        if(n == 0 || c < 3)
            return true;

        return false;
    }
}
