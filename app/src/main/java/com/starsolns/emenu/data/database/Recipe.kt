package com.starsolns.emenu.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipe_table")
data class Recipe (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val image: String,
    @ColumnInfo(name ="image_source")
    val imageSource: String,
    val name: String,
    val type: String,
    val category: String,
    val ingredients: String,
    @ColumnInfo(name="cooking_time")
    val cookingTime: String,
    @ColumnInfo(name="instructions")
    val directions: String
        )