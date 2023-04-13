package com.cursosandroidant.inventory.addModule.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.cursosandroidant.inventory.entities.Product
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AddViewModelTest{

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    //Lo que queremos probar es que cuando se ejecute este método, nuestra variable result cambie de
    //valor y nosotros poder detectarlo esto sería la confirmación de que en efecto fue al repositorio
    //agregó el producto y se obtuvo una respuesta
    @Test
    fun addProductTest(){
        val addViewModel = AddViewModel()
        val product = Product(117,"Xbox", 20,
            "https://cdn.pixabay.com/photo/2016/02/14/21/09/xbox-1200296_1280.jpg", 4.8,
        56)
        //Se crea un observador y luego lo liberamos
        //Aquí vamos a vigilar a result, que es MutableLiveData pero el tipo es un Boolean
        val observer = Observer<Boolean>{}
        try {
            //Se configura el observador
            addViewModel.getResult().observeForever(observer)
            addViewModel.addProduct(product)
            //Vamos a captar el resultado
            val result = addViewModel.getResult().value
            assertThat(result, `is`(true))
            //assertThat(result, not(nullValue())) opcional
        } finally {
            addViewModel.getResult().removeObserver(observer)
        }
    }
}