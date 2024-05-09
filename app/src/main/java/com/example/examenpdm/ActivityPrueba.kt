package com.example.examenpdm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.examenpdm.ui.theme.ExamenPDMTheme

class ActivityPrueba: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val name = intent.getStringExtra("name")
        val imageUrl = intent.getStringExtra("imageUrl")
        val wateringInterval = intent.getIntExtra("wateringInterval", 999)
        val fecha= intent.getStringExtra("fecha")

        super.onCreate(savedInstanceState)
        setContent {
            ExamenPDMTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LazyVerticalGrid(
                        contentPadding = PaddingValues(10.dp), //espacio de bordes exteriores
                        columns = GridCells.Adaptive(150.dp) //columnas adaptativas
                    ){

                    }

                }
            }
        }
    }
}