package com.example.fundamentosjunit

import org.junit.Assert.*
import org.junit.Test
import kotlin.random.Random

class AssertionsTest {

    @Test
    fun getArrayTest(){
        val assertions = Assertions()
        val array = arrayOf(21, 117) //valor esperado
        assertArrayEquals("mensaje personalizado de error en testing", array, assertions.getLuckyNumbers())
    }

    @Test
    fun getNameTest(){
        val assertions = Assertions()
        val name = "Alain"
        val otherName = "Max"
        //Es igual a la respuesta
        assertEquals(name, assertions.getName())
        //No es igual a la respuesta
        assertNotEquals(otherName, assertions.getName())
    }

    @Test
    fun checkHumanTest(){
        val assertions = Assertions()
        val bot = User("8bit", 1, false)
        val juan = User("Juan", 18, true)
        //al tratarse de booleanos unicamente vamos a necesitar un valor esto es por que la misma
        // afirmación ya nos esta diciendo cual es el valor esperado
        assertFalse(assertions.checkHuman(bot))
        assertTrue(assertions.checkHuman(juan))
    }

    @Test
    fun checkNullUserTest(){
        val user = null
        val assertions = Assertions()
        assertNull(user)
        assertNull(assertions.checkHuman(user))
    }

    @Test
    fun checkNotNullUserTest(){
        val user = User("Juan", 18, true)
        //validando que un objeto no es nulo
        assertNotNull(user)
    }

    @Test
    fun checkNotSameUsersTest(){
        val bot = User("8bit", 1, false)
        val juan = User("Juan", 18, true)
        //validando que sean dos objetos diferentes y no el mismo objeto
        assertNotSame(bot, juan)
    }

    @Test
    fun checkSameUsersTest(){
        val bot = User("Juan", 18, true)
        val juan = User("Juan", 18, true)
        val copyJuan = juan
        //validando que los dos objetos a evaluar sean el mismo objetos
        assertSame(copyJuan, juan)
    }

    //Esta prueba trata de simular una peticion al servidor en promedio la respuesta
    // demora de 200 milisegundos a 1100 el objetivo de la prueba es que no sobrepase los 100
    //timepout nos sirve para limitar nuestra prueba ya que si gasta mas tiempo va cortar y va fracasar
    @Test(timeout = 1_000)
    fun getCitiesTest(){
        val cities = arrayOf("Mexico", "Perú", "Argentina")
        Thread.sleep(Random.nextLong(200, 1_100))
        assertEquals(3, cities.size)
    }
}