package com.starsolns.emenu.data.database

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface RecipeDao {

    @Insert
    suspend fun insertRecipe(recipe: Recipe)

}