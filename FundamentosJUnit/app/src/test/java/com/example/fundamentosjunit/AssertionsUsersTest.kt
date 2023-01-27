package com.example.fundamentosjunit

import org.junit.After
import org.junit.AfterClass
import org.junit.Assert
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test


internal class AssertionsUsersTest{

    private lateinit var bot : User

    //Corre antes de cada prueba o test
    @Before
    fun setUp(){
        bot = User("8bit", 1, false)
        println("------- Before -------")
    }

    //corre despues de cada prueba o test
    @After
    fun tearDown(){
        bot = User()
        println("------- After -------")
    }

    companion object {
        private lateinit var juan : User

        //Alternativa para cuando nuestros recursos sean pesados como por ejemplo levantar un base de
        // datos algun servicio de telefono ya sea la escritura de datos en la memoria, la camara, tambie
        //es recomendado para aquellas variables que se pueden compartir de forma comun en todos los métodos
        //y que no tendran un cambio o almenos no en su base
        @BeforeClass @JvmStatic
        fun setUpCommon() {
            juan = User("Juan", 18, true)
            println("------- BeforeClass -------")
        }

        @AfterClass @JvmStatic
        fun tearDownCommon() {
            juan = User()
            println("------- AfterClass -------")
        }
    }

    @Test
    fun checkHumanTest(){
        val assertions = Assertions()
        //al tratarse de booleanos unicamente vamos a necesitar un valor esto es por que la misma
        // afirmación ya nos esta diciendo cual es el valor esperado
        Assert.assertFalse(assertions.checkHuman(bot))
        Assert.assertTrue(assertions.checkHuman(juan))
        println("------- checkHumanTest -------")
    }

    @Test
    fun checkNotNullUserTest(){
        //validando que un objeto no es nulo
        Assert.assertNotNull(juan)
        println("------- checkNotNullUserTest -------")
    }

    @Test
    fun checkNotSameUsersTest(){
        //validando que sean dos objetos diferentes y no el mismo objeto
        Assert.assertNotSame(bot, juan)
        println("------- checkNotSameUsersTest -------")
    }

    @Test
    fun checkSameUsersTest(){
        val copyJuan = juan
        //validando que los dos objetos a evaluar sean el mismo objetos
        Assert.assertSame(copyJuan, juan)
        println("------- checkSameUsersTest -------")
    }
}