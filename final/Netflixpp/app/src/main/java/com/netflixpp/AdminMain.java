package com.netflixpp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AdminMain extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminmain);

        Button upload = (Button) findViewById(R.id.uploadMovie);
        Button deleteMovie = (Button) findViewById(R.id.DeleteMovie);
        Button user_mode = (Button) findViewById(R.id.user_mode);
        Button user_page = (Button) findViewById(R.id.gouserpage);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),(UploadMovie.class)));
                finish();
            }
        });

        deleteMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),(DeleteMovie.class)));
                finish();
            }
        });

        user_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),(MainActivity.class)));
                finish();
            }
        });

        user_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),(Userpage.class)));
                finish();
            }
        });


    }
}
