package com.example.examenpdm

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.examenpdm.data.Planta
import com.example.examenpdm.ui.plants.PlantsViewModel

private val plantsViewModel = PlantsViewModel()
@Composable
fun MediaList() {
    val context = LocalContext.current
    // Carga los datos al iniciar la composable
    LaunchedEffect(key1 = true) {
        plantsViewModel.loadData(context)
    }
    val plantsState by plantsViewModel.listaPlantas
    // Muestra la lista de plantas en la UI

    LazyVerticalGrid(
        contentPadding = PaddingValues(10.dp), //espacio de bordes exteriores
        columns = GridCells.Adaptive(150.dp) //columnas adaptativas
    ){
        items(plantsState){ planta ->
            MediaItem(planta, Modifier.padding(5.dp))
            //los items seran cards, recorriendo la lista de plantas
        }
    }
}

@JvmOverloads
@Composable
fun MediaItem(item: Planta, modifier: Modifier = Modifier){
    val context = LocalContext.current
    Card(
        modifier = modifier
            .clickable {
                val intent = Intent(context, Detalles::class.java)
                intent.putExtra("description", item.description)
                intent.putExtra("growZoneNumber", item.growZoneNumber)
                intent.putExtra("imageUrl", item.imageUrl)
                intent.putExtra("name", item.name)
                intent.putExtra("plantId", item.plantId)
                intent.putExtra("wateringInterval", item.wateringInterval)
                context.startActivity(intent)
            },
    ){
        Column() {
            Box( //contenedor de imagen
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .width(200.dp)
                    .height(200.dp)
            ){
                AsyncImage(
                    model = item.imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }
            Box( //contenedor de titulo
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center,
            ){
                Text(item.name.toString())
            }
        }
    }
}