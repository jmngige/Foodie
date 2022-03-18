package com.starsolns.emenu.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RecipeDao {

    @Insert
    suspend fun insertRecipe(recipe: Recipe)

    @Query("SELECT * FROM recipe_table ORDER BY id")
    fun getAllRecipes(): LiveData<List<Recipe>>

}