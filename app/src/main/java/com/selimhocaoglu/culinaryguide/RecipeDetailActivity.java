package com.selimhocaoglu.culinaryguide;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.selimhocaoglu.culinaryguide.model.Recipe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeDetailActivity extends AppCompatActivity {

    private TextView nameTextView;
    private TextView ingredientsTextView;
    private TextView instructionsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        nameTextView = findViewById(R.id.nameTextView);
        ingredientsTextView = findViewById(R.id.ingredientsTextView);
        instructionsTextView = findViewById(R.id.instructionsTextView);

        long recipeId = getIntent().getLongExtra("recipe_id", -1);
        if (recipeId != -1) {
            loadRecipeDetails(recipeId);
        } else {
            Toast.makeText(this, "Recipe ID not found!", Toast.LENGTH_SHORT).show();
            finish();  // Eğer recipeId bulunamazsa, activity'yi kapatır.
        }
    }

    private void loadRecipeDetails(long recipeId) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<Recipe> call = apiService.getRecipeById(recipeId);
        call.enqueue(new Callback<Recipe>() {
            @Override
            public void onResponse(Call<Recipe> call, Response<Recipe> response) {
                if (response.isSuccessful()) {
                    Recipe recipe = response.body();
                    if (recipe != null) {
                        nameTextView.setText(recipe.getName());
                        ingredientsTextView.setText(recipe.getIngredients());
                        instructionsTextView.setText(recipe.getInstructions());
                    }
                } else {
                    Toast.makeText(RecipeDetailActivity.this, "Failed to load recipe details", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Recipe> call, Throwable t) {
                Toast.makeText(RecipeDetailActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
