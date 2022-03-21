package com.starsolns.emenu.data.repository

import androidx.lifecycle.LiveData
import com.starsolns.emenu.data.database.Recipe
import com.starsolns.emenu.data.database.RecipeDao

class RecipeRepository(private val recipeDao: RecipeDao) {

     val getAllRecipes : LiveData<List<Recipe>> = recipeDao.getAllRecipes()
    val getAllFavouriteRecipes: LiveData<List<Recipe>> = recipeDao.getAllFavouriteRecipes()

    suspend fun insertRecipe(recipe: Recipe){
        recipeDao.insertRecipe(recipe)
    }

    suspend fun updateRecipe(recipe: Recipe){
        recipeDao.updateRecipe(recipe)
    }

}