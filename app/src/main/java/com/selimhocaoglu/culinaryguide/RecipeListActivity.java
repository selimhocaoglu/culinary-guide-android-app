package com.selimhocaoglu.culinaryguide;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.selimhocaoglu.culinaryguide.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;
    private ApiService apiService;

    private Button btnSoups;
    private Button btnMainCourses;
    private Button btnDesserts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btnSoups = findViewById(R.id.btn_soups);
        btnMainCourses = findViewById(R.id.btn_main_courses);
        btnDesserts = findViewById(R.id.btn_desserts);

        apiService = ApiClient.getClient().create(ApiService.class);

        // Butonlar için tıklama olaylarını ayarla
        btnSoups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchRecipesByCategory("Soup");
            }
        });

        btnMainCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchRecipesByCategory("Main Course");
            }
        });

        btnDesserts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchRecipesByCategory("Dessert");
            }
        });
    }

    // Kategoriye göre tarifleri al
    private void fetchRecipesByCategory(String category) {
        Call<List<Recipe>> call = apiService.getRecipesByCategory(category);
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if (response.isSuccessful()) {
                    List<Recipe> recipes = response.body();
                    if (recipes != null && !recipes.isEmpty()) {
                        recipeAdapter = new RecipeAdapter(recipes, new RecipeAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(Recipe recipe) {
                                openRecipeDetailActivity(recipe.getId());
                            }
                        });
                        recyclerView.setAdapter(recipeAdapter);
                    } else {
                        Toast.makeText(RecipeListActivity.this, "No recipes found for this category.", Toast.LENGTH_SHORT).show();
                        recyclerView.setAdapter(null);  // Eğer tarif yoksa RecyclerView'ı temizle
                    }
                } else {
                    Toast.makeText(RecipeListActivity.this, "Failed to fetch recipes.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Toast.makeText(RecipeListActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openRecipeDetailActivity(Long recipeId) {
        Intent intent = new Intent(RecipeListActivity.this, RecipeDetailActivity.class);
        intent.putExtra("recipe_id", recipeId);
        startActivity(intent);
    }
}
