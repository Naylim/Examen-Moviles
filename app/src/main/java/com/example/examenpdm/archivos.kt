package com.example.examenpdm

import android.content.Context
import com.example.examenpdm.data.Planta
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.*

fun addPlantToFile(context: Context, plant: Planta) {
    val filename = "garden.json"

    // Cargar los datos actuales del archivo si existe
    val plantsList: MutableList<Planta> = loadPlantsFromFile(context, filename).toMutableList()

    // Agregar el nuevo objeto a la lista
    plantsList.add(plant)

    GlobalScope.launch(Dispatchers.IO) {
        // Realiza la operación de escritura de archivos en un hilo secundario
        writePlantsToFile(context, plantsList, filename)
    }
    // Escribir la lista actualizada de nuevo en el archivo
}

fun loadPlantsFromFile(context: Context, filename: String): List<Planta> {
    val gson = Gson()
    val plantsListType = object : TypeToken<List<Planta>>() {}.type

    try {
        val inputStream: FileInputStream = context.openFileInput(filename)
        val reader = BufferedReader(InputStreamReader(inputStream))
        return gson.fromJson(reader, plantsListType)
    } catch (e: FileNotFoundException) {
        // El archivo no existe, retorna una lista vacía
        return emptyList()
    } catch (e: Exception) {
        e.printStackTrace()
        // Retorna una lista vacía en caso de error
        return emptyList()
    }
}

fun writePlantsToFile(context: Context, plantsList: List<Planta>, filename: String) {
    val gson = Gson()
    val jsonString = gson.toJson(plantsList)

    try {
        val outputStream: FileOutputStream = FileOutputStream(File(context.getFileStreamPath(filename).path))
        outputStream.use { it.write(jsonString.toByteArray()) }
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
    } catch (e: IOException) {
        e.printStackTrace()
    }
}

fun deletePlantFromFile(context: Context, plantName: String) {
    val filename = "garden.json"

    // Cargar los datos actuales del archivo si existe
    val plantsList: MutableList<Planta> = loadPlantsFromFile(context, filename).toMutableList()

    // Buscar el índice del elemento a eliminar
    val index = plantsList.indexOfFirst { it.name == plantName }
    if (index != -1) {
        // Eliminar el elemento de la lista
        plantsList.removeAt(index)

        // Escribir la lista actualizada de nuevo en el archivo
        writePlantsToFile(context, plantsList, filename)
    }
}
