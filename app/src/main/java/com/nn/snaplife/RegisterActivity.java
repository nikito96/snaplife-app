package com.nn.snaplife;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RegisterActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText repeatPasswordEditText;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.initViews();
        this.registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String repeatPassword = repeatPasswordEditText.getText().toString();

                new RegisterAsyncTask(email, username, password).execute();
            }
        });
    }

    private void initViews() {
        this.emailEditText = findViewById(R.id.emailEditText);
        this.usernameEditText = findViewById(R.id.usernameEditText);
        this.passwordEditText = findViewById(R.id.passwordEditText);
        this.repeatPasswordEditText = findViewById(R.id.repeatPasswordEditText);
        this.registerButton = findViewById(R.id.registerButton);
    }

    private class RegisterAsyncTask extends AsyncTask<Void, Void, Void> {

        private String email;
        private String username;
        private String password;

        RegisterAsyncTask(String email, String username, String password) {
            this.email = email;
            this.username = username;
            this.password = password;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String urlString = getString(R.string.register_url);
            HttpURLConnection conn = null;

            try {
                JSONObject outputObject = new JSONObject();
                outputObject.put("email", this.email);
                outputObject.put("username", this.username);
                outputObject.put("password", this.password);

                URL url = new URL(urlString);
                conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-type", "application/json");
                conn.setRequestProperty("Accept", "application/json");
                conn.setDoOutput(true);

                String outputString = outputObject.toString();
                byte[] output = outputString.getBytes();

                OutputStream os = conn.getOutputStream();

                os.write(output);

                InputStream inputStream = new BufferedInputStream(conn.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String response = bufferedReader.readLine();
                int reponseCode = conn.getResponseCode();

                if (reponseCode == 200) {
                    JSONObject responseObject = new JSONObject(response);
                    String username = responseObject.getString("username");
                } else {

                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                conn.disconnect();
            }

            return null;
        }
    }
}
