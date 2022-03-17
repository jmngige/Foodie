package com.starsolns.emenu.util

object Constants {

    const val MEAL_TYPE: String = "meaType"
    const val MEAL_CATEGORY: String = "mealCategory"
    const val MEAL_DURATION: String = "mealDuration"


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

        durationList.add("5-10")
        durationList.add("10-15")
        durationList.add("15-20")
        durationList.add("20-25")
        durationList.add("25-30")
        durationList.add("30-35")
        durationList.add("35-40")
        durationList.add("40-45")
        durationList.add("45-50")
        durationList.add("50-60")
        durationList.add("60-65")
        durationList.add("65-70")
        durationList.add("70-75")
        durationList.add("75-80")
        durationList.add("80-85")
        durationList.add("85-90")
        durationList.add("90-100")

        return durationList
    }



}