package com.starsolns.emenu.util

object Constants {

    const val MEAL_TYPE: String = "mealType"
    const val MEAL_CATEGORY: String = "mealCategory"
    const val MEAL_DURATION: String = "mealDuration"

    const val IMAGE_SOURCE_LOCAL = "local"
    const val IMAGE_SOURCE_ONLINE = "online"


    fun getMealTypes(): ArrayList<String>{
        val typesList = ArrayList<String>()

        typesList.add("Breakfast")
        typesList.add("Brunch")
        typesList.add("Lunch")
        typesList.add("Snacks")
        typesList.add("Supper")
        typesList.add("Salad")
        typesList.add("Desert")
        typesList.add("Other")

        return typesList
    }

    fun getMealCategories(): ArrayList<String>{
        val categoriesList = ArrayList<String>()

        categoriesList.add("Pizza")
        categoriesList.add("Chicken")
        categoriesList.add("Salad")
        categoriesList.add("Burger")
        categoriesList.add("Juice")
        categoriesList.add("Smokies")
        categoriesList.add("Sandwich")
        categoriesList.add("Coffee")
        categoriesList.add("Tea")
        categoriesList.add("Cake")
        categoriesList.add("BBQ")
        categoriesList.add("Chapati")
        categoriesList.add("Beef")
        categoriesList.add("Ugali")
        categoriesList.add("Other")

        return categoriesList
    }

    fun getMealDuration(): ArrayList<String>{
        val durationList = ArrayList<String>()

        durationList.add("5-10 Minutes")
        durationList.add("10-15 Minutes")
        durationList.add("15-20 Minutes")
        durationList.add("20-25 Minutes")
        durationList.add("25-30 Minutes")
        durationList.add("30-35 Minutes")
        durationList.add("35-40 Minutes")
        durationList.add("40-45 Minutes")
        durationList.add("45-50 Minutes")
        durationList.add("50-60 Minutes")
        durationList.add("60-65 Minutes")
        durationList.add("65-70 Minutes")
        durationList.add("70-75 Minutes")
        durationList.add("75-80 Minutes")
        durationList.add("80-85 Minutes")
        durationList.add("85-90 Minutes")
        durationList.add("90-100 Minutes")

        return durationList
    }



}