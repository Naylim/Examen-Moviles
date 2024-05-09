package com.example.examenpdm.data

import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class DataProvider {
    var Plantas: List<Planta> = emptyList()
        private set

    fun loadData(context: Context, fileName: String){
        val plantsJsonString = context.assets.open(fileName).bufferedReader().use{
            it.readText()
        }
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val adapter = moshi.adapter(Array<Planta>::class.java)
        //adapta la info
        Plantas = adapter.fromJson(plantsJsonString)?.toList() ?: emptyList()
    }
}