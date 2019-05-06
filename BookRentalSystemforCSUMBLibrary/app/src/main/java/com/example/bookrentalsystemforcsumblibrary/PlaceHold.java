package com.example.bookrentalsystemforcsumblibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PlaceHold extends AppCompatActivity {

    private EditText pickupMonth, pickupDay, pickupYear, pickupHour, pickupMin, pickupZone;
    private EditText returnMonth, returnDay, returnYear, returnHour, returnMin, returnZone;
    private Button search;
    private Database myDB;
    private String pickupTime, returnTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_hold);

        myDB = new Database(this);

        pickupMonth = (EditText) findViewById(R.id.pickupMonth);
        pickupDay = (EditText) findViewById(R.id.pickupDay);
        pickupYear = (EditText) findViewById(R.id.pickupYear);
        pickupHour = (EditText) findViewById(R.id.pickupHour);
        pickupMin = (EditText) findViewById(R.id.pickupMin);
        pickupZone = (EditText) findViewById(R.id.pickupZone);

        returnMonth = (EditText) findViewById(R.id.returnMonth) ;
        returnDay = (EditText) findViewById(R.id.returnDay) ;
        returnYear = (EditText) findViewById(R.id.returnYear);
        returnHour = (EditText) findViewById(R.id.returnHour);
        returnMin = (EditText) findViewById(R.id.returnMin);
        returnZone = (EditText) findViewById(R.id.returnZone);

        search = (Button) findViewById(R.id.searchButton);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pickupTime = pickupMonth.getText().toString() + "/"  + pickupDay.getText().toString() + "/" + pickupYear.getText().toString() + " " + pickupHour.getText().toString() + ":" + pickupMin.getText().toString() + " (" + pickupZone.getText().toString() + ")";
                returnTime = returnMonth.getText().toString() + "/"  + returnDay.getText().toString() + "/" + returnYear.getText().toString() + " " + returnHour.getText().toString() + ":" + returnMin.getText().toString() + " (" + returnZone.getText().toString() + ")";

                Toast.makeText(getApplication(), returnMonth.getText().toString() + "/"  + returnDay.getText().toString() + "/" + returnYear.getText().toString() + " " + returnHour.getText().toString() + ":" + returnMin.getText().toString() + " (" + returnZone.getText().toString() + ")", Toast.LENGTH_LONG).show();

                if( myDB.checkDays(pickupTime, returnTime) > 7 || myDB.checkDays(pickupTime, returnTime) < 0){
                    Toast.makeText(getApplication(), "The books can’t be reserved due to rental restriction", Toast.LENGTH_LONG).show();
                    return;
                }

                ArrayList<String> books = myDB.getBooks(pickupTime, pickupTime);

            }
        });
    }


}
