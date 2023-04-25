package com.cursosandroidant.forecastweather.common.dataAccess

import com.cursosandroidant.forecastweather.entities.WeatherForecastEntity
import com.google.gson.Gson
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.containsString
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.notNullValue
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import javax.net.ssl.HttpsURLConnection

@RunWith(MockitoJUnitRunner::class)
class ResponseServerTest {

    private lateinit var mockWebServer : MockWebServer

    @Before
    fun setup(){
        mockWebServer = MockWebServer()
        mockWebServer.start()
    }

    //liberar el recurso después de cada prueba.
    @After
    fun tearDown(){
        mockWebServer.shutdown()
    }

    //vamos a verificar que realmente estamos leyendo el archivo JSON correctamente. Usaremos el
    // archivo JSON anteriormente creado
    @Test
    fun `read json file success`(){
        val reader = JSONFileLoader().loadJSONString("weather_forecast_response_success")
        //validamos que el JSON no esta null sino también ya comienza a coincidir con lo que
        //nosotros tenemos
        assertThat(reader, `is`(notNullValue()))
        assertThat(reader, containsString("America/Mexico_City"))
    }

    //Pruebas relacionadas con las respuestas del servidor. Se trata de Mock web service
    //Esta herramienta nos permite hacer lo mismo que el servidor pero de forma local ahorrando los recursos del servidor
    @Test
    fun `get WeatherForecast and check timezone exist`(){
        val response = MockResponse()
            .setResponseCode(HttpsURLConnection.HTTP_OK)
            .setBody(JSONFileLoader().loadJSONString("weather_forecast_response_success")
                ?: "{errorCode:34}")
        mockWebServer.enqueue(response)
        //El contra eslas es para que no se confunda algun dato con la propiedad
        assertThat(response.getBody()?.readUtf8(), containsString("\"timezone\""))
    }

    //como leer el archivo creado para una respuesta fallida.
    @Test
    fun `get weatherForecast and check fail response`(){
        //creamos la varible de MockResponse() simulamops que es una conexión correcta
        val response = MockResponse()
            .setResponseCode(HttpsURLConnection.HTTP_OK)
            .setBody(JSONFileLoader().loadJSONString("weather_forecast_response_fail")
                ?: "{errorCode:34}")
        mockWebServer.enqueue(response)
        //El contra eslas es para que no se confunda algun dato con la propiedad
        assertThat(response.getBody()?.readUtf8(), containsString("{\"cod\":401, \"message\": " +
                "\"Invalid API key. Please see https://openweathermap.org/faq#error401 for more info.\"}"))
    }

    @Test
    fun `get weatherForecast and check contains hourly list no Empty`(){
        val response = MockResponse()
            .setResponseCode(HttpsURLConnection.HTTP_OK)
            .setBody(JSONFileLoader().loadJSONString("weather_forecast_response_success")
                ?: "{errorCode:34}")
        mockWebServer.enqueue(response)

        //Se lee el archivo JSON directamente
        assertThat(response.getBody()?.readUtf8(), containsString("hourly"))

        //Comprobamos que nuestro arreglo de pronósticos por hora no es nulo
        val json = Gson().fromJson(response.getBody()?.readUtf8() ?: "",WeatherForecastEntity::class.java)
        assertThat(json.hourly.isEmpty(), `is`(false))
    }

}