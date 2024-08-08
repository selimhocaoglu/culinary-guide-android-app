package com.selimhocaoglu.culinaryguide;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.selimhocaoglu.culinaryguide.model.UserRecipe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddUserRecipeActivity extends AppCompatActivity {

    private EditText etRecipeId;
    private EditText etMealType;
    private EditText etDate;
    private Button btnAddUserRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user_recipe);

        etRecipeId = findViewById(R.id.et_recipe_id);
        etMealType = findViewById(R.id.et_meal_type);
        etDate = findViewById(R.id.et_date);
        btnAddUserRecipe = findViewById(R.id.btn_add_user_recipe);

        btnAddUserRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUserRecipe();
            }
        });
    }

    private void addUserRecipe() {
        String recipeIdStr = etRecipeId.getText().toString();
        String mealType = etMealType.getText().toString();
        String dateStr = etDate.getText().toString();

        if (recipeIdStr.isEmpty() || mealType.isEmpty() || dateStr.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        long recipeId = Long.parseLong(recipeIdStr);
        UserRecipe userRecipe = new UserRecipe();
        userRecipe.setRecipeId(recipeId);
        userRecipe.setMealType(mealType);

        // Parse the date string to a Date object
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"); // Match the format you expect
        try {
            Date date = sdf.parse(dateStr);
            userRecipe.setDate(date);
        } catch (ParseException e) {
            Toast.makeText(this, "Invalid date format", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<UserRecipe> call = apiService.addUserRecipe(userRecipe);
        call.enqueue(new Callback<UserRecipe>() {
            @Override
            public void onResponse(Call<UserRecipe> call, Response<UserRecipe> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AddUserRecipeActivity.this, "User recipe added successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddUserRecipeActivity.this, "Failed to add user recipe", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserRecipe> call, Throwable t) {
                Toast.makeText(AddUserRecipeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
