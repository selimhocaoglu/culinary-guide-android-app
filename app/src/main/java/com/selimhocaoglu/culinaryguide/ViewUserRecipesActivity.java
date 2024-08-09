package com.selimhocaoglu.culinaryguide;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.selimhocaoglu.culinaryguide.model.UserRecipe;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewUserRecipesActivity extends AppCompatActivity {

    private Button btnViewUserRecipes;
    private ListView lvUserRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user_recipes);

        btnViewUserRecipes = findViewById(R.id.btn_view_user_recipes);
        lvUserRecipes = findViewById(R.id.lv_user_recipes);

        btnViewUserRecipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewUserRecipes();
            }
        });

        // Tarife tıklandığında ilgili RecipeDetailActivity'ye yönlendirme
        lvUserRecipes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserRecipe userRecipe = (UserRecipe) parent.getItemAtPosition(position);
                Long recipeId = userRecipe.getRecipeId();

                if (recipeId != null) {
                    Intent intent = new Intent(ViewUserRecipesActivity.this, RecipeDetailActivity.class);
                    intent.putExtra("recipe_id", recipeId);
                    startActivity(intent);
                } else {
                    Toast.makeText(ViewUserRecipesActivity.this, "Recipe ID not found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void viewUserRecipes() {
        // UserSessionManager üzerinden userId'yi alıyoruz
        Long userId = UserSessionManager.getInstance().getCurrentUserId();

        if (userId == null) {
            Toast.makeText(this, "User not logged in. Please log in.", Toast.LENGTH_SHORT).show();
            finish(); // Aktiviteyi bitirerek kullanıcıyı giriş ekranına yönlendirebilirsiniz.
            return;
        }

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<List<UserRecipe>> call = apiService.getUserRecipesByUserId(userId);
        call.enqueue(new Callback<List<UserRecipe>>() {
            @Override
            public void onResponse(Call<List<UserRecipe>> call, Response<List<UserRecipe>> response) {
                if (response.isSuccessful()) {
                    List<UserRecipe> userRecipes = response.body();

                    // Tarifleri tarihe göre yeniden eskiye doğru sıralayın
                    if (userRecipes != null) {
                        Collections.sort(userRecipes, new Comparator<UserRecipe>() {
                            @Override
                            public int compare(UserRecipe o1, UserRecipe o2) {
                                return o2.getDate().compareTo(o1.getDate());
                            }
                        });
                    }

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
    }
}
