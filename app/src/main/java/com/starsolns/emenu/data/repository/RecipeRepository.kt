package com.starsolns.emenu.data.repository

import androidx.lifecycle.LiveData
import com.starsolns.emenu.data.database.Recipe
import com.starsolns.emenu.data.database.RecipeDao

class RecipeRepository(private val recipeDao: RecipeDao) {

     val getAllRecipes : LiveData<List<Recipe>> = recipeDao.getAllRecipes()

    suspend fun insertRecipe(recipe: Recipe){
        recipeDao.insertRecipe(recipe)
    }

}