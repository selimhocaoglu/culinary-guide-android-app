package com.selimhocaoglu.culinaryguide;

import com.selimhocaoglu.culinaryguide.model.Comment;
import com.selimhocaoglu.culinaryguide.model.Recipe;
import com.selimhocaoglu.culinaryguide.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    @POST("/users/register")
    Call<Void> registerUser(@Body User user);

    @POST("/users/login")
    Call<User> loginUser(@Body User user);

    @GET("/recipes")
    Call<List<Recipe>> getRecipes();

    @POST("/recipes")
    Call<Recipe> createRecipe(@Body Recipe recipe);

    @GET("/recipes/{id}")
    Call<Recipe> getRecipeById(@Path("id") Long id);

    @POST("/recipes/{recipeId}/comments")
    Call<Comment> addComment(@Path("recipeId") Long recipeId, @Body Comment comment);

    @GET("/recipes/{recipeId}/comments")
    Call<List<Comment>> getComments(@Path("recipeId") Long recipeId);
}

