package com.example.examenpdm

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import coil.compose.AsyncImage
import com.example.examenpdm.data.Planta
import com.example.examenpdm.ui.theme.ExamenPDMTheme

class Detalles : ComponentActivity() {
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
                    muestra(p)
                }
            }
        }
    }
}

@Composable
fun muestra(
    p: Planta,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = Modifier.padding(15.dp)
    ) {
        Column {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .height(300.dp)
            ) {
                AsyncImage(
                    model = p.imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                val context = LocalContext.current
                FloatingActionButton(
                    onClick = {
                        (context as? Activity)?.finish()
                    },
                    shape = CircleShape,
                    modifier = Modifier
                        .padding(16.dp)
                        .size(50.dp)
                        .offset(x = (-140).dp, y = (-100).dp),
                    containerColor = Color.White
                ) {
                    Icon(Icons.Filled.ArrowBack, "Atras")
                }
                FloatingActionButton(
                    onClick = {
                        val shareIntent = Intent().apply {
                            action = Intent.ACTION_SEND
                            var textoCompartido = "NOMBRE: ${p.name} \nDESCRIPCION:  \n${p.description}"
                            putExtra(Intent.EXTRA_TEXT, textoCompartido)
                            type = "text/plain"
                        }
                        val chooser = Intent.createChooser(shareIntent, "Compartir")
                        context.startActivity(chooser)
                    },

                    shape = CircleShape,
                    modifier = Modifier
                        .padding(16.dp)
                        .size(50.dp)
                        .offset(x = (140).dp, y = (-100).dp),
                    containerColor = Color.White
                ) {
                    Icon(Icons.Filled.Share, "Compartir")
                }
                val list: List<Planta> = loadPlantsFromFile(context, "garden.json")
                if(!list.contains(p)) {
                    FloatingActionButton(
                        onClick = {
                            addPlantToFile(context, p)
                            Toast.makeText(
                                context,
                                "Planta agregada con exito.",
                                Toast.LENGTH_SHORT
                            ).show()
                            (context as? Activity)?.finish()
                        },
                        modifier = Modifier
                            .padding(16.dp)
                            .size(70.dp)
                            .offset(x = (140).dp, y = (150).dp),
                        //containerColor = Color(0xFFCFEC)
                        //#FFCFEC
                    ) {
                        Icon(Icons.Filled.Add, "Floating action button.")
                    }
                }
            }

            Text(
                text = "${p.name}",
                fontSize = 50.sp,
                modifier = Modifier
                    .padding(10.dp)
                    .align(Alignment.CenterHorizontally)
            )
            var intervaloRiego = ""
            if (p.wateringInterval == 1) {
                intervaloRiego = "Todos los dias"
            } else intervaloRiego = "Cada ${p.wateringInterval} dias"

            Text(
                text = "Intervalo de riego: $intervaloRiego ",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(5.dp)
            )

            convertidorHTML(p.description.toString())
        }
    }
}


@Composable
fun convertidorHTML(html: String) {
    val text = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY)
    RichText(text = text)
}

@Composable
fun RichText(text: CharSequence) {
    Text(
        text = buildAnnotatedString {
            append(text)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .verticalScroll(rememberScrollState()),
        fontSize = 20.sp // Cambia el tamaño de la fuente aquí
    )
}