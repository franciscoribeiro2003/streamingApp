package com.netflixpp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
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

public class LoginPage extends AppCompatActivity {
    private EditText userTemp;
    private EditText passTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);


        Button login=(Button) findViewById(R.id.login);
        Button register=(Button) findViewById(R.id.register);
        userTemp=findViewById(R.id.user);
        passTemp=findViewById(R.id.password);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //try {
                    startActivity(new Intent(getApplicationContext(),AdminMain.class));
                    finish();
                    //pushInfo();
                //} catch (IOException e) {
                //    e.printStackTrace();
                //}
            }
        });



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),(RegisterPage.class)));
                finish();
            }
        });



    }

    public void pushInfo() throws IOException{
        URL url = null;
        try {url = new URL("http://localhost/user/login");}
        catch (MalformedURLException e ) {e.printStackTrace();}

        OkHttpClient client = new OkHttpClient();
        String user=userTemp.getText().toString();
        String password=passTemp.getText().toString();

        String jsonfile=toJson(user,password);
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody body = RequestBody.Companion.create(jsonfile,JSON);

        Request request= new Request.Builder()
                .url(url)
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.code()==200){
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();
                }
                else{
                    Log.i("Wrong", "wrong user");
                    startActivity(new Intent(getApplicationContext(),MovieCatalogue.class));
                    finish();
                }
            }
        });

    }

    String toJson(String username, String password) {
        return "{\n" + "\"username\"" + ":" + "\"" +  username + "\"" +  ",\n" +
                "\"password\"" +":"+ "\"" + password + "\"\n" + "}";

    }

}
