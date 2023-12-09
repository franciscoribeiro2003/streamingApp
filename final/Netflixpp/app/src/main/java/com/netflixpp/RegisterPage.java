package com.netflixpp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterPage extends AppCompatActivity {
    String ip_cloud = "localhost";
    private EditText usernametext;
    private EditText passwordtext;
    private EditText fullnametext;
    private CheckBox isCreator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        Button loginpagebutton = (Button) findViewById(R.id.login);

        loginpagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),(LoginPage.class)));
                finish();
            }
        });
        usernametext = findViewById(R.id.user);
        passwordtext = findViewById(R.id.password);
        fullnametext = findViewById(R.id.full_name);
        isCreator = findViewById(R.id.creator);
        Button register = (Button) findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    submitForm();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void submitForm() throws IOException {

        URL urlAPI = null;
        try {
            urlAPI = new URL("http://"+ ip_cloud +":8080/user/create");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        OkHttpClient client = new OkHttpClient();

        String User = usernametext.getText().toString();
        String PW = passwordtext.getText().toString();
        String FN = fullnametext.getText().toString();
        boolean isChecked = isCreator.isChecked();

        String json = format(User, PW, FN,isChecked);

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody body = RequestBody.Companion.create(json,JSON);

        Log.i("LOG_INTENT", User + PW + FN);
        Request request = new Request.Builder()
                .url(urlAPI)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                String mMessage = e.getMessage();
                showToastError("Problems connecting with the server");
                Log.w("failure Response", mMessage);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String mMessage = response.body().string();

                int responseCode = response.code();

                if(response.code() == 200) {
                    startActivity(new Intent(getApplicationContext(), (LoginPage.class)));
                    finish();
                    showToast(FN);
                }
                else{
                    showToastError("Already exists this Username");
                }
            }
        });
    }
    public String format(String user, String pass, String fulln, boolean isChecked) {
        String contentCreator = isChecked ? "true" : "false";

        return "{\n" +
                "\"username\"" + ":" + "\"" + user + "\"" + ",\n" +
                "\"password\"" + ":" + "\"" + pass + "\"" + ",\n" +
                "\"fullname\"" + ":" + "\"" + fulln + "\"" + ",\n" +
                "\"isContentCreator\"" + ":" + contentCreator + "\n" +
                "}";
    }
    public void showToast(final String Text){
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(RegisterPage.this,"Welcome " + Text + " now proceed to the login", Toast.LENGTH_LONG).show();
            }
        });
    }
    public void showToastError(final String Text){
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(RegisterPage.this, Text, Toast.LENGTH_LONG).show();
            }
        });
    }


}
