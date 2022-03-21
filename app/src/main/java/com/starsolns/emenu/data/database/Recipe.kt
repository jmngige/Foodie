package com.starsolns.emenu.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "recipe_table")
data class Recipe (
    var image: String,
    @ColumnInfo(name ="image_source")
    var imageSource: String,
    var name: String,
    var type: String,
    var category: String,
    var ingredients: String,
    @ColumnInfo(name="cooking_time")
    var cookingTime: String,
    @ColumnInfo(name="instructions")
    var directions: String,
    @ColumnInfo(name = "favourite_dish")
    var favourite: Boolean = false,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0): Serializable