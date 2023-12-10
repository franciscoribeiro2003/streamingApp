package com.netflixpp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.documentfile.provider.DocumentFile;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class UploadMovie extends AppCompatActivity {

    private static final int YOUR_REQUEST_CODE = 1001; // Replace with your request code
    private static final MediaType MP4 = MediaType.get("video/mp4");
    private Uri selectedFileUri;
    String pathURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_upload);

        // Your existing back button logic
        Button back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AdminMain.class));
                finish();
            }
        });

        // Select Video Button Logic
        Button selectVideoButton = findViewById(R.id.buttonvideo);
        selectVideoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("video/*");
                startActivityForResult(intent, YOUR_REQUEST_CODE);
            }
        });

        // Upload Movie Button Logic
        Button uploadMovieButton = findViewById(R.id.uploadmovief);
        uploadMovieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pathURL != null) {
                    // Upload video file
                    File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                    File sourceFile2 = new File(path,pathURL);
                    uploadVideo(Uri.fromFile(sourceFile2));
                    // Upload movie details (implement this method)
                    uploadMovieDetails();
                } else {
                    // Handle case where no video file is selected
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == YOUR_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                selectedFileUri = data.getData();
                if (selectedFileUri != null) {
                    pathURL = getRealPathFromURI(selectedFileUri);
                    Log.e("Upload", pathURL != null ? pathURL : "Path URL is null");
                }
            } else {
                Log.e("Upload", "Data is null");
            }
        } else {
            Log.e("Upload", "Failed to get result");
        }
    }

    // Method to upload the selected video file
    private void uploadVideo(Uri videoUri) {
        if (videoUri == null) {
            Log.e("UPLOAD", "Video Uri is null");
            return;
        }

        // Get the file path from the Uri
        String filePath = getRealPathFromURI(videoUri);

        if (filePath == null || filePath.isEmpty()) {
            Log.e("UPLOAD", "File path is null or empty");
            return;
        }

        OkHttpClient client = new OkHttpClient();

        File videoFile = new File(filePath);
        if (!videoFile.exists()) {
            Log.e("UPLOAD", "Video file doesn't exist");
            return;
        }

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", "video.mp4", RequestBody.create(videoFile, MP4))
                .build();

        Request request = new Request.Builder()
                .url("http://192.168.0.127:8080/movie/storeMp4/")
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("UPLOAD", "/store/mp4 encountered problems");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // Handle success
            }
        });
    }


    // Helper method to get real path from Uri
    private String getRealPathFromURI(Uri uri) {
        DocumentFile documentFile = DocumentFile.fromSingleUri(this, uri);
        String filePath = null;
        try {
            if (documentFile != null && documentFile.exists()) {
                filePath = documentFile.getUri().getPath();
            }
            return filePath;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filePath;
    }


    // Method to upload movie details
    private void uploadMovieDetails() {
        OkHttpClient client = new OkHttpClient();

        // Get values from EditText fields (assuming they are named accordingly in your layout)
        EditText movieNameEditText = findViewById(R.id.MovieName);
        EditText userNameEditText = findViewById(R.id.username);
        EditText yearEditText = findViewById(R.id.year);
        EditText durationEditText = findViewById(R.id.TotalTime);

        String movieName = movieNameEditText.getText().toString();
        String userName = userNameEditText.getText().toString();
        String year = yearEditText.getText().toString();
        String duration = durationEditText.getText().toString();

        // Create a JSON body with movie details
        JSONObject json = new JSONObject();
        try {
            json.put("moviename", movieName);
            json.put("username", userName);
            json.put("year", year);
            json.put("duration", duration);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(json.toString(), MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url("http://192.168.0.127:8080/movie/create")
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("UPLOAD", " creating details with problems with problems" );
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // Handle success
            }
        });
    }
}