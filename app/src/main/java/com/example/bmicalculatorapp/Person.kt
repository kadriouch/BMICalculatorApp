package com.example.bmicalculatorapp

data class Person(
    val weight: Double,
    val heightCm: Double,
    val gender: String
) {
    fun calculateBMI(): Double {
        val heightM = heightCm / 100.0
        return weight / (heightM * heightM)
    }

    fun getCategory(): BMICategory {
        val bmi = calculateBMI()
        return when {
            bmi < 18.5 -> BMICategory.UNDERWEIGHT
            bmi < 25.0 -> BMICategory.NORMAL
            bmi < 30.0 -> BMICategory.OVERWEIGHT
            else -> BMICategory.OBESE
        }
    }
}