package com.selimhocaoglu.culinaryguide;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.selimhocaoglu.culinaryguide.model.Recipe;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        apiService = ApiClient.getClient().create(ApiService.class);

        fetchRecipes();
    }

    private void fetchRecipes() {
        Call<List<Recipe>> call = apiService.getRecipes();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if (response.isSuccessful()) {
                    List<Recipe> recipes = response.body();
                    recipeAdapter = new RecipeAdapter(recipes);
                    recyclerView.setAdapter(recipeAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                // Handle error
            }
        });
    }
}
