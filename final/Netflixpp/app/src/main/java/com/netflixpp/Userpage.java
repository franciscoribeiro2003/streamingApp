package com.netflixpp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Userpage extends AppCompatActivity {
    private TextView user;
    private TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.currentuser);

        user=findViewById(R.id.user);
        name=findViewById(R.id.nameuser);

        User usertemp = CurrentUser.getCurrentUser();
        user.setText(usertemp.getUsername());
        name.setText(usertemp.getFullName());

        // Initialize buttons by finding them in the layout
        Button catalogueButton = findViewById(R.id.catalogue);
        Button UserPage = findViewById(R.id.userpage);
        Button buttonAdmin = findViewById(R.id.admin);

        // Set onClickListener for Button Left
        catalogueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Define the intent to navigate to another activity (ChangeActivity.class)
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
                // You can add more logic or data to the intent before starting the activity if needed
            }
        });

        // Set onClickListener for Button Right
        UserPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Define the intent to navigate to another activity (OtherActivity.class)
                startActivity(new Intent(getApplicationContext(),Userpage.class));
                finish();
                // You can add more logic or data to the intent before starting the activity if needed
            }
        });

        // Set onClickListener for Admin Button
        buttonAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Define the intent to navigate to another activity (AdminActivity.class)
                if (CurrentUser.getCurrentUser().isCreator())
                    startActivity(new Intent(getApplicationContext(),AdminMain.class));
                finish();
                // You can add more logic or data to the intent before starting the activity if needed
            }
        });


    }

}
