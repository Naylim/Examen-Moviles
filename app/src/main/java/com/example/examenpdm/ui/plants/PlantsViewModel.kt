package com.example.examenpdm.ui.plants

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.examenpdm.data.DataProvider
import com.example.examenpdm.data.Planta

class PlantsViewModel {
    private val dataProvider = DataProvider()
    val listaPlantas: MutableState<List<Planta>> = mutableStateOf(emptyList())

    fun loadData(context: Context){
        dataProvider.loadData(context, "plants.json")
        listaPlantas.value = dataProvider.Plantas
    }
}