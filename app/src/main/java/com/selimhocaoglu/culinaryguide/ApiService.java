package com.selimhocaoglu.culinaryguide;

import com.selimhocaoglu.culinaryguide.model.Comment;
import com.selimhocaoglu.culinaryguide.model.Recipe;
import com.selimhocaoglu.culinaryguide.model.User;
import com.selimhocaoglu.culinaryguide.model.UserRecipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @POST("/users/register")
    Call<Void> registerUser(@Body User user);

    @POST("/users/login")
    Call<User> loginUser(@Body User user);

    @GET("/recipes")
    Call<List<Recipe>> getRecipes();

    @POST("/user-recipes")
    Call<UserRecipe> addUserRecipe(@Body UserRecipe userRecipe);

    @GET("/user-recipes/{userId}")
    Call<List<UserRecipe>> getUserRecipes(@Path("userId") Long userId);

    @GET("/user-recipes/{userId}/date")
    Call<List<UserRecipe>> getUserRecipesByDate(@Path("userId") Long userId, @Query("date") String date);

    @GET("/user-recipes/{userId}/date/meal")
    Call<List<UserRecipe>> getUserRecipesByDateAndMealType(@Path("userId") Long userId, @Query("date") String date, @Query("mealType") String mealType);

    @POST("/recipes")
    Call<Recipe> createRecipe(@Body Recipe recipe);

    @GET("/recipes/{id}")
    Call<Recipe> getRecipeById(@Path("id") Long id);

    @POST("/recipes/{recipeId}/comments")
    Call<Comment> addComment(@Path("recipeId") Long recipeId, @Body Comment comment);

    @GET("/recipes/{recipeId}/comments")
    Call<List<Comment>> getComments(@Path("recipeId") Long recipeId);

    @GET("/recipes/by-category")
    Call<List<Recipe>> getRecipesByCategory(@Query("category") String category);
}

