package com.example.examenpdm

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat.recreate
import coil.compose.AsyncImage
import com.example.examenpdm.data.Planta
import com.example.examenpdm.ui.theme.ExamenPDMTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MyGarden : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val description = intent.getStringExtra("description")
        val growZoneNumber = intent.getIntExtra("growZoneNumber",999)
        val imageUrl = intent.getStringExtra("imageUrl")
        val name = intent.getStringExtra("name")
        val plantId = intent.getStringExtra("plantId")
        val wateringInterval = intent.getIntExtra("wateringInterval", 9999)
        val p = Planta(description,growZoneNumber,imageUrl,name,plantId,wateringInterval)

        setContent {
            ExamenPDMTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GardenList()
                }
            }
        }
    }
}


@SuppressLint("SuspiciousIndentation")
@Composable
fun GardenList() {
    val context = LocalContext.current
    val list: List<Planta> = loadPlantsFromFile(context, "garden.json")
    if(list.isEmpty()){
        noHay()
    }else {
        LazyVerticalGrid(
            contentPadding = PaddingValues(10.dp), //espacio de bordes exteriores
            columns = GridCells.Adaptive(150.dp) //columnas adaptativas
        ) {
            items(list) { planta ->
                GardenItem(planta, Modifier.padding(5.dp))
            }
        }
    }
}
@Composable
fun noHay(){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(text = "No hay plantitas", fontWeight = FontWeight.Bold, fontSize = 25.sp)
    }
}

@Composable
fun GardenItem(item: Planta, modifier: Modifier = Modifier){
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
                FloatingActionButton(
                    onClick = {
                        deletePlantFromFile(context, item.name.toString())
                        Toast.makeText(
                            context,
                            "${item.name} eliminado de la lista",
                            Toast.LENGTH_SHORT
                        ).show()
                        (context as? Activity)?.recreate()
                    },
                    shape = CircleShape,
                    modifier = Modifier
                        .padding(16.dp)
                        .size(60.dp)
                        .offset(x = (60).dp, y = (80).dp),
                    containerColor = Color.Black
                    //#FFCFEC
                ) {
                    Icon(Icons.Filled.Delete, "Floating action button.")
                }
            }
            Box( //contenedor de titulo
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center,
            ){
                Column (
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                )
                {
                    Text(item.name.toString(), fontSize = 25.sp,)
                    Text(text = "Se agrego el dia:", fontWeight = FontWeight.Bold)
                    Text(text = "${FechaActual()}")
                    Text(text = "Ultimo regado:", fontWeight = FontWeight.Bold)
                    Text(text = "${FechaActual()}")
                    var intervaloRiego: String
                    if(item.wateringInterval == 1 ){
                        intervaloRiego="Todos los dias"
                    }else intervaloRiego="Cada ${item.wateringInterval} dias"
                    Text(text = "Intervalo de riego: ", fontWeight = FontWeight.Bold)
                    Text(text = "$intervaloRiego")
                }
            }
        }
    }
}
fun FechaActual():String? {
    val fechaActual = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy") // Formato de fecha
    val fecha = fechaActual.format(formatter).toString()
    return fecha
}
