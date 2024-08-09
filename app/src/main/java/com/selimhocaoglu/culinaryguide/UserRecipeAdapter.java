package com.selimhocaoglu.culinaryguide;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.selimhocaoglu.culinaryguide.model.Recipe;
import com.selimhocaoglu.culinaryguide.model.UserRecipe;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class UserRecipeAdapter extends ArrayAdapter<UserRecipe> {

    private Context context;
    private List<UserRecipe> userRecipes;

    public UserRecipeAdapter(Context context, List<UserRecipe> userRecipes) {
        super(context, 0, userRecipes);
        this.context = context;
        this.userRecipes = userRecipes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UserRecipe userRecipe = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_user_recipe, parent, false);
        }


//        TextView tvRecipeId = convertView.findViewById(R.id.tv_recipe_name);
//        TextView tvMealType = convertView.findViewById(R.id.tv_meal_type);
//        TextView tvDate = convertView.findViewById(R.id.tv_date);
//
//        tvRecipeId.setText("Recipe ID: " + userRecipe.getRecipeId());
//        tvMealType.setText("Meal Type: " + userRecipe.getMealType());
//        tvDate.setText("Date: " + new SimpleDateFormat("yyyy-MM-dd").format(userRecipe.getDate()));

        TextView tvTitle = convertView.findViewById(R.id.recipeTitle);
        TextView tvCategory = convertView.findViewById(R.id.recipeCategory);
        TextView tvRecipeDateMealType = convertView.findViewById(R.id.recipeDateMealType);

        // Recipe üzerinden başlık ve kategoriyi alıyoruz
        if (userRecipe.getRecipe() != null) {
            tvTitle.setText(userRecipe.getRecipe().getName()); // Yemek adı
            tvCategory.setText(userRecipe.getRecipe().getCategory()); // Yemek kategorisi
        } else {
            tvTitle.setText("Unknown Recipe"); // Recipe verisi bulunamazsa
            tvCategory.setText("Unknown Category");
        }

        // Set date and meal type
        String dateMealTypeText = "Date: " + new SimpleDateFormat("yyyy-MM-dd").format(userRecipe.getDate()) +
                " | Meal: " + userRecipe.getMealType();
        tvRecipeDateMealType.setText(dateMealTypeText);

        // Her bir öğeye tıklama özelliği ekleyin
        tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RecipeDetailActivity.class);
                intent.putExtra("recipeId", userRecipe.getRecipeId());
                context.startActivity(intent);
            }
        });

        return convertView;
    }
}
