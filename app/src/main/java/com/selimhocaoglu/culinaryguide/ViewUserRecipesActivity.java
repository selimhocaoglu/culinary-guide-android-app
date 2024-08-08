package com.selimhocaoglu.culinaryguide;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.selimhocaoglu.culinaryguide.model.UserRecipe;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewUserRecipesActivity extends AppCompatActivity {

    private EditText etDate;
    private Button btnViewUserRecipes;
    private ListView lvUserRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user_recipes);

        etDate = findViewById(R.id.et_date);
        btnViewUserRecipes = findViewById(R.id.btn_view_user_recipes);
        lvUserRecipes = findViewById(R.id.lv_user_recipes);

        btnViewUserRecipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewUserRecipes();
            }
        });
    }

    private void viewUserRecipes() {
        String dateStr = etDate.getText().toString();

        if (dateStr.isEmpty()) {
            Toast.makeText(this, "Please enter a date", Toast.LENGTH_SHORT).show();
            return;
        }

        Long userId = 1L; // Bu değeri gerçek kullanıcı ID'si ile değiştirin
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);

            ApiService apiService = ApiClient.getClient().create(ApiService.class);
            Call<List<UserRecipe>> call = apiService.getUserRecipesByDate(userId, String.valueOf(date));
            call.enqueue(new Callback<List<UserRecipe>>() {
                @Override
                public void onResponse(Call<List<UserRecipe>> call, Response<List<UserRecipe>> response) {
                    if (response.isSuccessful()) {
                        List<UserRecipe> userRecipes = response.body();
                        UserRecipeAdapter adapter = new UserRecipeAdapter(ViewUserRecipesActivity.this, userRecipes);
                        lvUserRecipes.setAdapter(adapter);
                    } else {
                        Toast.makeText(ViewUserRecipesActivity.this, "Failed to retrieve user recipes", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<UserRecipe>> call, Throwable t) {
                    Toast.makeText(ViewUserRecipesActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Toast.makeText(this, "Invalid date format", Toast.LENGTH_SHORT).show();
        }
    }
}
