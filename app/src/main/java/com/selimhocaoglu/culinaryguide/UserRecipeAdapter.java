package com.selimhocaoglu.culinaryguide;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.selimhocaoglu.culinaryguide.model.UserRecipe;

import java.text.SimpleDateFormat;
import java.util.List;

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
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_user_recipe, parent, false);
        }

        UserRecipe userRecipe = userRecipes.get(position);

        TextView tvRecipeId = convertView.findViewById(R.id.tv_recipe_id);
        TextView tvMealType = convertView.findViewById(R.id.tv_meal_type);
        TextView tvDate = convertView.findViewById(R.id.tv_date);

        tvRecipeId.setText("Recipe ID: " + userRecipe.getRecipeId());
        tvMealType.setText("Meal Type: " + userRecipe.getMealType());
        tvDate.setText("Date: " + new SimpleDateFormat("yyyy-MM-dd").format(userRecipe.getDate()));

        return convertView;
    }
}
