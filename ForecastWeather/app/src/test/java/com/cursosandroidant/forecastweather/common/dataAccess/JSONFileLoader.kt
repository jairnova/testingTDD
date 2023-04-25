package com.cursosandroidant.forecastweather.common.dataAccess

import com.cursosandroidant.forecastweather.entities.WeatherForecastEntity
import com.google.gson.Gson
import java.io.InputStreamReader

class JSONFileLoader {

    private var jsonStr : String? = null

    //Cómo leer ese archivo JSON que se creo

    //Estas funciones nos ayudaran a leer precisamente estos archivos JSON.
    //lo que devuelve el método puede ser null por no poderse garantizar una lectura dependera
    //totalmente del contenido de cada archivo.
    fun loadJSONString(file : String) : String?{
        val loader  = InputStreamReader(javaClass.classLoader?.getResourceAsStream(file))
        jsonStr = loader.readText()
        loader.close()
        return jsonStr
    }

    //Podemos transformarlo a las entidades que hemos creado dentro de nuestra aplicación.
    //No podemos garantizar desde la función que en nuestro archivo JSON tenga el formato adecuado.
    fun loadWeatherForecastEntity(file : String) : WeatherForecastEntity?{
        val loader  = InputStreamReader(javaClass.classLoader?.getResourceAsStream(file))
        jsonStr = loader.readText()
        loader.close()
        return Gson().fromJson(jsonStr, WeatherForecastEntity::class.java)
    }
}