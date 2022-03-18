package com.starsolns.emenu.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
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

    init {
        repository = RecipeRepository(recipeDao)
    }


    fun insertRecipe(recipe: Recipe){
        viewModelScope.launch(Dispatchers.IO){
            repository.insertRecipe(recipe)
        }
    }


}