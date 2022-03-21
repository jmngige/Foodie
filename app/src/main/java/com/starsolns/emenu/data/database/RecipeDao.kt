package com.starsolns.emenu.data.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: Recipe)

    @Query("SELECT * FROM recipe_table ORDER BY id ASC")
    fun getAllRecipes(): LiveData<List<Recipe>>

    @Update
    suspend fun updateRecipe(recipe: Recipe)

    @Query("SELECT * FROM recipe_table WHERE favourite_dish = 1")
    fun getAllFavouriteRecipes(): LiveData<List<Recipe>>

}