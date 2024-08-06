package com.selimhocaoglu.culinaryguide;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.selimhocaoglu.culinaryguide.model.Recipe;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private List<Recipe> recipes;

    public RecipeAdapter(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        holder.nameTextView.setText(recipe.getName());
        holder.ingredientsTextView.setText(recipe.getIngredients());
        holder.instructionsTextView.setText(recipe.getInstructions());
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView;
        TextView ingredientsTextView;
        TextView instructionsTextView;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            ingredientsTextView = itemView.findViewById(R.id.ingredientsTextView);
            instructionsTextView = itemView.findViewById(R.id.instructionsTextView);
        }
    }
}

