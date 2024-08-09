package com.selimhocaoglu.culinaryguide.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.selimhocaoglu.culinaryguide.ApiClient;
import com.selimhocaoglu.culinaryguide.ApiService;
import com.selimhocaoglu.culinaryguide.MainActivity;
import com.selimhocaoglu.culinaryguide.R;
import com.selimhocaoglu.culinaryguide.UserSessionManager;
import com.selimhocaoglu.culinaryguide.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }

    private void loginUser() {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        Call<User> call = apiService.loginUser(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User loggedInUser = response.body();

                    // Kullanıcıyı UserSessionManager'a kaydediyoruz
                    UserSessionManager.getInstance().setCurrentUser(loggedInUser);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

