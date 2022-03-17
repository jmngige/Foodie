package com.starsolns.emenu.data.repository

import com.starsolns.emenu.data.database.Recipe
import com.starsolns.emenu.data.database.RecipeDao

class RecipeRepository(private val recipeDao: RecipeDao) {

    suspend fun insertRecipe(recipe: Recipe){
        recipeDao.insertRecipe(recipe)
    }

}