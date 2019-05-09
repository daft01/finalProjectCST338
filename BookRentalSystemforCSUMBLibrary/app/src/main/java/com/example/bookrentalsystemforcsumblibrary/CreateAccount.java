package com.example.bookrentalsystemforcsumblibrary;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
        editTextUsername = (EditText) findViewById(R.id.usernameTextView);
        editTextPassword = (EditText) findViewById(R.id.passwordTextView);

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

                    if(firstWrong) {
                        AlertDialog alertDialog = new AlertDialog.Builder(CreateAccount.this).create();
                        alertDialog.setTitle("");
                        alertDialog.setMessage("Number of tries exceeded");

                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(CreateAccount.this, MainActivity.class));
                            }
                        });

                        alertDialog.show();
                        return;
                    }
                    else {
                        firstWrong = true;
                    }

                    AlertDialog alertDialog = new AlertDialog.Builder(CreateAccount.this).create();
                    alertDialog.setTitle("");
                    alertDialog.setMessage(m.toString());

                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    alertDialog.show();
                    return;
                }

                Log.d(".ol",m.toString());
                if( myDB.insertUser(username,password) ) {

                    AlertDialog alertDialog = new AlertDialog.Builder(CreateAccount.this).create();
                    alertDialog.setTitle("");
                    alertDialog.setMessage("Account was created");

                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(CreateAccount.this, MainActivity.class));
                        }
                    });

                    alertDialog.show();
                    return;
                }
                else {

                    if(firstTacken) {
                        AlertDialog alertDialog = new AlertDialog.Builder(CreateAccount.this).create();
                        alertDialog.setTitle("");
                        alertDialog.setMessage("Number of tries exceeded");

                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(CreateAccount.this, MainActivity.class));
                            }
                        });

                        alertDialog.show();
                    }
                    else {
                        firstTacken = true;
                    }

                    AlertDialog alertDialog = new AlertDialog.Builder(CreateAccount.this).create();
                    alertDialog.setTitle("");
                    alertDialog.setMessage("User Name is taken");

                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(CreateAccount.this, MainActivity.class));
                        }
                    });

                    alertDialog.show();
                    return;
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

        if(n < 1 || c < 3)
            return true;

        return false;
    }
}
