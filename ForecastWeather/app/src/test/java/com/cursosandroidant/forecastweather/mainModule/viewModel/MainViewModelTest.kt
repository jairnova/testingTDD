package com.cursosandroidant.forecastweather.mainModule.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.cursosandroidant.forecastweather.MainCoroutineRule
import com.cursosandroidant.forecastweather.common.dataAccess.WeatherForecastService
import com.cursosandroidant.forecastweather.entities.WeatherForecastEntity
import com.cursosandroidant.historicalweatherref.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception


class MainViewModelTest{

    @get:Rule
    val instantExcecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutinesRule = MainCoroutineRule()

    private lateinit var mainViewModel: MainViewModel
    private lateinit var service: WeatherForecastService

    //Para meter el método estático de before class
    companion object{
        private lateinit var retrofit: Retrofit

        @BeforeClass
        @JvmStatic
        fun setupCommon(){
            retrofit = Retrofit.Builder().baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }

    @Before
    fun setup(){
        mainViewModel = MainViewModel()
        service = retrofit.create(WeatherForecastService::class.java)
    }

    //Primera prueba con retrofit
    @Test
    fun checkCurrentWeatherIsNotNullTest(){
        runBlocking {
            val result = service.getWeatherForecastByCoordinates(19.4342, -99.1962,
                "6a5c325c9265883997730d09be2328e8", "metric", "en")
            assertThat(result.current, `is`(notNullValue()))
        }
    }

    //Ahora podemos comprobar datos que vienen directamente desde el servidor con Red Trophy y con rutinas
    @Test
    fun checkTimeZoneReturnsMexicoCityTest(){
        runBlocking {
            val result = service.getWeatherForecastByCoordinates(19.4342, -99.1962,
                "6a5c325c9265883997730d09be2328e8", "metric", "en")
            assertThat(result.timezone, `is`("America/Mexico_City"))
        }
    }

    //Error cuando no se envian los datos completos
    @Test
    fun checkErrorResponseWithOnlyCoordinatesTest(){
        runBlocking {
            try {
                service.getWeatherForecastByCoordinates(19.4342, -99.1962,
                    "", "", "")
            } catch (e : Exception){
                assertThat(e.localizedMessage, `is`("HTTP 401 Unauthorized"))
            }
        }
    }

    //Se probara el view model directamente aquí, pero con la peculiaridad de que se trabajara
    //con corrutinas
    @Test
    fun checkHourlySizeTest(){
        //Se validara que hourly no venga vacío o mejor dicho, que tenga la longitud correcta. En este
        //caso son 48 horas laqs que están de pronóstico, por lo tanto tiene que coincidir
        runBlocking {
            mainViewModel.getWeatherAndForecast(19.4342, -99.1962,
                "6a5c325c9265883997730d09be2328e8", "metric", "en")
            val result = mainViewModel.getResult().getOrAwaitValue()
            assertThat(result.hourly.size, `is`(48))
        }
    }
}