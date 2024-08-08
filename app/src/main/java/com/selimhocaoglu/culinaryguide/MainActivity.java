package com.selimhocaoglu.culinaryguide;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnViewRecipes;
    private Button btnAddUserRecipe;
    private Button btnViewUserRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnViewRecipes = findViewById(R.id.btn_view_recipes);
        btnAddUserRecipe = findViewById(R.id.btn_add_user_recipe);
        btnViewUserRecipes = findViewById(R.id.btn_view_user_recipes);

        btnViewRecipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RecipeListActivity.class);
                startActivity(intent);
            }
        });

        btnAddUserRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddUserRecipeActivity.class);
                startActivity(intent);
            }
        });

        btnViewUserRecipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewUserRecipesActivity.class);
                startActivity(intent);
            }
        });
    }
}
