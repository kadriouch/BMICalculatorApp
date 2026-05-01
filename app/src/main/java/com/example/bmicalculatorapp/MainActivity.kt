package com.example.bmicalculatorapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    BMIScreen()
                }
            }
        }
    }
}

@Composable
fun BMIScreen() {
    var weightInput by remember { mutableStateOf("") }
    var heightInput by remember { mutableStateOf("") }
    var selectedGender by remember { mutableStateOf("Male") }
    var bmiResult by remember { mutableStateOf<Double?>(null) }
    var bmiCategory by remember { mutableStateOf<BMICategory?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "BMI Calculator",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Gender selection
        Row {
            listOf("Male", "Female").forEach { gender ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = selectedGender == gender,
                        onClick = { selectedGender = gender }
                    )
                    Text(text = gender)
                    Spacer(modifier = Modifier.width(16.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Weight input
        OutlinedTextField(
            value = weightInput,
            onValueChange = { weightInput = it },
            label = { Text("Weight (kg)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Height input
        OutlinedTextField(
            value = heightInput,
            onValueChange = { heightInput = it },
            label = { Text("Height (cm)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Calculate button
        Button(
            onClick = {
                val weight = weightInput.toDoubleOrNull()
                val height = heightInput.toDoubleOrNull()
                if (weight != null && height != null && height > 0) {
                    val person = Person(
                        weight = weight,
                        heightCm = height,
                        gender = selectedGender
                    )
                    bmiResult = person.calculateBMI()
                    bmiCategory = person.getCategory()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calculate BMI", fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Result display
        val result = bmiResult
        val category = bmiCategory
        if (result != null && category != null) {
            val color = when (category) {
                BMICategory.NORMAL -> Color.Green
                else -> Color.Red
            }
            Text(
                text = "Your BMI: %.1f".format(result),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = category.message,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = color
            )
        }
    }
}