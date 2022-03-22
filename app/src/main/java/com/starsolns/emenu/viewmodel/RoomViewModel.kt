package com.starsolns.emenu.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.starsolns.emenu.data.database.Recipe
import com.starsolns.emenu.data.database.RecipeDao
import com.starsolns.emenu.data.database.RecipeDatabase
import com.starsolns.emenu.data.repository.RecipeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RoomViewModel(application: Application): AndroidViewModel(application) {

    private var recipeDao = RecipeDatabase.getDatabase(application).recipeDao()
    private var repository: RecipeRepository


    val getAllRecipes: LiveData<List<Recipe>>
    val getAllFavouriteRecipes: LiveData<List<Recipe>>

    init {
        repository = RecipeRepository(recipeDao)
        getAllRecipes = repository.getAllRecipes
        getAllFavouriteRecipes = repository.getAllFavouriteRecipes
    }

    fun insertRecipe(recipe: Recipe){
        viewModelScope.launch(Dispatchers.IO){
            repository.insertRecipe(recipe)
        }
    }

    fun updateRecipe(recipe: Recipe){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateRecipe(recipe)
        }
    }

    fun deleteRecipe(recipe: Recipe){
        viewModelScope.launch(Dispatchers.IO){
        repository.deleteRecipe(recipe)
        }
    }


}